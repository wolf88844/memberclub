/**
 * @(#)MemberSkuTest.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.OrderSystemTypeEnum;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.SkuPerformContext;
import com.memberclub.domain.dataobject.sku.MemberSkuSnapshotDO;
import com.memberclub.domain.dto.sku.MemberSkuDTO;
import com.memberclub.infrastructure.mapstruct.PerformConvertor;
import org.junit.Test;

/**
 * @author 掘金五阳
 */
public class MemberSkuTest {


    @Test
    public void testMapper() {
        MemberSkuDTO dto = new MemberSkuDTO();
        dto.setBizType(1);
        dto.setSkuId(100001);

        MemberSkuSnapshotDO memberSkuSnapshotDO = PerformConvertor.INSTANCE.toMemberSkuDO(dto);

        System.out.println(memberSkuSnapshotDO);
    }

    @Test
    public void testMapper2() {
        PerformContext context = new PerformContext();
        SkuPerformContext skuPerformContext = new SkuPerformContext();

        context.setBizType(BizTypeEnum.DEMO_MEMBER);
        context.setOrderSystemType(OrderSystemTypeEnum.COMMON_ORDER);
        //MemberPerformHis memberPerformHis = PerformConvertor.INSTANCE.toMemberPerformHis(context, skuPerformContext);
        //System.out.println(memberPerformHis);
    }
}