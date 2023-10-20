package com.oauth.demo.service.converter;

public interface Converter <S, T> {
    T convert(S value);

    S convertBack(T value);
}
