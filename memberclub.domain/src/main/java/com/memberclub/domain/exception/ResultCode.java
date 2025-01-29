/**
 * @(#)ResultCode.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.exception;

import lombok.Getter;

/**
 * @author yuhaiqiang
 */
public enum ResultCode {

    INTERNAL_ERROR(1, "内部错误", true),
    PARAM_VALID(2, "参数异常", false),
    ORDER_CREATE_ERROR(3, "会员单生成失败", false),
    COMMON_ORDER_SUBMIT_ERROR(4, "提单失败", false),
    CAN_NOT_PERFORM_RETRY(10, "当前状态不允许再次重试履约", false),
    LOCK_ERROR(11, "加锁失败异常", true),
    PERFORM_ITEM_GRANT_ERROR(12, "履约项发放失败", true),
    DEPENDENCY_ERROR(13, "下游接口异常", true),
    DATA_UPDATE_ERROR(14, "数据更新异常", true),
    AFTERSALE_UNABLE_ERROR(15, "不能发起售后异常", false),
    CONFIG_DATA_ERROR(16, "配置数据有误", false),
    PERIOD_PERFORM_TASK_CREATE_ERROR(17, "周期履约任务创建失败", true),
    AFTERSALE_DO_APPLY_ERROR(18, "售后受理执行失败", true),
    EXTRACT_MESSAGE_ERROR(19, "解析消息异常", false),
    PERIOD_PERFORM_EXECUTE_ERROR(20, "周期履约任务执行失败", true),
    INVENTORY_DECREMENT_FAIL(21, "扣减库存失败", false, false),
    INVENTORY_DECREMENT_DUPLICATED(22, "扣减库存重复", false, false),
    INVENTORY_ROLLBACK_INVALID(23, "回补库存无效", false, false),
    INVENTORY_ROLLBACK_FAIL(24, "回补库存失败", true, false),

    ;

    private int value;

    private String msg;

    @Getter
    private boolean success;

    @Getter
    private boolean needRetry;

    ResultCode(int value, String msg, boolean needRetry) {
        this.value = value;
        this.msg = msg;
        this.needRetry = needRetry;
        this.success = false;
    }

    ResultCode(int value, String msg, boolean success, boolean needRetry) {
        this.value = value;
        this.msg = msg;
        this.needRetry = needRetry;
        this.success = success;
    }

    public static ResultCode findByCode(int value) throws IllegalArgumentException {
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

    public String getMsg() {
        return msg;
    }

    public ResultCode setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public int getCode() {
        return this.value;
    }

    public MemberException newException() {
        return MemberException.newException(this);
    }

    public MemberException newException(String msg) {
        return MemberException.newException(this, msg);
    }

    public MemberException newException(String msg, Exception e) {
        return MemberException.newException(this, msg, e);
    }
}
