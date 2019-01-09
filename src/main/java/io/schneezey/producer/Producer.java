package io.schneezey.producer;

import java.net.InetAddress;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

import io.schneezey.pojo.SmallPojo;


/**
 * This is meant to show how to use an Avro generated class from a schema.  
 * 
 * I started with a manual preparation of creating a schema using Avro reflection. 
 *
 */
public class Producer 
{
	public static Logger logger = LoggerFactory.getLogger(Producer.class);

	public static void main(String[] args) {

		produceAvroGeneratedPojo();
	}

	private static SmallPojo createTestPojo() {
		SmallPojo pojo = SmallPojo.newBuilder()
		.setId(Random.randomInt(999999999)).setTestString(Random.randomWord(40))
		.build();
		return pojo;
	}
    
	private static void produceAvroGeneratedPojo() {
		KafkaProducer<String,SmallPojo> producer = null;
    	try {
	        Properties config = new Properties();
	        config.put("client.id", InetAddress.getLocalHost().getHostName());
	        config.put("bootstrap.servers", "localhost:9092");
	        config.put("acks", "all");
	        
	        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
	                org.apache.kafka.common.serialization.StringSerializer.class);
	        
	        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
	        config.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");

	        producer = new KafkaProducer<String, SmallPojo> (config);
	        
	        
	        for (int i = 0; i < 3; i++) {
	        	SmallPojo pojo = createTestPojo();
		        ProducerRecord<String,SmallPojo> avroRecord = new ProducerRecord<String,SmallPojo> ("test.avrogen.smallpojo", pojo.getId().toString(), pojo);
	        	producer.send( avroRecord );
		        logger.debug("Avro Message produced" + pojo.toString() );
			}
	        producer.close();
    	} catch (Exception ee) {
    		ee.printStackTrace();
    		logger.error("Exception Caught \n" + ee.getLocalizedMessage());
    	}
    	finally {
    		if (producer != null) producer.close();
    	}
	}

}
