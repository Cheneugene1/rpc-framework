# RPC框架实现

一个基于Java的简单RPC框架，支持远程方法调用、服务注册与发现、序列化与反序列化、负载均衡、容错机制等核心功能。

## 核心功能

- ✅ 远程方法调用
- ✅ 本地服务注册与发现
- ✅ 基于HTTP协议的通信
- ✅ 支持多种序列化策略（JDK、JSON、Kryo、Hessian）
- ✅ 支持多种负载均衡策略（随机、轮询、一致性哈希）
- ✅ 支持多种重试策略（不重试、固定间隔重试）
- ✅ 支持多种容错策略（快速失败、失败转移、失败安全、失败恢复）
- ✅ 基于SPI机制的组件扩展
- ✅ 双检锁单例模式的配置管理
- ✅ 完善的错误处理
- ✅ 详细的日志记录

## 技术栈

- Java 8+
- Maven
- Vert.x（HTTP服务器）
- Hutool（HTTP客户端）
- Lombok（简化代码）

## 项目结构

```
rpc12/
├── core/                    # 核心模块
│   ├── src/main/java/com/cyz/rpc/
│   │   ├── config/         # 配置管理
│   │   ├── constant/       # 常量定义
│   │   ├── fault/          # 容错机制
│   │   │   ├── retry/      # 重试策略
│   │   │   └── tolerant/   # 容错策略
│   │   ├── loadbalancer/   # 负载均衡
│   │   ├── model/          # 数据模型
│   │   ├── proxy/          # 代理层
│   │   ├── registry/       # 注册中心
│   │   ├── serializer/     # 序列化层
│   │   ├── server/         # 服务器层
│   │   ├── spi/            # SPI机制
│   │   └── utils/          # 工具类
│   └── pom.xml
├── example/                 # 示例模块
│   ├── example-common/      # 公共接口
│   ├── example-provider/    # 服务提供者
│   └── example-consumer/    # 服务消费者
├── pom.xml                  # 父级依赖管理
└── README.md                # 使用说明文档
```

## 快速开始

### 1. 编译项目

```bash
cd f:\code\rpc\rpc12
mvn clean install
```

### 2. 配置文件（application.properties）

在项目resources目录下创建application.properties文件，支持以下配置项：

```properties
# RPC框架配置
rpc.name=cyz-rpc
rpc.version=1.0
rpc.serverHost=localhost
rpc.serverPort=8080
rpc.serializer=jdk
rpc.loadBalancer=roundRobin
rpc.retryStrategy=no
rpc.tolerantStrategy=failFast
rpc.mock=false

# 注册中心配置
rpc.registryConfig.registry=local
rpc.registryConfig.address=http://localhost:2380
rpc.registryConfig.timeout=10000
```

### 3. 启动服务提供者

运行 `example-provider` 模块中的 `ProviderExample` 类，启动RPC服务器。

### 4. 运行服务消费者

运行 `example-consumer` 模块中的 `ConsumerExample` 类，调用远程服务。

## 使用示例

### 1. 定义服务接口

```java
public interface UserService {
    User getUserById(Long id);
    User createUser(User user);
}
```

### 2. 实现服务接口

```java
public class UserServiceImpl implements UserService {
    @Override
    public User getUserById(Long id) {
        // 实现逻辑
    }
    
    @Override
    public User createUser(User user) {
        // 实现逻辑
    }
}
```

### 3. 注册服务并启动服务器

```java
// 注册服务
LocalRegistry.registerStatic(UserService.class.getName(), UserServiceImpl.class);

// 启动服务器
HttpServer server = new VertxHttpServer();
server.start(8080);
```

### 4. 调用远程服务

```java
// 创建代理对象
UserService userService = ServiceProxyFactory.getProxy(UserService.class);

// 调用远程方法
User user = userService.getUserById(1L);
```

## 扩展指南

### 1. 添加新的序列化器

