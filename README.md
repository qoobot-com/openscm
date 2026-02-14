# OpenSCM 供应链管理系统

基于 Spring Boot 3.5.10 + Java 21 构建的企业级开源供应链管理系统。

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.10-green.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Progress](https://img.shields.io/badge/Progress-100%25-brightgreen.svg)](docs/项目进度.md)

## 项目简介

OpenSCM 是一个现代化的企业级供应链管理系统（SCM），旨在为企业提供完整的供应链管理解决方案。系统采用微服务架构设计，支持供应商管理、采购管理、库存管理、订单管理、物流管理、财务管理、认证授权和第三方集成等核心业务模块，帮助企业实现供应链数字化转型，提升管理效率。

## 核心特性

### 架构特性
- **模块化架构**: 采用多模块设计，业务模块解耦，易于扩展和维护
- **多租户支持**: 支持多租户数据隔离，适合SaaS场景
- **微服务友好**: 模块可独立部署，支持微服务架构演进
- **统一认证授权**: 基于 Spring Security + JWT 的安全认证机制
- **RBAC权限管理**: 灵活的角色权限管理体系，支持细粒度权限控制
- **操作日志审计**: 完整的操作日志记录和查询功能

### 功能特性
- **供应商管理**: 供应商信息管理、资质审核、绩效评估、黑名单管理
- **采购管理**: 采购计划、采购订单、采购入库、供应商询价、统计分析
- **库存管理**: 仓库信息、出入库管理、库存盘点、库存预警、库存统计
- **订单管理**: 销售订单、订单支付、订单发货、订单退货、订单统计
- **物流管理**: 运输计划、配送管理、物流费用、物流跟踪、物流统计
- **财务管理**: 应收应付、收付款、成本核算、财务报表、费用管理
- **认证授权**: OAuth2.0认证、多租户、第三方登录、登录安全增强
- **第三方集成**: ERP/WMS/TMS集成、电子发票、支付集成、消息集成

### 技术特性
- **文件管理**: 支持文件上传下载、类型校验、用户隔离
- **定时任务**: 内置定时任务调度框架
- **数据字典**: 统一的数据字典管理，支持缓存
- **API文档**: 集成 SpringDoc OpenAPI，自动生成API文档
- **单元测试**: 完善的单元测试框架，覆盖核心业务逻辑
- **Redis缓存**: 支持数据缓存，提升系统性能

## 技术栈

| 组件 | 版本 | 说明 |
|------|------|------|
| Java | 21 | JDK 21 LTS |
| Spring Boot | 3.5.10 | 核心框架 |
| Spring Security | 3.5.10 | 安全框架 |
| Spring Data Redis | 3.5.10 | Redis集成 |
| Spring Scheduling | 3.5.10 | 定时任务 |
| MySQL | 8.0+ | 关系型数据库 |
| MyBatis-Plus | 3.5.7 | ORM框架 |
| Redis | 7.0+ | 缓存数据库 |
| JWT | 0.12.3 | Token认证 |
| Hutool | 5.8.23 | 工具库 |
| Lombok | 1.18.42 | 代码简化 |
| SpringDoc | 2.3.0 | API文档 |
| JUnit 5 | 5.10.0 | 单元测试 |
| Mockito | 5.7.0 | Mock测试 |

## 项目结构

```
openscm/
├── openscm-common/          # 公共模块（常量、工具类、配置等）
│   ├── constant/           # 常量定义
│   ├── result/             # 统一响应结果封装
│   ├── exception/          # 全局异常处理
│   ├── model/              # 基础实体类
│   ├── config/             # 配置类（Security、MyBatis-Plus、Redis等）
│   ├── annotation/         # 自定义注解（@OperationLog等）
│   ├── aspect/             # AOP切面
│   ├── service/            # 公共服务接口（文件服务、日志服务）
│   ├── security/           # 安全相关（JWT、Filter、Handler）
│   └── util/               # 工具类（JwtUtils、SecurityUtils）
├── openscm-api/             # API接口模块
├── openscm-auth/            # 认证授权模块
├── openscm-system/          # 系统管理模块
│   ├── entity/             # 用户、角色、菜单、部门、字典、日志等实体
│   ├── mapper/             # MyBatis Mapper接口
│   ├── dto/                # 数据传输对象
│   ├── vo/                 # 视图对象
│   ├── service/            # 服务接口和实现
│   ├── schedule/           # 定时任务
│   ├── controller/         # REST控制器
│   └── resources/mapper/   # MyBatis XML映射
├── openscm-supplier/        # 供应商管理模块
│   ├── entity/             # 供应商、分类、绩效、黑名单实体
│   ├── mapper/             # Mapper接口
│   ├── dto/                # 供应商DTO
│   ├── vo/                 # 供应商VO、统计VO
│   ├── service/            # 供应商服务
│   └── controller/         # 供应商、分类、绩效、黑名单控制器
├── openscm-purchase/        # 采购管理模块
│   ├── entity/             # 采购计划、订单、入库、询价实体
│   ├── mapper/             # Mapper接口
│   ├── dto/                # 采购DTO
│   ├── vo/                 # 采购VO、统计VO
│   ├── service/            # 采购服务
│   └── controller/         # 采购控制器
├── openscm-inventory/       # 库存管理模块
│   ├── entity/             # 仓库、库存、出入库、盘点实体
│   ├── mapper/             # Mapper接口
│   ├── service/            # 库存服务
│   └── controller/         # 库存控制器
├── openscm-order/           # 订单管理模块
│   ├── entity/             # 销售订单、支付、发货、退货实体
│   ├── mapper/             # Mapper接口
│   ├── service/            # 订单服务
│   └── controller/         # 订单控制器
├── openscm-logistics/       # 物流管理模块
│   ├── entity/             # 运输计划、配送、跟踪、费用实体
│   ├── mapper/             # Mapper接口
│   ├── service/            # 物流服务
│   └── controller/         # 物流控制器
├── openscm-finance/         # 财务管理模块
│   ├── entity/             # 应收应付、收付款、成本、报表实体
│   ├── mapper/             # Mapper接口
│   ├── service/            # 财务服务
│   └── controller/         # 财务控制器
├── openscm-integration/     # 系统集成模块
│   ├── entity/             # ERP/WMS/TMS、发票、支付、消息实体
│   ├── mapper/             # Mapper接口
│   ├── service/            # 集成服务
│   └── controller/         # 集成控制器
├── openscm-admin/           # 管理后台启动模块
├── docs/                    # 项目文档
│   └── 项目进度.md         # 项目进度报告
├── sql/                     # 数据库脚本
│   ├── init.sql            # 系统初始化脚本
│   ├── supplier_management.sql    # 供应商管理表
│   ├── purchase_management.sql    # 采购管理表
│   ├── inventory_management.sql   # 库存管理表
│   ├── order_management.sql      # 订单管理表
│   ├── logistics_management.sql   # 物流管理表
│   ├── financial_management.sql  # 财务管理表
│   └── integration_module.sql     # 集成模块表
├── pom.xml                 # 父POM配置
└── README.md               # 项目说明文档
```

## 开发进度

| 阶段 | 状态 | 完成度 |
|------|------|--------|
| 第一阶段：项目架构搭建 | ✅ 已完成 | 100% |
| 第二阶段：基础设施与公共模块 | ✅ 已完成 | 100% |
| 第三阶段：供应商管理系统 | ✅ 已完成 | 100% |
| 第四阶段：采购管理系统 | ✅ 已完成 | 100% |
| 第五阶段：库存管理系统 | ✅ 已完成 | 100% |
| 第六阶段：订单管理系统 | ✅ 已完成 | 100% |
| 第七阶段：物流管理系统 | ✅ 已完成 | 100% |
| 第八阶段：财务管理系统 | ✅ 已完成 | 100% |
| 第九阶段：认证授权模块 | ✅ 已完成 | 100% |
| 第十阶段：第三方集成模块 | ✅ 已完成 | 100% |

**总体进度**: 100% (10/10 阶段完成)

详细的开发进度请参考 [项目进度](docs/项目进度.md)

## 功能模块

### 系统管理
- **用户管理**: 用户CRUD、密码修改、用户启用/禁用
- **角色管理**: 角色CRUD、菜单权限分配、角色启用/禁用
- **菜单管理**: 菜单CRUD、菜单树形结构
- **部门管理**: 部门CRUD、部门树形结构
- **数据字典**: 字典类型管理、字典数据管理、Redis缓存
- **操作日志**: 基于注解的操作日志记录、异步保存、查询分析

### 认证授权
- **JWT Token认证**: Token生成、验证、刷新
- **用户登录/登出**: 用户认证、登出清理
- **密码加密**: BCrypt加密存储
- **权限验证**: 基于注解的权限控制
- **OAuth2.0认证**: 支持OAuth2.0标准认证
- **单点登录(SSO)**: 支持单点登录
- **多租户支持**: 租户隔离、租户配置、租户数据隔离
- **第三方登录**: 微信登录、钉钉登录、企业微信登录
- **登录安全增强**: 多因素认证、登录限制、审计日志

### 供应商管理
- **供应商信息**: 供应商CRUD、资质管理、联系方式
- **供应商审核**: 资质审核流程、审核状态管理
- **供应商分类**: 分类CRUD、多级分类管理
- **绩效评估**: 质量/交付/服务评分、自动计算总分
- **黑名单管理**: 添加/解除/删除黑名单
- **数据统计**: 供应商总数、待审核、已通过、已拒绝统计

### 采购管理
- **采购计划**: 计划创建、提交审批、审批流程、计划执行
- **采购订单**: 订单CRUD、订单状态流转、订单确认/取消/完成
- **采购入库**: 入库单创建、质检、入库确认、库存更新
- **供应商询价**: 询价单创建、供应商报价、报价对比
- **统计分析**: 采购数据统计、成本分析、供应商分析

### 库存管理
- **仓库信息**: 仓库CRUD、仓库类型管理
- **货位管理**: 货位CRUD、货位属性管理
- **物料管理**: 物料主数据、物料分类
- **出入库管理**: 入库单/出库单创建、出入库确认、库存更新
- **库存查询**: 库存查询、库存明细、库存汇总
- **库存盘点**: 盘点计划、盘点执行、盘点差异处理
- **库存预警**: 上下限预警、呆滞料预警
- **统计分析**: 库存周转率、库存结构分析

### 订单管理
- **销售订单**: 订单CRUD、订单状态管理、订单审核
- **订单支付**: 支付记录、支付确认、支付状态查询
- **订单发货**: 发货记录、物流跟踪、签收管理
- **订单退货**: 退货申请、退货审核、退款处理
- **订单统计**: 订单数据统计、金额统计、订单分析

### 物流管理
- **运输计划**: 运输计划创建、调度、执行、完成
- **配送管理**: 配送单管理、配送跟踪、配送签收
- **物流费用**: 费用计算、费用结算、费用分析
- **物流跟踪**: 物流轨迹、物流节点、异常预警
- **统计分析**: 物流数据统计、时效分析、成本分析

### 财务管理
- **应收应付**: 应收账款、应付账款、对账管理
- **收付款管理**: 收款单、付款单、票据管理
- **成本核算**: 采购成本、销售成本、库存成本核算
- **财务报表**: 利润表、资产负债表、现金流量表
- **费用管理**: 费用报销、费用预算、费用分析

### 第三方集成
- **ERP集成**: SAP、Oracle、用友、金蝶等ERP系统对接
- **WMS集成**: 富勒、唯智、百世、顺丰等WMS系统对接
- **TMS集成**: oTMS、运去哪、路歌等TMS系统对接
- **电子发票**: 航天信息、百望云、票易通等开票平台集成
- **支付集成**: 微信支付、支付宝、银联支付集成
- **消息集成**: 邮件、短信、站内信、微信通知

### 公共服务
- **文件管理**: 文件上传下载、类型校验、大小限制、用户隔离
- **定时任务**: 日志清理、健康检查、日报生成、缓存预热
- **消息通知**: 系统通知、邮件通知、短信通知

## 快速开始

### 环境要求

- JDK 21+
- Maven 3.9+
- MySQL 8.0+
- Redis 7.0+ (可选)

### 数据库初始化

1. 创建数据库：
```sql
CREATE DATABASE openscm DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 按顺序执行初始化脚本：
```bash
# 1. 系统基础表
mysql -u root -p openscm < sql/init.sql

# 2. 供应商管理表
mysql -u root -p openscm < sql/supplier_management.sql

# 3. 采购管理表
mysql -u root -p openscm < sql/purchase_management.sql

# 4. 库存管理表
mysql -u root -p openscm < sql/inventory_management.sql

# 5. 订单管理表
mysql -u root -p openscm < sql/order_management.sql

# 6. 物流管理表
mysql -u root -p openscm < sql/logistics_management.sql

# 7. 财务管理表
mysql -u root -p openscm < sql/financial_management.sql

# 8. 集成模块表
mysql -u root -p openscm < sql/integration_module.sql
```

### 配置修改

修改 `openscm-admin/src/main/resources/application-dev.yml` 中的数据库和Redis连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/openscm?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    username: your_username
    password: your_password
  data:
    redis:
      host: localhost
      port: 6379
      password:
      database: 0
```

### 启动项目

1. 编译项目：
```bash
mvn clean install
```

2. 启动应用：
```bash
cd openscm-admin
mvn spring-boot:run
```

3. 访问：

| 服务 | 地址 |
|------|------|
| 应用地址 | http://localhost:8080 |
| API文档 | http://localhost:8080/swagger-ui.html |
| API文档JSON | http://localhost:8080/api-docs |

### 默认账号

- 用户名: admin
- 密码: admin123

### 打包部署

```bash
# 打包（跳过测试）
mvn clean package -DskipTests

# 运行
java -jar openscm-admin/target/openscm-admin-1.0.0-SNAPSHOT.jar
```

## API 接口文档

### 认证管理 (/api/auth)
- `POST /api/auth/login` - 用户登录
- `POST /api/auth/logout` - 用户退出
- `GET /api/auth/info` - 获取当前用户信息

### 系统管理
- 用户管理、角色管理、菜单管理、部门管理、数据字典、操作日志、文件管理

### 供应商管理
- 供应商信息、供应商分类、绩效评估、黑名单

### 采购管理
- 采购计划、采购订单、采购入库、供应商询价、采购统计

### 库存管理
- 仓库管理、库存查询、出入库管理、库存盘点

### 订单管理
- 销售订单、订单支付、订单发货、订单退货

### 物流管理
- 运输计划、配送管理、物流跟踪

### 财务管理
- 应收应付、收付款、财务报表

### 第三方集成
- ERP集成、WMS集成、TMS集成、电子发票、支付集成、消息集成

完整的API文档请访问 Swagger UI：http://localhost:8080/swagger-ui.html

## 项目统计

| 指标 | 数量 |
|------|------|
| 模块数 | 12 |
| 实体类 | 60+ |
| Mapper | 60+ |
| Service | 50+ |
| Controller | 50+ |
| API 接口 | 150+ |
| 配置类 | 10+ |
| 工具类 | 3+ |
| 注解 | 1+ |
| 切面 | 1+ |
| 测试类 | 30+ |
| 定时任务 | 4+ |
| 数据库表 | 50+ |

## 功能亮点

### 1. JWT 认证集成
- JWT认证过滤器自动验证Token
- 统一的认证失败处理
- 统一的权限拒绝处理
- 当前登录用户信息获取
- Token自动过期和刷新

### 2. 操作日志完善
- 基于注解的操作日志记录
- 异步保存到数据库，不影响主流程
- 完整的日志查询接口
- 支持按时间、类型、用户等条件查询

### 3. 文件上传下载
- 文件类型校验（支持常见图片、文档格式）
- 文件大小限制
- 用户隔离存储（按用户ID分目录）
- 按日期自动分目录
- 支持文件删除和存在性检查

### 4. 定时任务调度
- 每天清理过期日志（保留30天）
- 每小时系统健康检查
- 每天生成系统日报
- 每5分钟缓存预热
- 支持动态添加定时任务

### 5. 供应商管理系统
- 供应商信息CRUD（含审核流程）
- 供应商资质审核（待审核/已通过/已拒绝）
- 供应商分类管理（多级分类）
- 供应商绩效评估（自动计算质量/交付/服务评分）
- 供应商黑名单管理（添加/解除/删除）
- 供应商数据统计（总数、待审核、已通过、已拒绝）

### 6. 数据字典管理
- 字典类型管理
- 字典数据管理
- Redis缓存提升性能
- 支持按类型和值查询标签

### 7. RBAC权限管理
- 角色管理
- 菜单管理
- 角色菜单权限分配
- 权限验证

### 8. 多租户支持
- 租户隔离
- 租户配置
- 租户数据隔离
- 支持SaaS场景

### 9. 第三方集成
- ERP系统集成（SAP、Oracle、用友、金蝶）
- WMS系统集成（富勒、唯智、百世、顺丰）
- TMS系统集成（oTMS、运去哪、路歌）
- 电子发票集成（航天信息、百望云、票易通）
- 支付集成（微信支付、支付宝、银联）
- 消息集成（邮件、短信、站内信）
- 统一的数据同步接口和日志记录

## 开发规范

### 代码规范
- 遵循 Alibaba Java 开发规范
- 使用 Google Java Code Style
- 所有公共方法必须添加 JavaDoc 注释
- 常量使用大写下划线命名
- 变量使用小驼峰命名
- 类名使用大驼峰命名

### Git 规范
- 提交信息格式：`type(scope): subject`
  - `feat`: 新功能
  - `fix`: 修复bug
  - `docs`: 文档更新
  - `style`: 代码格式调整
  - `refactor`: 重构
  - `test`: 测试相关
  - `chore`: 构建/工具相关

### 分支规范
- `main`: 主分支，稳定版本
- `develop`: 开发分支
- `feature/*`: 功能分支
- `bugfix/*`: 修复分支
- `release/*`: 发布分支

## 后续优化

### 功能优化
1. 前端界面开发（Vue3 + Element Plus）
2. 性能优化和压力测试
3. 缓存策略优化
4. 数据库索引优化

### 运维优化
1. Docker容器化部署
2. Kubernetes集群部署
3. CI/CD自动化部署
4. 监控告警系统
5. 日志分析系统

### 文档完善
1. 部署文档
2. 运维文档
3. 开发文档
4. API文档完善

## 贡献指南

欢迎贡献代码、提出建议或报告问题！

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'feat: Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 提交 Pull Request

## 许可证

本项目采用 [MIT License](LICENSE) 开源协议。

## 联系方式

- 项目地址: https://github.com/qoobot-com/openscm
- 问题反馈: https://github.com/qoobot-com/openscm/issues
- 邮箱: dev@qoobot.com

## 致谢

感谢所有为 OpenSCM 项目做出贡献的开发者！

---

**OpenSCM供应链管理系统 - 企业级供应链管理解决方案**
