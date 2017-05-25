#!/bin/bash

SCR_PATH=$(pwd)

java -jar $SCR_PATH/scripts/selenium-server-standalone-3.0.1.jar -port 5566 -role hub &
echo $! > $SCR_PATH/scripts/seleniumhub.pid