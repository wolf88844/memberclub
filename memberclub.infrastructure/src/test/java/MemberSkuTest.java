/**
 * @(#)MemberSkuTest.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import com.memberclub.domain.dataobject.MemberSkuSnapshotDO;
import com.memberclub.domain.dto.MemberSkuDTO;
import com.memberclub.infrastructure.mapstruct.PerformConvertor;
import org.junit.Test;

/**
 * @author yuhaiqiang
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
}