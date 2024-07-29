PROJECT_ROOT="/home/ec2-user/mapddang-back"
JAR_FILE="$PROJECT_ROOT/dnd-travel.jar"

APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

# build 파일 복사
echo "$TIME_NOW > $JAR_FILE 복사" >> $DEPLOY_LOG
cp $PROJECT_ROOT/build/libs/dnd-travel-0.0.1-SNAPSHOT.jar $JAR_FILE

# jar 파일 실행
echo "$TIME_NOW > $JAR_FILE 실행" >> $DEPLOY_LOG
nohup java -jar $JAR_FILE > $APP_LOG 2> $ERROR_LOG &

CURRENT_PID=$(pgrep -f $JAR_FILE)
echo "$TIME_NOW > 서비스 PID: $CURRENT_PID " >> $DEPLOY_LOG
