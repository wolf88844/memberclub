---
theme: cyanosis
---
最近五阳写了一个付费会员中台 开源项目，欢迎大家一起探讨学习

项目地址：https://gitee.com/juejinwuyang/memberclub


# MemberClub 简述
常见的会员共有以下几种形式 https://mp.weixin.qq.com/s/41dvqjqD9NgsOQvJvHP2wQ  付费会员的主要业务形态是：捆绑多种极具折扣和优惠力度的权益在会员商品中，用户购买后在一段时间内可享受会员权益，包括不限于：各位折扣力度大的优惠券、会员折扣价、会员专属内容，等会员专属权益。权益类型也可简单分为资产类（如券）、资格类（专属内容）。

从会员运营角度看，会员的不同权益是可以自由组合的，不同人群可看到不同的权益、享受价格不同。因此应当将会员视为商品，商品中包含多类权益。

付费会员中台应提供会员商品的交易解决方案，在各类购买场景下提供各类会员商品形态的履约及售后结算能力。同时会员交易是电商交易系统的一个细分场景，它抽象了会员等虚拟商品的业务共性，同时对业务差异性预置了扩展点，通过插件方便扩展。此外电商交易系统中 营销优惠、收银支付、资金结算等非会员独有领域 应依托于其他业务中台。

# 领域划分和领域能力
付费会员中台领域划分包括 购买域、履约域、售后域和预结算域
## 购买域
购买域需提供提交订单、预览订单、取消订单的业务能力，需提供续费购买、自动续费、先享后付、不回本包退、随单搭售、直购、兑换码购买等多样化的购买能力。领域能力则包括会员开通单能力、库存能力、购买配额能力、会员新客能力等。
## 履约域
履约域需提供主单履约、主单逆向履约、周期履约的业务能力，领域能力上包括履约单管理、履约接单完单能力、履约拆合单能力、权益发放能力、周期发放能力等。
## 售后域
售后域需提供售后预览、售后提交的业务能力。领域能力上包括售后可退校验能力、售后金额计算能力、过期退能力、随单退、售后次数限额能力，支持在续费、自动续费、直购、搭售等购买场景的售后。

## 预结算域
会员交易订单在多个时点需要进行结算，包括交易结算和离线收入报账，预结算提供了支付完成、履约、退款、过期等多个时代结算事件通知能力。
# 系统架构
## 业务能力和领域能力
系统设计时应区分业务能力和领域能力，业务能力是指系统对外部提供的业务能力，不可再细分，如购买域需提供购买预览、购买提单、取消等业务能力。领域能力则为实现该业务能力所必须的能力，如购买域在提单阶段需扣减商品库存、记录用户购买配额数据、记录会员新客标签、记录会员单等，这类系统能力视为领域能力。

为什么要区分业务能力和领域能力呢？
## 可编排的流程
业务中台要负责承接各类业务形态相似的业务系统，一般情况下业务能力是业务必选的能力，中台负责对外提供的标准 API（也可以由中台提供端到端的接入），而领域能力在不同的产品线上所需不同。如部分产品线不需要库存、购买配额等能力，不需要年卡等周期卡履约能力等。

业务中台如何抽象业务共性、隔离业务差异性、提供快速可靠的扩展机制，是中台建设的重点和难点。业界常见的做法是

1） 抽象共用的业务逻辑为领域能力。

2） 前瞻性的预置扩展点，业务通过插件进行差异化能力扩展

3） 通过流程引擎编排流程，实现流程的差异化配置，实现业务流程的可视化和扁平化。

## 扩展点插件
扩展点在业务中台中无处不在，这是因为业务中台要承接的业务太多，很难保证所有业务完全相同。而业务差异性部分又不能叠罗汉式堆叠在主流程中，因为这意味 **影响点扩散导致业务隔离性差。即业务特性配置在主流程中，势必潜在影响其他业务。久而久之，系统必定成为雷场，到处是地雷，难以梳理维护。** 外加系统架构腐化后的破窗效应，会让后来者倾向于在屎山代码继续堆屎！连重构都变得困难，最终难以收场。

扩展点插件通过业务线和业务域两层路由，在运行时委托某一业务的插件执行业务逻辑，系统主流程依然保持简洁和扁平，改动其中一个业务，完全不会影响到其他业务，系统扩展性和隔离性大大增强！

## 状态驱动
在实现各个领域能力时，应该依托于领域模型的状态变更执行业务逻辑。如会员交易锁领域能力在预提单、提单成功、预取消、取消成功、履约和履约成功等各个业务状态下 实现锁的领域能力。

在购买、履约、售后等主流程中，各领域能力封装到流程节点中，由流程引擎负责编排执行。
## 模型驱动
会员C 端交易流程要解决以下问题：C端高并发低延迟、业务复杂、数据一致性要求高、资金安全等，因此应尽可能的保证 C 端系统流程设计的简洁。

如C 端在实现库存扣减、用户配额记录、年卡履约等领域能力时，应由商品模型驱动业务流程，而非 C 端根据不同条件决定是否 做某件事，尽可能简化 C 端业务逻辑。 商品模型应实现 BC端模型分离。



# 技术架构
在 memberclub 项目中你可以学习到
## 条件注入
为了提供更好的扩展点，memberclub 对各类基础组件、外部存储系统依赖均抽象了接口，不同接口实现类可通过 Spring 条件注入到系统。如你希望自己重写分布式锁组件，那么你可以上线分布式锁接口，将其注入到 Spring 中，就可以直接替换掉原有的分布式锁组件，无需改动原有代码。

