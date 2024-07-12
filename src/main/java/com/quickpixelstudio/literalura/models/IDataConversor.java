package com.quickpixelstudio.literalura.models;

public interface IDataConversor {
    <T> T getData(String json, Class<T> tClass);
}