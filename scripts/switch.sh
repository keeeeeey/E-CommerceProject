#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy() {

    IDLE_PORT=$(find_idle_port)

    echo "> Port to Switch : $IDLE_PORT"
    echo "> Switching Port"

	# 하나의 문장 만들어 다음 파이프라인으로 넘김
	# 반드시 홑따옴표 사용
	# `sudo tee..` :  앞에서 넘어온 문장을 `service-url.inc` 에 덮어씀
    echo 'set \$service_url http://127.0.0.1:${IDLE_PORT};' | sudo tee /etc/nginx/conf.d/service-url.inc

    echo "> Reload NginX"

	# nginx reload는 restart와 다르게 중단 없이 불러옴
	# 중요 설정들은 반영되지 않음
    sudo service nginx reload

}