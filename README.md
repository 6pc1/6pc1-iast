# 6pc1-iast

本项目主要是实现了一个简易的Active IAST的demo。

iast文件夹是主要的插桩程序，用来对程序进行主动插桩 test是运行的测试环境，里面写了一个测试命令执行的页面

## 配置与运行

- 使用maven package包
- 选用tomcat8，配置好相应的虚拟机选项，其中-Xbootclasspath/p为了解决在双亲委派机制下Agent找不到类的为题

```java
-Dfile.encoding=GBK -noverify -Xbootclasspath/p:E:\AST\IAST\6pc1-iast\iast\target\agent.jar -javaagent:E:\AST\IAST\6pc1-iast\iast\target\agent.jar  //也就是对应jar包的位置
```

- 访问

```
http://localhost:8080/cmd.jsp?cmd=whoami
```

项目会保存调用栈与调用信息到`iast\src\main\resources`目录中

- 如果想查看被插桩的类的效果的话，修改`AgentTransform`里面的路径

## 参考文章

[浅谈被动式IAST产品与技术实现-代码实现Demo篇 - Sky's 自留地 (03sec.com)](https://www.03sec.com/Ideas/qian-tan-bei-dong-shiiast-chan-pin-yu-ji-shu-shi-x-1.html#morphing)

[RASP攻防实现篇](https://www.03sec.com/Ideas/qian-tanrasp-ji-shu-gong-fang-zhi-shi-zhan-huan-ji.html)

[IAST学习笔记 - 先知社区 (aliyun.com)](https://xz.aliyun.com/t/11042?time__1311=mqmx0DyDuDBD2Djx4BuQ1YQhDA2QKiYKeD&alichlgref=https%3A%2F%2Fwww.google.com%2F)

[洞态IAST安装使用 - FreeBuf网络安全行业门户](https://m.freebuf.com/articles/web/287339.html)

[Java Web安全\] (javasec.org)](https://javasec.org/java-iast/IAST-Basic.html)