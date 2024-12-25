/**
 * @(#)ConvertorMethod.java, 十二月 24, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mapstruct;

import com.memberclub.domain.common.PeriodTypeEnum;
import com.memberclub.domain.common.RightTypeEnum;
import org.mapstruct.Named;

/**
 * author: 掘金五阳
 */
public class ConvertorMethod {

    @Named("toPeriodTypeInt")
    public int toPeriodTypeInt(PeriodTypeEnum periodType) {
        return periodType.toInt();
    }

    @Named("toPeriodTypeEnum")
    public PeriodTypeEnum toPeriodTypeEnum(int periodType) {
        return PeriodTypeEnum.findByInt(periodType);
    }

    @Named("toRightTypeEnum")
    public RightTypeEnum toRightTypeEnum(int rightType) {
        return RightTypeEnum.findByInt(rightType);
    }

    @Named("toRightTypeInt")
    public int toRightTypeInt(RightTypeEnum rightType) {
        return rightType.toInt();
    }

}