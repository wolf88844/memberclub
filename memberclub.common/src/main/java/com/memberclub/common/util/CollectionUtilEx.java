/**
 * @(#)CollectionUtilEx.java, 一月 25, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * author: 掘金五阳
 */
public class CollectionUtilEx {

    public static <T> List<T> toList(Collection<T> collection) {
        if (collection == null) {
            return new ArrayList<>();
        }
        if (collection instanceof List) {
            return (List<T>) collection;
        }
        return Lists.newArrayList(collection);
    }

    public static <T> List<T> filter(Collection<T> collection, Predicate<T> function) {
        if (collection == null) {
            return new ArrayList<>();
        }
        if (collection instanceof List) {
            return (List<T>) collection;
        }
        return collection.stream().filter(function).collect(Collectors.toList());
    }

    public static <T, R> List<R> filterAndMap(Collection<T> collection, Predicate<T> function, Function<T, R> mapper) {
        if (collection == null) {
            return new ArrayList<>();
        }
        if (collection instanceof List) {
            return CollectionUtilEx.map((List<T>) collection, mapper);
        }
        return collection.stream().filter(function).map(mapper).collect(Collectors.toList());
    }

    public static <T> Set<T> toSet(Collection<T> collection) {
        if (collection == null) {
            return new HashSet<>();
        }
        if (collection instanceof Set) {
            return (Set<T>) collection;
        }
        return Sets.newHashSet(collection);
    }

    public static <T, R> List<R> map(List<T> collection, Function<T, R> mapper) {
        return collection.stream().map(mapper).collect(Collectors.toList());
    }

    public static <T, R> Set<R> map(Set<T> collection, Function<T, R> mapper) {
        return collection.stream().map(mapper).collect(Collectors.toSet());
    }

    public static <T, R> List<R> mapToList(Collection<T> collection, Function<T, R> mapper) {
        return collection.stream().map(mapper).collect(Collectors.toList());
    }

    public static <T, R> Set<R> mapToSet(Collection<T> collection, Function<T, R> mapper) {
        return collection.stream().map(mapper).collect(Collectors.toSet());
    }

    public static <K, V, C> Map<K, C> convertMapValue(Map<K, V> map,
                                                      BiFunction<K, V, C> valueFunction,
                                                      BinaryOperator<C> mergeFunction) {
        if (MapUtils.isEmpty(map)) {
            return new HashMap<>();
        }
        return map.entrySet().stream().collect(Collectors.toMap(
                e -> e.getKey(),
                e -> valueFunction.apply(e.getKey(), e.getValue()),
                mergeFunction
        ));
    }

    public static <K, V, C> Map<K, C> convertMapValue(Map<K, V> originMap, BiFunction<K, V, C> valueConverter) {
        return convertMapValue(originMap, valueConverter, pickSecond());
    }

    public static <T, K> Map<K, T> toMap(Collection<T> collection, Function<? super T, ? extends K> keyMapper) {
        return toMap(collection, keyMapper, Function.identity());
    }

    public static <T, K, V> Map<K, V> toMap(Collection<T> collection,
                                            Function<? super T, ? extends K> keyFunction,
                                            Function<? super T, ? extends V> valueFunction) {
        return toMap(collection, keyFunction, valueFunction, pickSecond());
    }

    public static <T, K, V> Map<K, V> toMap(Collection<T> collection,
                                            Function<? super T, ? extends K> keyFunction,
                                            Function<? super T, ? extends V> valueFunction,
                                            BinaryOperator<V> mergeFunction) {
        if (CollectionUtils.isEmpty(collection)) {
            return new HashMap<>(0);
        }

        return collection.stream().collect(Collectors.toMap(keyFunction, valueFunction, mergeFunction));
    }

    public static <T> BinaryOperator<T> pickFirst() {
        return (k1, k2) -> k1;
    }

    public static <T> BinaryOperator<T> pickSecond() {
        return (k1, k2) -> k2;
    }
}