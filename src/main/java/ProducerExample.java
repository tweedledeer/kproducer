/**
 * Author gkarabut
 * since 11/30/17.
 */
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

public class ProducerExample {

  static private Properties getProperties() throws Exception {
    Properties defaultProps = new Properties();
    String filename = "kafka.properties";
    InputStream input = ProducerExample.class.getClassLoader().getResourceAsStream(filename);
    defaultProps.load(input);
    return defaultProps;
  }

  static private Producer<Long, String> createProducer() throws Exception {
    Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
        getProperties().getProperty("bootstrapEndpoint"));
    props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    return new KafkaProducer<>(props);
  }

  static void runProducer() throws Exception {
    //  static void runProducer(final int sendMessageCount) throws Exception {
    PrintWriter writer = new PrintWriter("output.log", "UTF-8");
    final Producer<Long, String> producer = createProducer();
    long time = System.currentTimeMillis();
    try {
      //      for (long index = time; index < time + sendMessageCount; index++) {
      //        final ProducerRecord<Long, String> record =
      //            new ProducerRecord<>(getProperties().getProperty("kafkaTopic"), index, "Hello Mom " + index);
      //        RecordMetadata metadata = producer.send(record).get();
      //
      //        long elapsedTime = System.currentTimeMillis() - time;
      //        System.out
      //            .printf("sent record(key=%s value=%s) " + "meta(partition=%d, offset=%d) time=%d\n",
      //                record.key(), record.value(), metadata.partition(), metadata.offset(), elapsedTime);
      //      }
      long index = 0;
      while (true) {
        final ProducerRecord<Long, String> record =
            new ProducerRecord(getProperties().getProperty("kafkaTopic"), index,
                "Hello Mom " + index);
        RecordMetadata metadata = producer.send(record).get();

        long elapsedTime = System.currentTimeMillis() - time;
        Thread.sleep(3000);

        String output = String
            .format("sent record(key=%s value=%s) " + "meta(partition=%d, offset=%d) time=%d\n",
                record.key(), record.value(), metadata.partition(), metadata.offset(), elapsedTime);
        System.out.printf(output);
        writer.println(output);
        index++;
      }
    } finally {
      writer.close();
      producer.flush();
      producer.close();
    }
  }

  public static void main(String[] args) throws Exception {
    runProducer();
    //       if (args.length == 0) {
    //        runProducer(500);
    //      } else {
    //        runProducer(Integer.parseInt(args[0]));
    //      }
  }
}


