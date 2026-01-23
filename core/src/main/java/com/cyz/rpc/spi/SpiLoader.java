package com.cyz.rpc.spi;

import cn.hutool.core.io.resource.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpiLoader {
    private static final Logger log = LoggerFactory.getLogger(SpiLoader.class);

    /**
     * 存储已加载的类：接口名 ->（key -> 实现类）
     */
    private static final Map<String, Map<String, Class<?>>> loaderMap = new ConcurrentHashMap<>();

    /**
     * 系统 SPI 目录
     */
    private static final String SYSTEM_SPI_DIR = "META-INF/rpc/system/";

    /**
     * 自定义 SPI 目录
     */
    private static final String CUSTOM_SPI_DIR = "META-INF/rpc/custom/";

    /**
     * 扫描路径
     */
    private static final String[] SCAN_DIRS = {SYSTEM_SPI_DIR, CUSTOM_SPI_DIR};

    /**
     * 加载所有类型
     */
    private static final List<Class<?>> LOAD_CLASS_LIST = new ArrayList<>();

    /**
     * 获取实例
     *
     * @param tClass
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getInstance(Class<T> tClass, String key) {
        String tClassName = tClass.getName();
        Map<String, Class<?>> keyClassMap = loaderMap.get(tClassName);
        if (keyClassMap == null) {
            throw new RuntimeException(String.format("SpiLoader 未加载 %s 类型", tClassName));
        }
        if (!keyClassMap.containsKey(key)) {
            throw new RuntimeException(String.format("SpiLoader 的 %s 不存在 key=%s 的类型", tClassName, key));
        }
        Class<?> implClass = keyClassMap.get(key);
        try {
            return (T) implClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(String.format("SpiLoader 创建 %s 实例失败", implClass.getName()), e);
        }
    }

    /**
     * 加载指定类型
     *
     * @param loadClass
     * @return
     */
    public static Map<String, Class<?>> load(Class<?> loadClass) {
        log.info("加载类型为 {} 的 SPI", loadClass.getName());
        String loadClassName = loadClass.getName();
        Map<String, Class<?>> keyClassMap = new HashMap<>();

        // 加载所有目录下的配置文件
        for (String scanDir : SCAN_DIRS) {
            List<URL> resources = null;
            try {
                resources = ResourceUtil.getResources(scanDir + loadClassName);
            } catch (Exception e) {
                log.error("spi resource load error", e);
            }
            if (resources == null) {
                continue;
            }

            // 读取配置文件内容
            for (URL resource : resources) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.openStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split("=");
                        if (parts.length != 2) {
                            log.error("spi config error: {}", line);
                            continue;
                        }
                        String key = parts[0].trim();
                        String className = parts[1].trim();
                        try {
                            Class<?> implClass = Class.forName(className);
                            // 检查是否是实现类
                            if (!loadClass.isAssignableFrom(implClass)) {
                                log.error("{} is not assignable from {}", loadClass.getName(), className);
                                continue;
                            }
                            keyClassMap.put(key, implClass);
                        } catch (ClassNotFoundException e) {
                            log.error("load class error: {}", className, e);
                        }
                    }
                } catch (IOException e) {
                    log.error("read spi resource error", e);
                }
            }
        }
        loaderMap.put(loadClassName, keyClassMap);
        return keyClassMap;
    }

    /**
     * 加载所有类型
     */
    public static void loadAll() {
        log.info("加载所有 SPI");
        for (Class<?> aClass : LOAD_CLASS_LIST) {
            load(aClass);
        }
    }
}