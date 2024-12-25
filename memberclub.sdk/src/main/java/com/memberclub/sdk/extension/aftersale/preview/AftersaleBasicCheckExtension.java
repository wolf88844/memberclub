/**
 * @(#)AftersalePreviewCheckExtension.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.extension.aftersale.preview;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.common.log.CommonLog;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.common.MemberOrderStatusEnum;
import com.memberclub.domain.common.RefundStatusEnum;
import com.memberclub.domain.context.aftersale.contant.AftersaleUnableCode;
import com.memberclub.domain.context.aftersale.preview.AftersalePreviewContext;

/**
 * author: 掘金五阳
 */
public interface AftersaleBasicCheckExtension extends BaseExtension {
    default void statusCheck(AftersalePreviewContext context) {
        MemberOrderStatusEnum status = MemberOrderStatusEnum.findByInt(context.getMemberOrder().getStatus());
        RefundStatusEnum refundStatus = RefundStatusEnum.findByInt(context.getMemberOrder().getRefundStatus());


        CommonLog.info("当前订单状态:{}", status.toString(), refundStatus.toString());
        if (status == MemberOrderStatusEnum.REFUNDED) {
            AftersaleUnableCode.REFUNDED.throwException();
        }

        if (MemberOrderStatusEnum.nonPerformed(status.toInt())) {
            AftersaleUnableCode.NON_PERFORMED.throwException();
        }

        if (MemberOrderStatusEnum.PERFORMED == status) {
            if (refundStatus == RefundStatusEnum.ALL_REFUNDED) {
                AftersaleUnableCode.REFUNDED.throwException();
            }
            if (refundStatus == RefundStatusEnum.PORTION_REFUNDED) {
                CommonLog.info("当前会员订单已部分退可以再次申请售后");
            }
        }
    }

    default void getAndCheckPeriod(AftersalePreviewContext context) {
        context.setStime(context.getMemberOrder().getStime());
        context.setEtime(context.getMemberOrder().getEtime());
        if (context.getEtime() < TimeUtil.now()) {
            CommonLog.info("当前订单已过期不能退 stime:{}, etime:{}", context.getStime(), context.getEtime());
            AftersaleUnableCode.EXPIRE_ERROR.throwException();
        }
        CommonLog.info("当前订单有效期 stime:{}, etime:{}", context.getStime(), context.getEtime());
    }
}