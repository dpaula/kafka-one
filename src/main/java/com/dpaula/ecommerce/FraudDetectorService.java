package com.dpaula.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class FraudDetectorService {

    public static void main(String[] args) {

        var fraudService = new FraudDetectorService();

        try (var service = new KafkaService<>(FraudDetectorService.class.getSimpleName(),
                "ECOMMERCE_NEW_ORDER",
                fraudService::parse,
                Order.class)) {

            service.run();
        }
    }

    /**
     * Corpo da execução da mensagem
     *
     * @param record
     */
    private void parse(ConsumerRecord<String, Order> record) {
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
