PROJECT_ROOT="/home/ec2-user/mapddang-back"
JAR_FILE="$PROJECT_ROOT/dnd-travel.jar"
APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"
TIME_NOW=$(date +%c)

# 이전 프로세스 확인
CURRENT_PID=$(ps -ef | grep java | grep "$JAR_FILE" | grep -v grep | awk '{print $2}')
if [ -n "$CURRENT_PID" ]; then
    echo "$TIME_NOW > 이전 프로세스($CURRENT_PID) 종료 시도" >> $DEPLOY_LOG
    kill -9 $CURRENT_PID
fi

# build 파일 복사
echo "$TIME_NOW > $JAR_FILE 복사" >> $DEPLOY_LOG
cp $PROJECT_ROOT/build/libs/dnd-travel-0.0.1-SNAPSHOT.jar $JAR_FILE

# jar 파일 실행
echo "$TIME_NOW > $JAR_FILE 실행" >> $DEPLOY_LOG
nohup java -jar $JAR_FILE > $APP_LOG 2> $ERROR_LOG &

# 실행 확인
sleep 3
NEW_PID=$(ps -ef | grep java | grep "$JAR_FILE" | grep -v grep | awk '{print $2}')
echo "$TIME_NOW > 새로운 애플리케이션 시작 (PID: $NEW_PID)" >> $DEPLOY_LOG
