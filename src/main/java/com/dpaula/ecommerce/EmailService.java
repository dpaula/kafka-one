package com.dpaula.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class EmailService {

    public static void main(String[] args) {

        var emailService = new EmailService();
        var service = new KafkaService(EmailService.class.getSimpleName(),
                "ECOMMERCE_SEND_EMAIL",
                emailService::parse);

        service.run();
    }

    /**
     * Corpo da execução da mensagem
     *
     * @param record
     */
    private void parse(ConsumerRecord<String, String> record) {
        System.out.println("--------------------------------------------------");
        System.out.println("Enviando Email");
        System.out.println("Chave " + record.key());
        System.out.println("Valor " + record.value());
        System.out.println("Partition " + record.partition());
        System.out.println("Offset " + record.offset());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Email foi enviado");
    }
}
