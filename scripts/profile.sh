#!/usr/bin/env bash

# 쉬고 있는 profile (IDLE_PROFILE) 찾는 함수
function find_idle_profile ()
{
	# 현재 Nginx 가 바라보는 SpringBoot 가 정상 수행 중인지 확인
	# HttpStatus 값을 받음
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

	# 오류 코드가 400 이상이면 예외로 보고 real2 를 profile 로 사용
    if [ ${RESPONSE_CODE} -ge 400 ]
    then
      CURRENT_PROFILE=real2
    else
      CURRENT_PROFILE=$(curl -s http://localhost/profile )
    fi

    if [ ${CURRENT_PROFILE } ==real1 ]
    then
      IDLE_PROFILE=real2
    else
      IDLE_PROFILE=real1
    fi

	# bash 에서는 함수 값을 return 하는 기능 없음,
	# echo 로 출력되는 값을 클라이언트에서 잡아서 사용
	# 그래서 중간에 echo 있으면 안됨
    echo "${IDLE_PROFILE}"
}

# 쉬고 있는 profile 의 port 찾는 함수
function find_idle_port()
{
    IDLE_PROFILE=$(find_idle_profile)

    if [ ${IDLE_PROFILE} == real1 ]
    then
      echo "8081"
    else
      echo "8082"
    fi
}