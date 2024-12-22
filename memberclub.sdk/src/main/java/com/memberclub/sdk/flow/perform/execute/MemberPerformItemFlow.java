/**
 * @(#)MemberPerformItemFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.flow.perform.execute;

import com.google.common.collect.Lists;
import com.memberclub.common.exception.ResultCode;
import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.flow.SkipException;
import com.memberclub.common.log.CommonLog;
import com.memberclub.domain.common.PerformItemStatusEnum;
import com.memberclub.domain.dataobject.perform.PerformItemContext;
import com.memberclub.domain.dataobject.perform.PerformItemDO;
import com.memberclub.domain.entity.MemberPerformItem;
import com.memberclub.infrastructure.mybatis.mappers.MemberPerformItemDao;
import com.memberclub.sdk.extension.perform.execute.MemberPerformItemExtension;
import com.memberclub.sdk.service.domain.PerformDomainService;
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
                extensionManager.getExtension(context.getPerformContext().toDefaultScene(), MemberPerformItemExtension.class);
        List<MemberPerformItem> items = extension.toMemberPerformItems(context);

        int count = performDomainService.insertMemberPerformItems(items);
        if (count < items.size()) {
            CommonLog.warn("当前请求写入 member_perform_item数量不足 actual:{}, expect:{}, context:{}",
                    count, items.size(), context);
            // TODO: 2024/12/18 兼容异常处理
            List<PerformItemDO> performItemDOS = Lists.newArrayList();
            for (PerformItemDO item : context.getItems()) {
                MemberPerformItem itemInDb =
                        memberPerformItemDao.queryByItemToken(context.getPerformContext().getUserId(), item.getItemToken());
                if (itemInDb == null) {
                    ResultCode.INTERNAL_ERROR.throwException("member_perform_item写入失败,稍后重试");
                }
                if (!PerformItemStatusEnum.hasPerformed(itemInDb.getStatus())) {
                    performItemDOS.add(item);
                }
            }
            if (CollectionUtils.isEmpty(items)) {
                CommonLog.warn("已完成履约:{}", context.getRightType());
                throw new SkipException();
            }
            context.setItems(performItemDOS);
        }
    }

    @Override
    public void success(PerformItemContext context) {
        //下游保证批量发放成功
        performDomainService.itemPerformSuccess(context);
    }
}