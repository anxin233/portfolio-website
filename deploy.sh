#!/bin/bash
# ============================================
# 本地编译 → 打包镜像 → 推送 Docker Hub（docker.io）
# 用法: bash deploy.sh
# ============================================

set -e

DOCKERHUB_USER="${DOCKERHUB_USER:-aniden}"
FRONTEND_IMAGE="${DOCKERHUB_USER}/portfolio-frontend:latest"
BACKEND_IMAGE="${DOCKERHUB_USER}/portfolio-backend:latest"

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

# 3. 登录 Docker Hub（不要写成 docker login 用户名，应 docker login 或 docker login docker.io）
echo ""
echo "[3/6] 登录 Docker Hub..."
docker login

# 4. 打包前端镜像
echo ""
echo "[4/6] 打包前端镜像..."
docker build -t "${FRONTEND_IMAGE}" ./frontend

# 5. 打包后端镜像
echo ""
echo "[5/6] 打包后端镜像..."
docker build -t "${BACKEND_IMAGE}" ./backend

# 6. 推送
echo ""
echo "[6/6] 推送到 Docker Hub..."
docker push "${FRONTEND_IMAGE}"
docker push "${BACKEND_IMAGE}"

echo ""
echo "=========================================="
echo "  完成!"
echo "=========================================="
echo "  前端: ${FRONTEND_IMAGE}"
echo "  后端: ${BACKEND_IMAGE}"
echo ""
echo "  服务器部署: docker compose pull && docker compose up -d"
echo ""
