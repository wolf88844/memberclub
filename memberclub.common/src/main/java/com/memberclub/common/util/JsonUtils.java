/**
 * @(#)JsonUtils.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.memberclub.common.log.CommonLog;
import lombok.Getter;

import java.io.IOException;

import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;

/**
 * author: 掘金五阳
 */
public class JsonUtils {

    @Getter
    private static ObjectMapper objectMapper = new ObjectMapper();


    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(FAIL_ON_EMPTY_BEANS, false);
        objectMapper.disable(FAIL_ON_EMPTY_BEANS);
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);


    }


    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            CommonLog.error("转成 JSON异常 ", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String json, Class<T> tClass) {
        try {
            return objectMapper.readValue(json, tClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> reference) {
        try {
            return objectMapper.readValue(json, reference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}