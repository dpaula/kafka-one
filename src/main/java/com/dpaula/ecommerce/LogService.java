package com.dpaula.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

public class LogService {

    public static void main(String[] args) {

        //configurando consumidor de mensagens, cuja chave e valor sejam String, com as propriedades de configuração
        var consumer = new KafkaConsumer<String, String>(properties());

        // vai trazer todos os tópicos que comecem com ECOMERCE..
        Pattern topicosRegex = Pattern.compile("ECOMMERCE.*");

        // definindo qual será o tópico que o consumidor ficara escutando
        consumer.subscribe(topicosRegex);

        while (true) {
        // perguntando se tem mensagem no tópico por 100 ms
        var records = consumer.poll(Duration.ofMillis(100));
            if (records.isEmpty()) {
                continue;
            }
            System.out.println("Encontrei "+records.count()+" registros");

            for (var record : records) {
                System.out.println("--------------------------------------------------");
                System.out.println("LOG: "+record.topic());
                System.out.println("Chave " + record.key());
                System.out.println("Valor " + record.value());
                System.out.println("Partition " + record.partition());
                System.out.println("Offset " + record.offset());
            }
        }
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
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, LogService.class.getSimpleName());

        return properties;
    }
}
