/**
 * @(#)MemberPerformItemFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.flow.execute;

import com.google.common.collect.Lists;
import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.flow.SkipException;
import com.memberclub.common.log.CommonLog;
import com.memberclub.domain.context.perform.PerformItemContext;
import com.memberclub.domain.context.perform.common.PerformItemStatusEnum;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.entity.MemberPerformItem;
import com.memberclub.domain.exception.ResultCode;
import com.memberclub.infrastructure.mybatis.mappers.MemberPerformItemDao;
import com.memberclub.sdk.common.Monitor;
import com.memberclub.sdk.perform.extension.execute.MemberPerformItemExtension;
import com.memberclub.sdk.perform.service.domain.PerformDomainService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Service
public class MemberPerformItemFlow extends FlowNode<PerformItemContext> {

    @Autowired
    ExtensionManager extensionManager;

    @Autowired
    private MemberPerformItemDao memberPerformItemDao;

    @Autowired
    private PerformDomainService performDomainService;

    @Override

    public void process(PerformItemContext context) {
        MemberPerformItemExtension extension =
                extensionManager.getExtension(context.toDefaultScene(), MemberPerformItemExtension.class);
        List<MemberPerformItem> items = extension.toMemberPerformItems(context);

        int count = performDomainService.insertMemberPerformItems(items);
        if (count >= items.size()) {
            CommonLog.warn("当前请求写入成功 member_perform_item actual:{}, expect:{}, context:{}",
                    count, items.size(), context);
            Monitor.PERFORM_EXECUTE.counter(context.getBizType(),
                    "insert_item", true,
                    "right_type", context.getRightType());
            return;
        }

        CommonLog.warn("当前请求写入 member_perform_item数量不足 actual:{}, expect:{}, context:{}",
                count, items.size(), context);
        // TODO: 2024/12/18 兼容异常处理
        List<MemberPerformItemDO> memberPerformItemDOS = Lists.newArrayList();

        for (MemberPerformItemDO item : context.getItems()) {
            MemberPerformItem itemInDb =
                    memberPerformItemDao.queryByItemToken(context.getUserId(), item.getItemToken());
            if (itemInDb == null) {
                Monitor.PERFORM_EXECUTE.counter(context.getBizType(),
                        "insert_item", false,
                        "right_type", context.getRightType());
                throw ResultCode.INTERNAL_ERROR.newException("member_perform_item写入失败,稍后重试");
            }
            if (!PerformItemStatusEnum.hasPerformed(itemInDb.getStatus())) {
                memberPerformItemDOS.add(item);
            }
        }
        if (CollectionUtils.isEmpty(memberPerformItemDOS)) {
            Monitor.PERFORM_EXECUTE.counter(context.getBizType(),
                    "insert_item", "duplicated",
                    "right_type", context.getRightType());
            CommonLog.warn("已完成履约:{}", context.getRightType());
            throw new SkipException();
        }
        CommonLog.warn("当前是重试请求, member_perform_item已被幂等写入 dbExistSize:{}, insert:{}",
                memberPerformItemDOS.size(), items.size());

        context.setItems(memberPerformItemDOS);
    }

    @Override
    public void success(PerformItemContext context) {
        //下游保证批量发放成功
        performDomainService.onFinishPerformItem(context);
    }
}