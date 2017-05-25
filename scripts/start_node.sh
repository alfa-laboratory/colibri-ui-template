#!/bin/bash

SCR_PATH=$(pwd)
platform_name=$1
port=$2
echo $SCR_PATH
echo $platform_name
echo $port
PATH=$PATH:/usr/local/bin
echo $PATH
argum=$"-p ${port} --nodeconfig $SCR_PATH/src/test/resources/environment/${platform_name}/test_node.json"
screen -dmS ${platform_name} /usr/local/bin/appium ${argum}
echo $! > ${SCR_PATH}/src/test/resources/environment/${platform_name}/appium.pid
echo Node started!