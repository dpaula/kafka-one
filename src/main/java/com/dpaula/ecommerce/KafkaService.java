package com.dpaula.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * @author Fernando de Lima
 */
class KafkaService {
    private final ConsumerFunction parse;
    private final KafkaConsumer<String, String> consumer;

    KafkaService(String topico, ConsumerFunction parse) {
        this.parse = parse;
        //configurando consumidor de mensagens, cuja chave e valor sejam String, com as propriedades de configuração
        this.consumer = new KafkaConsumer<>(properties());

        List<String> topicos = Collections.singletonList(topico);

        // definindo qual será o tópico que o consumidor ficara escutando
        consumer.subscribe(topicos);

    }

    private static Properties properties() {

        var properties = new Properties();

        //setando o endereço do kafka
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        //Informando qual classe de DEserialização será usada para chave, neste caso sera string
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //Informando qual classe de DEserialização será usada para o valor, neste caso sera string
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // definindo o id do grupo consumidor
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, EmailService.class.getSimpleName());

        return properties;
    }

    void run() {
        while (true) {
            // perguntando se tem mensagem no tópico por 100 ms
            var records = consumer.poll(Duration.ofMillis(100));
            if (records.isEmpty()) {
                continue;
            }
            System.out.println("Encontrei " + records.count() + " registros");

            for (var record : records) {
                parse.consume(record);
            }
        }
    }

}
