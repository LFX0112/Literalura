package com.quickpixelstudio.literalura.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class DataConversor implements IDataConversor{
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> tClass){
        try {
            return objectMapper.readValue(json, tClass);
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}