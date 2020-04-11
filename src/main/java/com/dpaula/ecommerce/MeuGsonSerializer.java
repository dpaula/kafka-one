package com.dpaula.ecommerce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Serializer;

/**
 * @author Fernando de Lima
 *
 * Para ter um serializer válido para o kafka, tem que implementar seu Serializer<T>
 */
public class MeuGsonSerializer<T> implements Serializer<T> {

    private final Gson gson = new GsonBuilder().create();

    /**
     * O serialize do kafka define que dever ser convertido o meu objeto <T>
     *     para um array de bytes,
     *
     *     E neste caso vamos usar um conversor JSON
     *
     * @param s
     * @param t
     * @return array de bytes
     */
    @Override
    public byte[] serialize(String s, T t) {
        return gson.toJson(t).getBytes();
    }
}
