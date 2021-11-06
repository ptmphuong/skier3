# skier3
Java servlet for [CS6650 Building Scalable Distributed Systems Assignment 1][assignment-1-link]

* Run on [Apache Tomcat][tomcat-link]
* Use [Generic Object Pool][GenObjPool-link] to manage channels, and send message to [RabbitMQ][rabbitmq-link] server.

RabbitMQ consumer is implemented [here][rabbitmq-consumer-impl]

[assignment-1-link]: https://gortonator.github.io/bsds-6650/assignments-2021/Assignment-1
[tomcat-link]: http://tomcat.apache.org/
[GenObjPool-link]: https://commons.apache.org/proper/commons-pool/apidocs/org/apache/commons/pool2/impl/GenericObjectPool.html
[rabbitmq-link]: https://www.rabbitmq.com/
[rabbitmq-consumer-impl]: https://github.com/ptmphuong/rabbitMQ-consumer
