# java-proto-generator

该项目基于 Spring Boot 构建，实现 Java Bean 转换到 Proto IDL。

未来还可能会实现自动编译 Proto，并生成 Java 和 proto 之间互相转换的代码。

# 使用

1、拉取源代码到本地；

2、打开终端，进入项目目录，进入 gen 这个 module，执行 **mvn package**；

3、进入 gen 下的 target 文件夹，找到 gen-version.jar （version 是变量，例如 gen-0.0.1.jar）；

4、将该 jar 包引入到你自己的项目，然后写一个测试类，调用 com.kurt.gen.java2proto.service.ProtoGenerator#gen() 方法，第一个参数传入要生成 proto 文件的 POJO 的全限定名，第二个参数传入放 proto 文件的文件夹名，我建议是服务名.模块名这样，比如 pay.order，这个 pay 就是服务名，order 就是模块名。