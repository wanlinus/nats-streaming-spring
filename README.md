# Nats Streaming Spring

![](https://img.shields.io/badge/language-java-brightgreen)![](https://img.shields.io/badge/springboot-2.0-blue) ![](https://img.shields.io/badge/Java-1.8-blue)![](https://img.shields.io/badge/license-MIT-green)![](https://img.shields.io/badge/platform-linux--64%20%7C%20win--32%20%7C%20osx--64%20%7C%20win--64-lightgrey)

NSS -- 一个集成NATS-Streaming到Spring boot的插件

## Table of Contents

- [Description](#Description)
- [Geting Start](#Geting%20Start)
- [About Repo](#About%20Repo)
- [Maintainers](#Maintainers)
- [Contributing](#Contributing)
  - [Contributors](#Contributors)
- [License](#License)

## Description

[Nats](https://nats.io/) 一个使用Golang写的高性能MQ中间件,但是缺乏对Spring的支持. 在工作中Spring已经成为Java开发企业级标准. 所以社区急需一个能集成Spring的插件, 所以nss就诞生了. 这个插件使用NATS-Streaming. 因为NATS不支持数据持久化

## Geting Start

```bash
https://github.com/wanlinus/nats-streaming-spring.git
cd nats-streaming-spring
mvn clean install
```

maven:

```xml
<dependency>
	<groupId>cn.wanlinus</groupId>
    <artifactId>nats-streaming-spring</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```

开启支持NatsStreaming

```java
@EnableNatsStreaming
@SpringBootApplication
public class NSSApplication {
    public static void main(String[] args) {
        SpringApplication.run(NSSApplication.class, args);
    }
}
```

在application.yaml中配置nats-streaming

```yaml
spring:
  nats:
    streaming:
      cluster-id: test-cluster
      nats-urls: nats://10.0.0.20:4223
```

消费消息

```java
@Service
public class NatsService {
    private static final Logger logger = LoggerFactory.getLogger(NatsService.class);

    @Subscribe(value = "bbb", durableName = "asd")
    public void asd(Message message) {
        logger.info("收到bbb msg[{}]", new String(message.getData(), StandardCharsets.UTF_8));
    }
}
```

生产消息

```java
    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> {
            try {
                for (int i = 0; i < 10;i++) {
                    logger.info("发送消息");
                    String msg = "send msg " + i;
                    connection.publish("bbb", msg.getBytes(StandardCharsets.UTF_8));
                    Thread.sleep(1000);
                }

            } catch (IOException | InterruptedException | TimeoutException e) {
                e.printStackTrace();
            }
        }).start();
    }
```

然后就可以快乐的使用啦

## About Repo

[spring集成Nats](https://github.com/wanlinus/nats-spring)

[nats-spring测试代码](https://github.com/wanlinus/nats-spring-test)

## Maintainers

[@ wanli](https://https//github.com/wanlinus)

## Contributing

非常欢迎你的加入! [提一个Issue](https://github.com/wanlinus/nats-streaming-spring/issues/new) 或者提交一个 Pull Request.

Havefun 遵循 [Contributor Covenant](http://contributor-covenant.org/version/1/3/0/) 行为规范.

### Contributors

感谢以下参与项目的人：

[![img](https://avatars0.githubusercontent.com/u/34122643?s=460&v=4)](https://www.github.com/wanlinus)

## License

[MIT](https://github.com/wanlinus/havefun/blob/master/LICENSE) © Wanli