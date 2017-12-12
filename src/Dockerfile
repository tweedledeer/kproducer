FROM kafka_cont_snapshot
RUN
ENTRYPOINT /home/zookeeper/bin/zkServer.sh start && /home/kafka/kafka_2.11-1.0.0/bin/kafka-server-start.sh /home/kafka/kafka_2.11-1.0.0/config/server-properties  bash