在单元测试和standalone模式下，系统不应该依赖各类外部存储系统，因此通过抽象各个基础组件为通用接口，在不同的环境先可配置不同的实现类注入到 Spring，保证了系统在单测和 Standalone 模型下的独立运营。

## 分布式锁组件
系统提供了 本地组件和 Redis lua 组件。
```
public interface DistributeLock {

    boolean lock(String key, Long value, int timeSeconds);

    boolean unlock(String key, Long value);
}
```
## 重试组件

被标注了 Retryable 注解的方法，当抛出异常后，系统可自动重试。重试依然失败，失败请求将自动投递到延迟队列，进行分布式重试 。
```
@Retryable(maxTimes = 5, initialDelaySeconds = 1, maxDelaySeconds = 30, throwException = true)
@Override
public void process(AfterSaleApplyContext context) {
    try {
        extensionManager.getExtension(context.toBizScene(),
                AfterSaleApplyExtension.class).doApply(context);
    } catch (Exception e) {
        CommonLog.error("售后受理流程异常 context:{}", context, e);
        throw new AftersaleDoApplyException(AFTERSALE_DO_APPLY_ERROR, e);
    }
}
```
## 延迟队列组件
延迟队列组件提供延迟事件触发能力，如异常重试场景往往需要延迟一段时间后再次重试，此时将请求投递到延迟队列可实现延迟重试。

系统实现了本地 DelayQueue 和 Redison 、Rabbitmq 延迟队列能力。
## 分布式 ID 组件

系统提供了本地 RandomUtils 和 Redisson 分布式 ID 组件，你可以接入基于雪花算法的分布式 ID 系统。
```
@ExtensionConfig(desc = "分布式 ID 生成扩展点", type = ExtensionType.COMMON, must = true)
public interface IdGenerator extends BaseExtension {

    public Long generateId(IdTypeEnum idType);
}
```

## 通用任务表触发组件
周期履约、结算过期、售后过期退等场景，均需要系统有一个指定时间大批量任务触发能力。通过抽象通用任务表，实现通用任务延迟触发能力。
## mybatis-plus
集成 mybatis-plus，在数据模型更新时可通过 UpdateWrapper等由各业务线自行扩展，无需手动编写 SQL，扩展性更强！

同时系统实现了 insert ignore  批量插入能力。

## sharding-jdbc
通过集成 sharding-jdbc 实现了多数据源的分库分表能力。 使用者自行定义分表规则即可

## RabbitMQ接入和重试
集成了rabbitmq，并且基于死信队列实现了消息消费的延迟重试能力！

## Redisson
集成 Redisson
## Redis Lua
集成了 RedisTemplate，其中库存更新、用户标签写入更新、分布式锁部分实现通过 RedisLua 脚本实现。

## Apollo接入

系统集成了 Apollo 作为配置中心，同时系统提供了配置中心的接口，并内置了本地Map和 Apollo 两种组件
```
public interface DynamicConfig {

    boolean getBoolean(String key, Boolean value);

    int getInt(String key, int value);

    long getLong(String key, long value);

    String getString(String key, String value);
}
```

##  内存数据库和单元测试
集成了 H2 内存数据库，在开发阶段使用 单元测试 profile  执行，访问内存数据库，不会污染测试环境数据库！
# 工程目录结构
```
memberclub                                        # 主项目①pom.xml
├── starter                              # 管理后台项目②pom.xml(基于Ruoyi框架二次开发)
├── common                                # Maven项目bom模块
├── sdk                             # 公共的工具类模块
├── domain                             # 公共的工具类模块
├── plugin.demomember                             # 公共的工具类模块
├── infrastruce                               # 任务调度相关的核心类（如枚举类、接口定义、接口参数等）
├── starter                           # 任务派发模块
│   ├── controller                   # 任务派发的抽象接口层
│   ├── job                  # 任务派发的Http实现
│   └── mq                 # 任务派发的Redis实现
├── domain                                 # 分布式ID生成模块
├── sdk                           # Server(Supervisor & Worker)注册模块
│   ├──                    # Server注册中心的抽象接口层
│   ├── disjob-registry-consul                # Server注册中心：Consul实现
│   ├── disjob-registry-database              # Server注册中心：Database实现
│   ├── disjob-registry-etcd                  # Server注册中心：Etcd实现
│   ├── disjob-registry-nacos                 # Server注册中心：Nacos实现
│   ├── disjob-registry-redis                 # Server注册中心：Redis实现
│   └── disjob-registry-zookeeper             # Server注册中心：Zookeeper实现
├── common                            # 聚合各个模块的测试覆盖率报告
├── infrastructure                            # Samples项目③pom.xml
│   ├── disjob-samples-conf-common            # Samples公共配置（log4j2.xml）
│   ├── disjob-samples-conf-supervisor        # Samples Supervisor配置
│   ├── disjob-samples-conf-worker            # Samples Worker配置
│   ├── disjob-samples-frameless-worker       # Worker单独部署的范例（普通Java-main应用）
│   ├── disjob-samples-springboot-common      # Samples Spring-boot公共模块
│   ├── disjob-samples-springboot-merged      # Supervisor与Worker合并部署的范例（Spring-boot应用）
│   ├── disjob-samples-springboot-supervisor  # Supervisor单独部署的范例（Spring-boot应用）
│   └── disjob-samples-springboot-worker      # Worker单独部署的范例（Spring-boot应用）
├── plugin.demomember                         # Supervisor代码
```