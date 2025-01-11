/**
 * @(#)DefaultDelayOnceTaskExtension.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.extension.delay.impl;

import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionProvider;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.context.oncetask.common.TaskTypeEnum;
import com.memberclub.domain.context.perform.delay.DelayItemContext;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.dataobject.task.OnceTaskDO;
import com.memberclub.domain.dataobject.task.perform.PerformTaskContentDO;
import com.memberclub.infrastructure.mapstruct.PerformCustomConvertor;
import com.memberclub.sdk.perform.extension.delay.DelayPerformTaskExtension;

import java.util.List;

/**
 * author: 掘金五阳
 */
@ExtensionProvider(desc = "DemoMember 延迟任务扩展点", bizScenes = {
        @Route(bizType = BizTypeEnum.DEFAULT, scenes = SceneEnum.DEFAULT_SCENE)
})
public class DefaultDelayPerformTaskExtension implements DelayPerformTaskExtension {

    @Override
    public OnceTaskDO buildTask(DelayItemContext context, List<MemberPerformItemDO> items) {
        OnceTaskDO task = PerformCustomConvertor.buildTaskForPeriodPerform(context, context.getPerformContext(), items.get(0));
        task.setTaskType(TaskTypeEnum.PERIOD_PERFORM);
        task.setTaskContentClassName(PerformTaskContentDO.class.getName());

        PerformTaskContentDO content = PerformCustomConvertor.buildPerformTaskContent(context, items);

        task.setContent(content);
        return task;
    }

}