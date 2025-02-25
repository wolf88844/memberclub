/**
 * @(#)MemberPerformItemDO.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.perform;

import com.memberclub.domain.context.perform.PerformItemContext;
import com.memberclub.domain.context.perform.common.GrantTypeEnum;
import com.memberclub.domain.context.perform.common.PerformItemStatusEnum;
import com.memberclub.domain.context.perform.common.PeriodTypeEnum;
import com.memberclub.domain.context.perform.common.RightTypeEnum;
import com.memberclub.domain.dataobject.perform.item.PerformItemExtraInfo;
import lombok.Data;

import java.util.Objects;

/**
 * MemberPerformItemDO 是一个用于表示会员执行项的数据对象。
 * 该类包含与执行项相关的各种信息，如SKU ID、权利类型、授权类型、周期类型等，
 * 并提供了比较方法和一些辅助方法来处理执行项的状态和属性。
 *
 * @author 掘金五阳
 */
@Data
public class MemberPerformItemDO implements Comparable<MemberPerformItemDO> {

    /**
     * 商品SKU ID，表示与执行项相关的商品标识。
     */
    private long skuId;

    /**
     * 权利ID，表示执行项的权利标识。
     */
    private long rightId;

    /**
     * 权利类型，枚举类型 {@link RightTypeEnum}，表示执行项的权利类型。
     */
    private RightTypeEnum rightType;

    /**
     * 执行项令牌，用于唯一标识执行项。
     */
    private String itemToken;

    /**
     * 批次代码，表示执行项所属的批次。
     */
    private String batchCode;

    /**
     * 资产数量，表示执行项涉及的资产数量。
     */
    private int assetCount;

    /**
     * 子交易ID，表示与执行项相关的子交易标识。
     */
    private String subTradeId;

    /**
     * 阶段，默认值为1，表示执行项的阶段或批次。
     */
    private int phase = 1;

    /**
     * 周期，表示执行项的周期数。
     */
    private int cycle;

    /**
     * 购买索引，默认值为1，表示购买顺序或索引。
     */
    private int buyIndex = 1;

    /**
     * 授权类型，枚举类型 {@link GrantTypeEnum}，表示执行项的授权类型（发放或激活）。
     */
    private GrantTypeEnum grantType;

    /**
     * 提供商ID，表示执行项的提供商标识。
     */
    private int providerId;

    /**
     * 扩展信息，默认初始化为新的 {@link PerformItemExtraInfo} 对象，用于存储额外的执行项信息。
     */
    private PerformItemExtraInfo extra = new PerformItemExtraInfo();

    /**
     * 周期次数，表示执行项的周期次数。
     */
    private int periodCount;

    /**
     * 周期类型，枚举类型 {@link PeriodTypeEnum}，表示执行项的周期类型。
     */
    private PeriodTypeEnum periodType;

    /**
     * 开始时间，表示执行项的开始时间戳。
     */
    private long stime;

    /**
     * 结束时间，表示执行项的结束时间戳。
     */
    private long etime;

    /**
     * 更新时间，表示执行项的最后更新时间戳。
     */
    private long utime;

    /**
     * 执行状态，枚举类型 {@link PerformItemStatusEnum}，表示执行项的当前状态。
     */
    private PerformItemStatusEnum status;

    /**
     * 实现 {@link Comparable} 接口，根据阶段（phase）进行比较。
     *
     * @param o 另一个 {@link MemberPerformItemDO} 对象
     * @return 如果当前对象的阶段小于参数对象的阶段返回-1，否则返回1
     */
    @Override
    public int compareTo(MemberPerformItemDO o) {
        return Integer.compare(this.phase, o.getPhase());
    }

    /**
     * 标记执行项为成功完成，并更新更新时间为当前时间戳。
     *
     * @param context 执行上下文
     */
    public void onFinishPerform(PerformItemContext context) {
        this.status = PerformItemStatusEnum.PERFORM_SUCCESS;
        this.utime = System.currentTimeMillis();
    }

    /**
     * 判断执行项是否可结算。
     * 返回扩展信息中结算信息的 financeable 属性是否为 true。
     *
     * @return 如果扩展信息中的 financeable 属性为 true，则返回 true；否则返回 false
     */
    public boolean isFinanceable() {
        return Objects.nonNull(extra.getSettleInfo()) && Boolean.TRUE.equals(extra.getSettleInfo().getFinanceable());
    }
}
