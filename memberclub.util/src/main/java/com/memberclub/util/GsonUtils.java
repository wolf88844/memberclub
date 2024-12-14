/**
 * @(#)GsonUtils.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author yuhaiqiang
 */
public class GsonUtils {

    public static final Gson gson = new Gson();

    public static String toJSON(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, TypeToken<T> token) {
        return gson.fromJson(json, token.getType());
    }
}