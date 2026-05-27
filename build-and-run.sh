#!/bin/bash
echo "=== Stopping old Java processes ==="
for pid in $(netstat -ano 2>/dev/null | grep ':8080' | grep 'LISTENING' | awk '{print $NF}' | sort -u); do
  taskkill //F //PID $pid 2>/dev/null && echo "Killed PID $pid"
done
sleep 3
echo "=== Forcing clean rebuild ==="
MSYS_NO_PATHCONV=1 docker run --rm \
  -v "D:/Desktop/master/flutter-java/flutter-java/boss-backend":/app \
  -v /tmp/m2:/root/.m2 \
  -w /app \
  maven:3.9.7-eclipse-temurin-17 \
  mvn clean package -DskipTests -q

if [ $? -ne 0 ]; then
  echo "BUILD FAILED!"
  exit 1
fi
echo "=== Build Success, starting app ==="
java -jar "D:/Desktop/master/flutter-java/flutter-java/boss-backend/target/boss-backend-1.0.0.jar"
