# RPC框架实现

一个基于Java的简单RPC框架，支持远程方法调用、服务注册与发现、序列化与反序列化等核心功能。

## 核心功能

- ✅ 远程方法调用
- ✅ 本地服务注册与发现
- ✅ 基于HTTP协议的通信
- ✅ 支持JDK序列化
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
│   ├── src/main/java/com/yupi/rpc/
│   │   ├── model/          # 数据模型
│   │   ├── serializer/     # 序列化层
│   │   ├── registry/       # 注册中心
│   │   ├── proxy/          # 代理层
│   │   ├── server/         # 服务器层
│   │   └── client/         # 客户端层
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

### 2. 启动服务提供者

运行 `example-provider` 模块中的 `ProviderExample` 类，启动RPC服务器。

### 3. 运行服务消费者

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
LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

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

实现 `Serializer` 接口，并在相应位置使用新的序列化器：

```java
public class JsonSerializer implements Serializer {
    // 实现序列化和反序列化方法
}
```

### 2. 使用自定义服务器

实现 `HttpServer` 接口，并在启动时使用自定义服务器：

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

### 3. Serializer

序列化器接口，定义序列化和反序列化方法。

### 4. LocalRegistry

本地注册中心，管理服务接口与实现类的映射关系。

### 5. ServiceProxy

JDK动态代理实现，用于客户端远程调用服务。

### 6. HttpServer

HTTP服务器接口，处理客户端请求并调用本地服务。

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
| hutool-all | 5.8.18 | HTTP客户端 |
| lombok | 1.18.30 | 简化代码 |

## 许可证

MIT License

## 贡献

欢迎提交Issue和Pull Request！
