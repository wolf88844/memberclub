/**
 * @(#)TestSwitchEnum.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import com.memberclub.sdk.config.SwitchEnum;
import org.junit.Test;

/**
 * @author yuhaiqiang
 */
public class TestSwitchEnum {

    @Test
    public void test() {
        int timeout = SwitchEnum.LOCK_TIMEOUT_SECONDS.getInt(1);
        System.out.println(timeout);
    }
}