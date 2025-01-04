/**
 * @(#)PurchaseSubmitContext.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.purchase;

import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.dataobject.CommonUserInfo;
import com.memberclub.domain.dataobject.purchase.MemberOrderDO;
import com.memberclub.domain.dataobject.perform.SkuInfoDO;
import com.memberclub.domain.exception.MemberException;
import lombok.Data;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Data
public class PurchaseSubmitContext {

    /*********************************************/
    //基础入参
    private CommonUserInfo userInfo;

    private BizTypeEnum bizType;

    private PurchaseSubmitCmd submitCmd;

    /********************************************/
    //模型数据

    private MemberOrderDO memberOrder;

    private List<SkuInfoDO> skuInfos;

    public PurchaseSubmitContext(PurchaseSubmitCmd cmd) {
        this.submitCmd = cmd;
        this.bizType = cmd.getBizType();
        this.userInfo = cmd.getUserInfo();
    }


    /********************************************/
    public BizScene toDefaultBizScene() {
        return BizScene.of(bizType, submitCmd.getSource().getCode() + "");
    }


    public void monitor() {
        // TODO: 2025/1/4 补充监控
    }

    public void monitorException(MemberException e) {
        // TODO: 2025/1/4 补充监控
    }
}