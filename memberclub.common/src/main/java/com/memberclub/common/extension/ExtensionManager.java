/**
 * @(#)ExtensionManager.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.extension;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import com.memberclub.common.annotation.Route;
import com.memberclub.common.log.CommonLog;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ExtensionManager 是一个用于管理扩展点的类。
 * 它负责在Spring容器启动时扫描并注册所有带有 {@link ExtensionProvider} 注解的Bean，
 * 并提供根据业务场景和接口类型获取相应扩展实现的方法。
 *
 * @author 掘金五阳
 */
@Service
public class ExtensionManager {

    /**
     * Spring 应用上下文，用于获取Spring容器中的Bean。
     */
    @Autowired
    private ApplicationContext context;

    /**
     * 存储扩展点的映射，键为生成的唯一标识，值为对应的Bean对象。
     */
    private Map<String, Object> extensionBeanMap = new HashMap<>();

    /**
     * 存储业务类型到扩展实现的映射表，使用Guava的HashBasedTable。
     */
    @Getter
    private Table<BizTypeEnum, String, List<Object>> bizExtensionMeta = HashBasedTable.create();

    /**
     * 初始化方法，在Spring容器启动后自动调用。
     * 该方法扫描所有带有 {@link ExtensionProvider} 注解的Bean，并将其注册到扩展点映射中。
     */
    @PostConstruct
    public void init() {
        // 获取所有带有ExtensionProvider注解的Bean名称
        String[] beanNames = context.getBeanNamesForAnnotation(ExtensionProvider.class);

        for (String beanName : beanNames) {
            Object bean = context.getBean(beanName);
            // 获取Bean实现的所有接口
            Set<Class<?>> interfaces = ClassUtils.getAllInterfacesForClassAsSet(bean.getClass());
            // 获取ExtensionProvider注解信息
            ExtensionProvider extension = AnnotationUtils.findAnnotation(bean.getClass(), ExtensionProvider.class);
            Route[] routes = extension.bizScenes();

            for (Class<?> anInterface : interfaces) {
                if (BaseExtension.class.isAssignableFrom(anInterface)) {
                    for (Route route : routes) {
                        for (SceneEnum scene : route.scenes()) {
                            // 构建唯一的key
                            String key = buildKey(anInterface, route.bizType().getCode(), scene.getValue());

                            // 将Bean注册到extensionBeanMap中
                            Object value = extensionBeanMap.put(key, bean);
                            if (value != null) {
                                CommonLog.error("注册 Extension key:{}冲突", key);
                                throw new RuntimeException("注册 Extension 冲突");
                            }
                            CommonLog.info("注册 Extension key:{}, 接口:{}, 实现类:{}", key, anInterface.getSimpleName(), bean.getClass().getSimpleName());

                            // 更新bizExtensionMeta表
                            List<Object> extensions = bizExtensionMeta.get(route.bizType(), anInterface.getSimpleName());
                            if (extensions == null) {
                                bizExtensionMeta.put(route.bizType(), anInterface.getSimpleName(), Lists.newArrayList(bean));
                            } else {
                                extensions.add(bean);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 根据接口类、业务类型和场景构建唯一的key。
     *
     * @param anInterface 接口类
     * @param bizType 业务类型代码
     * @param scene 场景值
     * @return 唯一的key字符串
     */
    private String buildKey(Class<?> anInterface, int bizType, String scene) {
        return String.format("%s_%s_%s", anInterface.getSimpleName(), bizType, scene);
    }

    /**
     * 根据业务场景和接口类型获取相应的扩展实现。
     *
     * @param <T> 扩展接口的类型
     * @param bizScene 业务场景
     * @param tClass 扩展接口的Class对象
     * @return 对应的扩展实现
     * @throws RuntimeException 如果传入的参数不合法或未找到对应的扩展实现
     */
    public <T> T getExtension(BizScene bizScene, Class<T> tClass) {
        if (!tClass.isInterface()) {
            throw new RuntimeException(String.format("%s 需要是一个接口", tClass.getSimpleName()));
        }
        if (!BaseExtension.class.isAssignableFrom(tClass)) {
            throw new RuntimeException(String.format("%s 需要继承 BaseExtension 接口", tClass.getSimpleName()));
        }

        // 构建key并尝试获取扩展实现
        String key = buildKey(tClass, bizScene.getBizType(), bizScene.getScene());
        T value = (T) extensionBeanMap.get(key);

        // 如果未找到，则尝试使用默认的业务类型和场景
        if (value == null) {
            key = buildKey(tClass, BizTypeEnum.DEFAULT.getCode(), SceneEnum.DEFAULT_SCENE.getValue());
            value = (T) extensionBeanMap.get(key);
        }

        if (value == null) {
            throw new RuntimeException(String.format("%s 没有找到实现类%s", tClass.getSimpleName(), bizScene.getKey()));
        }
        return value;
    }

    /**
     * 获取构建业务场景的扩展实现。
     *
     * @param bizScene 业务场景
     * @return 对应的BizSceneBuildExtension实现
     */
    public BizSceneBuildExtension getSceneExtension(BizScene bizScene) {
        return getExtension(bizScene, BizSceneBuildExtension.class);
    }
}
