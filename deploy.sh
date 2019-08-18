#!/bin/bash

REPOSITORY=/home/ec2-user/app/travis

APP_JAR=$(basename ${REPOSITORY}/build/*jar)

DEPLOY_PATH=/var/www/wiw.nexters.com

systemctl status wiw

sudo cp $REPOSITORY/build/*.jar $DEPLOY_PATH/releases

sudo ln -Tfs $DEPLOY_PATH/releases/$APP_JAR $DEPLOY_PATH/current

sudo systemctl restart wiw

sudo systemctl status wiw