实现 `Serializer` 接口，并在配置文件中指定：

```java
public class JsonSerializer implements Serializer {
    // 实现序列化和反序列化方法
}
```

### 2. 添加新的负载均衡器

实现 `LoadBalancer` 接口，并在配置文件中指定：

```java
public class CustomLoadBalancer implements LoadBalancer {
    // 实现服务选择方法
    @Override
    public ServiceInstance select(List<ServiceInstance> serviceInstances) {
        // 自定义负载均衡逻辑
        return serviceInstances.get(0);
    }
}
```

### 3. 添加新的重试策略

实现 `RetryStrategy` 接口，并在配置文件中指定：

```java
public class CustomRetryStrategy implements RetryStrategy {
    // 实现重试逻辑
    @Override
    public Object doRetry(RetryFunction retryFunction) throws Exception {
        // 自定义重试策略
        return retryFunction.apply();
    }
}
```

### 4. 添加新的容错策略

实现 `TolerantStrategy` 接口，并在配置文件中指定：

```java
public class CustomTolerantStrategy implements TolerantStrategy {
    // 实现容错逻辑
    @Override
    public Object doTolerant(List<Exception> exceptions, Object... args) {
        // 自定义容错策略
        return null;
    }
}
```

### 5. 使用自定义服务器

实现 `HttpServer` 接口，并在启动时使用：

```java
public class CustomHttpServer implements HttpServer {
    // 实现服务器启动逻辑
}
```

## 核心组件说明

### 1. RpcRequest

RPC请求模型，包含服务名称、方法名称、参数类型和参数列表。

### 2. RpcResponse

RPC响应模型，包含响应数据、数据类型、响应信息和异常信息。

### 3. RpcConfig

RPC框架全局配置，包含服务名称、版本、序列化器、负载均衡器等配置项。

### 4. RegistryConfig

注册中心配置，包含注册中心类型、地址、超时时间等配置项。

### 5. RpcApplication

RPC框架应用入口，采用双检锁单例模式管理全局配置。

### 6. Serializer

序列化器接口，定义序列化和反序列化方法，支持JDK、JSON、Kryo、Hessian等多种实现。

### 7. LoadBalancer

负载均衡器接口，定义服务选择方法，支持随机、轮询、一致性哈希等多种实现。

### 8. RetryStrategy

重试策略接口，定义重试逻辑，支持不重试、固定间隔重试等实现。

### 9. TolerantStrategy

容错策略接口，定义容错逻辑，支持快速失败、失败转移、失败安全、失败恢复等实现。

### 10. LocalRegistry

本地注册中心，管理服务接口与实现类的映射关系。

### 11. ServiceProxy

JDK动态代理实现，用于客户端远程调用服务。

### 12. HttpServer

HTTP服务器接口，处理客户端请求并调用本地服务。

### 13. SpiLoader

SPI机制实现，用于动态加载组件，支持组件的扩展和替换。

## 通信流程

1. 客户端通过代理对象调用远程方法
2. 代理对象将调用信息封装为RpcRequest并序列化
3. 通过HTTP请求发送到服务端
4. 服务端接收请求并反序列化为RpcRequest
5. 从注册中心获取服务实现类
6. 使用反射调用目标方法
7. 将结果封装为RpcResponse并序列化
8. 通过HTTP响应返回给客户端
9. 客户端反序列化RpcResponse并返回结果

## 依赖说明

| 依赖 | 版本 | 用途 |
|------|------|------|
| vertx-core | 4.4.4 | HTTP服务器 |
| hutool-all | 5.8.18 | HTTP客户端和工具库 |
| lombok | 1.18.30 | 简化代码 |
| slf4j-api | 1.7.36 | 日志接口 |
| slf4j-simple | 1.7.36 | 简单日志实现（测试用） |

## 许可证

MIT License

## 贡献

欢迎提交Issue和Pull Request！
