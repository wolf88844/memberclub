/**
 * @(#)InitialSkuPerformContextsFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.flow.build;

import com.google.common.collect.Lists;
import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.SubOrderPerformContext;
import com.memberclub.domain.context.perform.common.SubOrderPerformStatusEnum;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.dataobject.perform.MemberSubOrderDO;
import com.memberclub.domain.dataobject.perform.SkuInfoDO;
import com.memberclub.domain.dataobject.sku.SkuPerformItemConfigDO;
import com.memberclub.domain.entity.MemberSubOrder;
import com.memberclub.infrastructure.id.IdGenerator;
import com.memberclub.infrastructure.id.IdTypeEnum;
import com.memberclub.infrastructure.mapstruct.PerformConvertor;
import com.memberclub.sdk.perform.extension.build.PerformItemCalculateExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Service
public class InitialSkuPerformContextsFlow extends FlowNode<PerformContext> {

    @Autowired
    private ExtensionManager extensionManager;

    @Override
    public void process(PerformContext context) {
        List<SkuInfoDO> details = context.getSkuBuyDetails();

        List<SubOrderPerformContext> subOrderPerformContexts = Lists.newArrayList();
        for (SkuInfoDO detail : details) {
            SubOrderPerformContext subOrderPerformContext = new SubOrderPerformContext();
            subOrderPerformContext.setSkuInfo(detail);
            MemberSubOrderDO his = PerformConvertor.INSTANCE.toSubOrderDO(context);
            subOrderPerformContext.setSubOrder(his);

            his.setPerformStatus(SubOrderPerformStatusEnum.INIT);
            his.setSkuId(detail.getSkuId());
            his.setBuyCount(detail.getBuyCount());
            his.setCtime(TimeUtil.now());
            his.setUtime(TimeUtil.now());
            Long subTradeId = buildSubTradeId(context, detail);
            his.setSubTradeId(subTradeId);

            PerformItemCalculateExtension calculateExtension =
                    extensionManager.getExtension(context.toDefaultScene(), PerformItemCalculateExtension.class);

            List<MemberPerformItemDO> items = Lists.newArrayList();
            for (SkuPerformItemConfigDO config : detail.getPerformConfig().getConfigs()) {
                MemberPerformItemDO item = calculateExtension.toPerformItem(config);
                item.setSkuId(detail.getSkuId());
                items.add(item);
            }
            subOrderPerformContext.setImmediatePerformItems(items);
            subOrderPerformContexts.add(subOrderPerformContext);
        }
        context.setSubOrderPerformContexts(subOrderPerformContexts);
        if (context.getBaseTime() == 0) {
            context.setBaseTime(TimeUtil.now());
            context.getCmd().setBaseTime(context.getBaseTime());
        }
    }

    private Long buildSubTradeId(PerformContext context, SkuInfoDO detail) {
        MemberSubOrder hisFromDb = context.matchHisFromDb(detail.getSkuId());
        if (hisFromDb != null) {
            return hisFromDb.getSubTradeId();
        } else {
            IdGenerator idGenerator = extensionManager.getExtension(context.toDefaultScene(),
                    IdGenerator.class);
            Long subTradeId = idGenerator.generateId(IdTypeEnum.PERFORM_HIS);
            return subTradeId;
        }
    }
}