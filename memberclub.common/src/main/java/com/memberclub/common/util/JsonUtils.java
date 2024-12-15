/**
 * @(#)JsonUtils.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.memberclub.common.log.CommonLog;

import java.io.IOException;

/**
 * author: 掘金五阳
 */
public class JsonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();


    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


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