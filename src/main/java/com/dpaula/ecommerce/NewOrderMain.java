package com.dpaula.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        var dispatcher = new KafkaDispatcher();

        //para produzir uma mensagem, com tipo da chave e tipo da mensagem
        try(var producer = new KafkaProducer<String, String>(properties());) {

            for (var i = 0; i < 10; i++) {
                var key = UUID.randomUUID().toString();

                var value = key + ",67144,8934844";
                dispatcher.send("ECOMMERCE_NEW_ORDER", key, value);

                var email = "Obrigado pelo pedido! Estamos processando seu pedido!";
                dispatcher.send("ECOMMERCE_SEND_EMAIL", key, email);
            }
        }
    }

    // criando as propriedades na mão, mas deve ser pelo arquivo de properties
    private static Properties properties() {

        var properties = new Properties();

        //setando o endereço do kafka
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        //Informando qual classe de serialização será usada para chave, neste caso sera string
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //Informando qual classe de serialização será usada para o valor, neste caso sera string
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return properties;
    }
}
