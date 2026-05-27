#!/bin/bash
# ============================================
# ECS deploy script — run from /opt/app
# Called manually or by GitHub Actions SSH
# ============================================
set -e

# ---- Config ----
REGISTRY="${REGISTRY:-ghcr.io}"
IMAGE="${IMAGE:-app-server}"
GITHUB_REPO_OWNER="${GITHUB_REPO_OWNER:-bosszhipin}"
FULL_IMAGE="${REGISTRY}/${GITHUB_REPO_OWNER}/${IMAGE}:latest"
COMPOSE_FILE="/opt/app/docker-compose.yml"

echo "========================================="
echo " Deploying ${FULL_IMAGE}"
echo "========================================="

# ---- Pull latest image ----
echo "[1/4] Pulling image..."
docker pull "${FULL_IMAGE}"

# ---- Stop old container ----
echo "[2/4] Stopping old container..."
cd /opt/app
docker compose down --remove-orphans 2>/dev/null || true

# ---- Start new container ----
echo "[3/4] Starting new container..."
docker compose up -d

# ---- Wait & verify ----
echo "[4/4] Verifying..."
sleep 8

if docker ps --filter "name=app-server" --filter "status=running" | grep -q app-server; then
    echo "========================================="
    echo " Deploy successful!"
    echo " Container status:"
    docker ps --filter name=app-server --format 'table {{.Names}}\t{{.Status}}\t{{.Ports}}'
    echo "========================================="
else
    echo "ERROR: Container failed to start!"
    docker compose logs --tail=50
    exit 1
fi

# ---- Cleanup ----
echo "Cleaning old images..."
docker image prune -af --filter "until=24h" 2>/dev/null || true
docker builder prune -f 2>/dev/null || true

echo "Done."
