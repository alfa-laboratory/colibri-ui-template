#!/bin/bash

SCR_PATH=$(pwd)
platform_name=$1

echo $platform_name
pid=`cat $SCR_PATH/src/test/resources/environment/${platform_name}/appium.pid`
echo $pid killed
kill -9 $pid
rm $SCR_PATH/src/test/resources/environment/${platform_name}/appium.pid