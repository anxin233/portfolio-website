#!/bin/bash
# ============================================
# 本地编译 → 打包镜像 → 推送 Harbor
# 用法: bash deploy.sh
# ============================================

set -e

HARBOR="harbor.aicode.ccwu.cc"
PROJECT="portfolio"
FRONTEND_IMAGE="${HARBOR}/${PROJECT}/frontend:latest"
BACKEND_IMAGE="${HARBOR}/${PROJECT}/backend:latest"

echo ""
echo "=========================================="
echo "  Portfolio 构建 & 部署"
echo "=========================================="

# 1. 前端编译
echo ""
echo "[1/6] 编译前端..."
cd frontend
npm install
npm run build
cd ..

# 2. 后端编译
echo ""
echo "[2/6] 编译后端..."
cd backend
mvn clean package -DskipTests -B
cd ..

# 3. 登录 Harbor
echo ""
echo "[3/6] 登录 Harbor..."
docker login ${HARBOR}

# 4. 打包前端镜像
echo ""
echo "[4/6] 打包前端镜像..."
docker build -t ${FRONTEND_IMAGE} ./frontend

# 5. 打包后端镜像
echo ""
echo "[5/6] 打包后端镜像..."
docker build -t ${BACKEND_IMAGE} ./backend

# 6. 推送
echo ""
echo "[6/6] 推送到 Harbor..."
docker push ${FRONTEND_IMAGE}
docker push ${BACKEND_IMAGE}

echo ""
echo "=========================================="
echo "  完成!"
echo "=========================================="
echo "  前端: ${FRONTEND_IMAGE}"
echo "  后端: ${BACKEND_IMAGE}"
echo ""
echo "  服务器部署: docker compose pull && docker compose up -d"
echo ""
