# 1. akkad-java

## 1.1. 设计模式akkad-design

点击即可查看[观察设计模式](akkad-java/akkad-design/Observer.md)详细介绍





## Netty



### 实践

#### 数据通信

- 长连接通道不断开的形式进行通信，客户端和服务器的通道一直处于开启状态，适用场景，服务器性能最够好，并且客户端数量比较少情况下。
- 一次性批量提交数据，采用短链接方式。可以将数据保存再本地临时缓存或者临时表里，当达到临界值时候，一次性批量提交。或者根据既定任务轮询提交。弊端，数据实时性得不到保证。
- 特殊长连接，指定某一段时间内，服务器和客户端没有任何通信，断开连接。下次连接则是客户端向服务器发起请求，再次简历连接。需要考虑两个问题：
  - 如何再超市（服务器和客户端没有任何通信）后关闭通道？关闭通道后又如何再次建立连接？
  - 客户端宕机，客户端重启后再次与服务器建立连接，但是服务器宕机后，客户端与服务器进行连接？

