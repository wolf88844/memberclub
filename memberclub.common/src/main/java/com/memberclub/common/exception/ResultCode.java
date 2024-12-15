/**
 * @(#)ResultCode.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.exception;

/**
 * @author yuhaiqiang
 */
public enum ResultCode {

    INTERNAL_ERROR(1, "内部错误"),
    CAN_NOT_PERFORM_RETRY(10, "当前状态不允许再次重试履约"),
    LOCK_ERROR(11, "加锁失败异常"),
    PERFORM_ITEM_GRANT_ERROR(12, "履约项发放失败");

    private int value;

    private String msg;

    ResultCode(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public static ResultCode findByInt(int value) throws IllegalArgumentException {
        for (ResultCode item : ResultCode.values()) {
            if (item.value == value) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid ResultCode value: " + value);
    }

    @Override
    public String toString() {
        return this.msg;
    }

    public int getValue() {
        return value;
    }

    public ResultCode setValue(int value) {
        this.value = value;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResultCode setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public int toInt() {
        return this.value;
    }

    public void throwException() {
        MemberException.throwException(this);
    }
}
