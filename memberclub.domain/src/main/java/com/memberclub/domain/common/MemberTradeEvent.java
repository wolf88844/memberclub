/**
 * @(#)MemberTradeEvent.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.common;

import com.google.common.collect.ImmutableList;

import java.util.List;

import static com.memberclub.domain.context.purchase.common.MemberOrderStatusEnum.PAYED;
import static com.memberclub.domain.context.purchase.common.MemberOrderStatusEnum.PERFORMED;
import static com.memberclub.domain.context.purchase.common.MemberOrderStatusEnum.PERFORMING;

/**
 * @author yuhaiqiang
 */
public enum MemberTradeEvent {

    MEMBER_ORDER_START_PERFORM("member_order_start_perform", PERFORMING.toInt(),
            ImmutableList.of(PAYED.toInt(), PERFORMING.toInt())),

    MEMBER_ORDER_SUCCESS_PERFORM("member_order_success_perform", PERFORMED.toInt(),
            ImmutableList.of(PERFORMING.toInt(), PERFORMED.toInt()));

    private List<Integer> fromStatus;

    private int toStatus;

    private String name;

    MemberTradeEvent(String name, int toStatus, List<Integer> fromStatus) {
        this.toStatus = toStatus;
        this.name = name;
        this.fromStatus = fromStatus;
    }

    public static MemberTradeEvent findByInt(int value) throws IllegalArgumentException {
        for (MemberTradeEvent item : MemberTradeEvent.values()) {
            if (item.toStatus == value) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid MemberTradeEvent toStatus: " + value);
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int toInt() {
        return this.toStatus;
    }

    public List<Integer> getFromStatus() {
        return fromStatus;
    }

    public MemberTradeEvent setFromStatus(List<Integer> fromStatus) {
        this.fromStatus = fromStatus;
        return this;
    }

    public int getToStatus() {
        return toStatus;
    }

    public MemberTradeEvent setToStatus(int toStatus) {
        this.toStatus = toStatus;
        return this;
    }

    public String getName() {
        return name;
    }

    public MemberTradeEvent setName(String name) {
        this.name = name;
        return this;
    }
}
