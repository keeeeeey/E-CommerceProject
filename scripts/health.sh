#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh
source ${ABSDIR}/switch.sh

IDLE_PORT=$(find_idle_port)

echo "> Health Check Start.."
echo "> IDLE_PORT : $IDLE_PORT"
echo "> curl -s http://localhost:$IDLE_PORT/profile"

sleep 10

for RETRY_COUNT in {1..10}
do
  RESPONSE=$( curl -s http://localhost:${IDLE_PORT}/profile )
  UP_COUNT=$( echo ${RESPONSE} | grep 'real' | wc -l )

  # UP_COUNT>= 1 인 경우
  # 'real' 문자열 있는지
  if [ ${UP_COUNT} -ge 1 ]
  then
    echo "> SUCCESS : Health Check"
    switch_proxy
    break
  else
    echo "> ERROR : Can't understand Response of Health Check or Doesn't Running"
    echo "> Health Check : ${RESPONSE}"
  fi

  if [ ${RETRY_COUNT} -eq 10 ]
  then
    echo "> FAIL : Health Check"
    echo "> Terminate Deployment without Connection to NginX"
    exit 1
  fi

  echo "> Retry ... Fail to Health Check Connection"
  sleep 10
done