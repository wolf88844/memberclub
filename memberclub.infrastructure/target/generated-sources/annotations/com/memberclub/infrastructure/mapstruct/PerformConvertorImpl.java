package com.memberclub.infrastructure.mapstruct;

import com.memberclub.domain.dataobject.MemberSkuSnapshotDO;
import com.memberclub.domain.dto.MemberSkuDTO;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-14T16:08:30+0800",
    comments = "version: 1.4.0.Beta3, compiler: javac, environment: Java 1.8.0_212 (Oracle Corporation)"
)
public class PerformConvertorImpl implements PerformConvertor {

    @Override
    public MemberSkuSnapshotDO toMemberSkuDO(MemberSkuDTO dto) {
        if ( dto == null ) {
            return null;
        }

        MemberSkuSnapshotDO memberSkuSnapshotDO = new MemberSkuSnapshotDO();

        memberSkuSnapshotDO.setBizType( dto.getBizType() );
        memberSkuSnapshotDO.setSkuId( dto.getSkuId() );

        return memberSkuSnapshotDO;
    }
}
