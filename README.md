# 《Spring微服务实战》 对应的demo
## 版本
* jdk 1.80_201
* Spring Boot 1.5.20.RELEASE
* Spring Cloud Edgware.SR5
* Mysql 5.6 
* Redis 3.2.8
* kafka 2.2.0
* zookeeper 3.4.13
## 模块
* spring-cloud-authentication-service 集成Oauth2，验证服务
* spring-cloud-config-server -- Spring Cloud Config 服务端， [https://github.com/2qiaoyu/config-repo.git]()
* spring-cloud-eureka-server -- Eureka 服务
* spring-cloud-licensing-service -- 许可证服务
* spring-cloud-organization-service -- 组织服务
* spring-cloud-organization-service-new --
  新组织服务，配合Zuul和specialroutes模块自定义路由进行AB测试
* spring-cloud-specialroutes-service 配合Zuul自定义路由查询AB测试配置
* spring-cloud-zipkin-server zipkin服务
* spring-cloud-zuul-server 网关服务
* spring-cloud-zuul-server-special 特殊网关服务，带有ABRoute
* sql -- sql脚本 
## 启动顺序
1. spring-cloud-eureka-server
2. spring-cloud-config-server
3. spring-cloud-authentication-service 
4. spring-cloud-licensing-service
5. spring-cloud-organization-service
6. spring-cloud-zuul-serve
7. spring-cloud-zipkin-server