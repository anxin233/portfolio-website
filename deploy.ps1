# ============================================
# 构建镜像 → 推送 Harbor
# 前端：本地编译 → 打包镜像
# 后端：本地 Maven 编译 → 打包镜像
# 用法: .\deploy.ps1
# ============================================

$ErrorActionPreference = "Stop"

# 刷新 PATH（确保 fnm、maven 等新安装的工具可用）
$env:PATH = [System.Environment]::GetEnvironmentVariable("PATH", "User") + ";" + [System.Environment]::GetEnvironmentVariable("PATH", "Machine")

$HARBOR = "harbor.aicode.ccwu.cc"
$PROJECT = "portfolio"
$FRONTEND_IMAGE = "${HARBOR}/${PROJECT}/frontend:latest"
$BACKEND_IMAGE = "${HARBOR}/${PROJECT}/backend:latest"

Write-Host ""
Write-Host "==========================================" -ForegroundColor Cyan
Write-Host "  Portfolio 构建 & 部署" -ForegroundColor Cyan
Write-Host "==========================================" -ForegroundColor Cyan

# 1. 前端：本地编译（需要 fnm use 20）
Write-Host ""
Write-Host "[1/6] 本地编译前端 (npm)..." -ForegroundColor Yellow
fnm env --use-on-cd --shell power-shell | Out-String | Invoke-Expression
fnm use 20
Set-Location frontend
npm run build
Set-Location ..

# 2. 后端：本地 Maven 编译
Write-Host ""
Write-Host "[2/6] 本地编译后端 (Maven)..." -ForegroundColor Yellow
$env:JAVA_HOME = "D:\Program Files\Java\jdk-17"
Set-Location backend
mvn clean package -DskipTests -B
Set-Location ..

# 3. 登录 Harbor
Write-Host ""
Write-Host "[3/6] 登录 Harbor..." -ForegroundColor Yellow
docker login $HARBOR

# 4. 前端：将本地 dist 打包为镜像
Write-Host ""
Write-Host "[4/6] 打包前端镜像..." -ForegroundColor Yellow
docker build -t $FRONTEND_IMAGE ./frontend

# 5. 后端：将本地 jar 打包为镜像
Write-Host ""
Write-Host "[5/6] 打包后端镜像..." -ForegroundColor Yellow
docker build -t $BACKEND_IMAGE ./backend

# 6. 推送
Write-Host ""
Write-Host "[6/6] 推送镜像到 Harbor..." -ForegroundColor Yellow
docker push $FRONTEND_IMAGE
docker push $BACKEND_IMAGE

Write-Host ""
Write-Host "==========================================" -ForegroundColor Green
Write-Host "  完成!" -ForegroundColor Green
Write-Host "==========================================" -ForegroundColor Green
Write-Host ""
Write-Host "  前端: $FRONTEND_IMAGE"
Write-Host "  后端: $BACKEND_IMAGE"
Write-Host ""
Write-Host "  服务器部署: docker compose pull && docker compose up -d"
Write-Host ""
