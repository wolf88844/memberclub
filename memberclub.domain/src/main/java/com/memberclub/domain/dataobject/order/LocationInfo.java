/**
 * @(#)MemberOrderLocationInfo.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.order;

import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class LocationInfo {

    /**
     * 实际定位二级城市 Id
     */
    private String actualSecondCityId;

    private String actualThirdCityId;

    /**
     * 勾选的二级城市 Id
     */
    private String selectedSecondCityId;

    private String selectedThirdCityId;

    /**
     * 经度
     */
    private String actualLongitude;

    /**
     * 维度
     */
    private String actualLatitude;

    /**
     * 经度
     */
    private String selectedLongitude;

    /**
     * 维度
     */
    private String selectedLatitude;
}