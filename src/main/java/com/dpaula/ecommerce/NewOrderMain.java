package com.dpaula.ecommerce;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //para produzir uma mensagem, com tipo da chave e tipo da mensagem
        var producer = new KafkaProducer<String, String>(properties());

        var value = "1313413,671343,89348934";

        //mensagem que tera a mesma informação, tanto pra chave quanto o valor
        var record = new ProducerRecord<>("ECOMMERCE_NEW_ORDER", value, value);

        // com o .get ele fica sincrono
        producer.send(record, (dadosSucesso, excpFalha) -> {
            //callback para tratar o retonro sincrono

            if(excpFalha != null){
                excpFalha.printStackTrace();
                return;
            }
            System.out.println("Sucesso enviando : "+dadosSucesso.topic()+":::partition "+dadosSucesso.partition()+"/ offset "+dadosSucesso.offset()+"/ timestamp "+dadosSucesso.timestamp());
        }).get();
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
