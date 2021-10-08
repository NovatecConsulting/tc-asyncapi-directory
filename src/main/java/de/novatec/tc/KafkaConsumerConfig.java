package de.novatec.tc;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.confluent.kafka.serializers.KafkaJsonDeserializer;
import io.confluent.kafka.serializers.KafkaJsonDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    private final Map<String, String> properties;

    @Autowired
    public KafkaConsumerConfig(@Qualifier("kafkaConfig") Map<String, String> properties) {
        this.properties = properties;
    }

    @Bean
    public ConsumerFactory<ObjectNode, ObjectNode> consumerFactory() {
        Map<String, Object> actualConfig = new HashMap<>(properties);
        actualConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaJsonDeserializer.class);
        actualConfig.put(KafkaJsonDeserializerConfig.JSON_KEY_TYPE, ObjectNode.class);
        actualConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaJsonDeserializer.class);
        actualConfig.put(KafkaJsonDeserializerConfig.JSON_VALUE_TYPE, ObjectNode.class);
        return new DefaultKafkaConsumerFactory<>(actualConfig);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<ObjectNode, ObjectNode> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<ObjectNode, ObjectNode> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
