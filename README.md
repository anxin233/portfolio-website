# DevPortfolio - 程序员个人网站

全栈工程师个人展示网站，包含项目经历、技术博客等模块。

## 技术栈

### 前端
- **Vue 3** + Composition API + TypeScript
- **Vite** 构建工具
- **Vue Router 4** 路由管理
- **Pinia** 状态管理
- 纯 CSS 动效（粒子背景、滚动动画、打字机效果）

### 后端
- **Java 17** + **Spring Boot 3.2**
- **Spring Data JPA** 数据持久化
- **H2**（开发）/ **MySQL**（生产）
- RESTful API 设计

## 项目结构

```
├── frontend/               # 前端项目
│   ├── src/
│   │   ├── components/     # 通用组件（导航、粒子背景、打字机等）
│   │   ├── views/          # 页面（首页、关于、项目、博客）
│   │   ├── router/         # 路由配置
│   │   ├── styles/         # 全局样式与主题
│   │   └── main.ts         # 入口文件
│   ├── package.json
│   └── vite.config.ts
│
├── backend/                # 后端项目
│   ├── src/main/java/com/portfolio/
│   │   ├── controller/     # API 控制器
│   │   ├── service/        # 业务逻辑
│   │   ├── repository/     # 数据访问
│   │   ├── entity/         # 实体类
│   │   ├── common/         # 通用类（统一响应等）
│   │   └── config/         # 配置（CORS、数据初始化）
│   ├── src/main/resources/
│   │   └── application.yml
│   └── pom.xml
```

## 快速启动

### 前端

```bash
cd frontend
npm install
npm run dev
```

浏览器访问 http://localhost:3000

### 后端

```bash
cd backend
mvn spring-boot:run
```

API 地址：http://localhost:8080

> 开发环境默认使用 H2 内存数据库，无需额外配置。
> 生产环境切换 MySQL，修改 `application.yml` 中的数据源配置即可。

## 页面说明

| 页面 | 路由 | 说明 |
|------|------|------|
| 首页 | `/` | Hero 动画、技术栈展示、代表项目预览 |
| 关于 | `/about` | 个人介绍、职业时间线、技能图谱、工程理念 |
| 项目 | `/projects` | PLM 系统详细展示 + 其他项目卡片 |
| 博客 | `/blog` | 文章列表（分类筛选） |
| 文章 | `/blog/:id` | 博客详情阅读页 |

## 博客 API

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/blog` | 获取所有已发布文章 |
| GET | `/api/blog/page?page=0&size=10` | 分页获取 |
| GET | `/api/blog/category/{category}` | 按分类筛选 |
| GET | `/api/blog/{id}` | 文章详情 |
| POST | `/api/blog` | 创建文章 |
| PUT | `/api/blog/{id}` | 更新文章 |
| DELETE | `/api/blog/{id}` | 删除文章 |

## 自定义

1. **个人信息**：编辑 `frontend/src/views/Home.vue` 和 `About.vue` 中的数据
2. **项目经历**：编辑 `frontend/src/views/Projects.vue` 中的项目数据
3. **联系方式**：全局搜索 `your@email.com` 替换为真实邮箱
4. **GitHub 链接**：编辑 `Footer.vue` 中的链接
5. **数据库**：修改 `application.yml` 切换到 MySQL
