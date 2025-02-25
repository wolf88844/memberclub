/**
 * @(#)PerformItemContext.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETIAL/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.perform;

import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.perform.execute.ItemGroupGrantResult;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import lombok.Data;

import java.util.List;

/**
 * PerformItemContext 是一个用于描述执行项上下文的类。
 * 该类包含了与执行项相关的业务类型、用户ID、权利类型、交易ID、子交易ID、SKU ID、是否周期性执行等信息，
 * 以及执行项列表和执行结果。此外，提供了一个方法将当前上下文转换为默认的业务场景。
 *
 * @author 掘金五阳
 */
@Data
public class PerformItemContext {

    /**
     * 业务类型，枚举类型 {@link BizTypeEnum}，表示当前执行项所涉及的业务类型。
     */
    private BizTypeEnum bizType;

    /**
     * 用户ID，表示触发执行操作的用户标识。
     */
    private long userId;

    /**
     * 权利类型，表示执行项的权利类型代码。
     */
    private int rightType;

    /**
     * 交易ID，表示与执行项相关的交易标识，通常用于追踪特定交易的操作。
     */
    private String tradeId;

    /**
     * 子交易ID，表示与执行项相关的子交易标识，通常用于更细粒度的追踪。
     */
    private String subTradeId;

    /**
     * SKU ID，表示与执行项相关的商品SKU标识。
     */
    private long skuId;

    /**
     * 是否为周期性执行，表示当前执行项是否为周期性任务。
     */
    private boolean periodPerform;

    /**
     * 执行项列表，包含多个 {@link MemberPerformItemDO} 对象，表示具体的执行项。
     */
    public List<MemberPerformItemDO> items;

    /**
     * 执行结果，包含执行项组授权的结果信息。
     */
    private ItemGroupGrantResult result;

    /**
     * 将当前上下文转换为默认的业务场景。
     * 根据业务类型的代码构建并返回相应的 {@link BizScene} 对象。
     *
     * @return 默认的业务场景对象
     */
    public BizScene toDefaultScene() {
        return BizScene.of(bizType.getCode());
    }
}
