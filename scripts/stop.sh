PROJECT_ROOT="/home/ec2-user/mapddang-back"
JAR_FILE="$PROJECT_ROOT/dnd-travel.jar"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"
TIME_NOW=$(date +%c)

# 현재 구동 중인 애플리케이션 pid 확인
CURRENT_PID=$(ps -ef | grep java | grep "$JAR_FILE" | grep -v grep | awk '{print $2}')

# 프로세스가 켜져 있으면 종료
if [ -z $CURRENT_PID ]; then
  echo "$TIME_NOW > 현재 실행 중인 애플리케이션이 없음" >> $DEPLOY_LOG
else
  echo "$TIME_NOW > 실행 중인 $CURRENT_PID 애플리케이션 종료 " >> $DEPLOY_LOG
  kill -15 $CURRENT_PID

# 프로세스 종료 확인
  sleep 5
  if ps -p $CURRENT_PID > /dev/null; then
      echo "$TIME_NOW > 애플리케이션 강제 종료 시도" >> $DEPLOY_LOG
      kill -9 $CURRENT_PID
  fi
fi
