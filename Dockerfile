FROM java:8
MAINTAINER me
WORKDIR /Users/gkarabut/src/kafkaproducer
ADD . /Users/gkarabut/src/kafkaproducer
RUN touch output.log
CMD java -jar build/libs/kafkaproducer-1.0-SNAPSHOT-all.jar
