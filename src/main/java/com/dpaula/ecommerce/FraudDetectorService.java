package com.dpaula.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class FraudDetectorService {

    public static void main(String[] args) {

        //configurando consumidor de mensagens, cuja chave e valor sejam String, com as propriedades de configuração
        var consumer = new KafkaConsumer<String, String>(properties());

        List<String> topicos = Collections.singletonList("ECOMMERCE_NEW_ORDER");

        // definindo qual será o tópico que o consumidor ficara escutando
        consumer.subscribe(topicos);

        while (true) {
        // perguntando se tem mensagem no tópico por 100 ms
        var records = consumer.poll(Duration.ofMillis(100));
            if (records.isEmpty()) {
                continue;
            }
            System.out.println("Encontrei "+records.count()+" registros");

            for (var record : records) {
                System.out.println("--------------------------------------------------");
                System.out.println("Processando novo pedido, verificando por fraude");
                System.out.println("Chave " + record.key());
                System.out.println("Valor " + record.value());
                System.out.println("Partition " + record.partition());
                System.out.println("Offset " + record.offset());

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Pedido processado");
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
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, FraudDetectorService.class.getSimpleName());
        // definindo um id para o consumidor
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, FraudDetectorService.class.getSimpleName()+ "-"+ UUID.randomUUID().toString());
        // definindo que será feito o commit das mensagens processadas, uma em uma
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
        return properties;
    }
}
