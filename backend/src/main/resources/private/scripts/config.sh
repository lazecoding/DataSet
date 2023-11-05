#!/bin/bash

########### 定义常量和方法 ############
# 可执行程序
APP_NAME=./dataset.jar
# 配置文件地址
CONFIG_PATH=./conf.yaml
# JVM 参数
ENV_JAVA_OPTS=""
# 端口
ENV_PORT=""
# 日志路径
LOG_PATH=""
# 日志级别
LOG_LEVEL=""

# 获取配置属性
# Looks up a config value by key from a simple YAML-style key-value map.
# $1: key to look up
# $2: default value to return if key does not exist
# $3: config file to read from
readFromConfig() {
    local key=$1
    local defaultValue=$2
    local configFile=$3
    # first extract the value with the given key (1st sed), then trim the result (2nd sed)
    # if a key exists multiple times, take the "last" one (tail)
    local value=`sed -n "s/^[ ]*${key}[ ]*: \([^#]*\).*$/\1/p" "${configFile}" | sed "s/^ *//;s/ *$//" | tail -n 1`
    [ -z "$value" ] && echo "$defaultValue" || echo "$value"
}
########### 定义常量和方法 ############

########### 读取配置文件属性 ##########
if [ -z "${ENV_JAVA_OPTS}" ]; then
    ENV_JAVA_OPTS=$(readFromConfig "env.java.opts" "" "${CONFIG_PATH}")
    # Remove leading and ending double quotes (if present) of value
    ENV_JAVA_OPTS="$( echo "${ENV_JAVA_OPTS}" | sed -e 's/^"//'  -e 's/"$//' )"
fi

if [ -z "${ENV_PORT}" ]; then
    ENV_PORT=$(readFromConfig "env.port" "8859" "${CONFIG_PATH}")
fi

if [ -z "${LOG_PATH}" ]; then
    LOG_PATH=$(readFromConfig "env.log.path" "../logs" "${CONFIG_PATH}")
fi

if [ -z "${LOG_LEVEL}" ]; then
    LOG_LEVEL=$(readFromConfig "env.log.level" "INFO" "${CONFIG_PATH}")
fi
########### 读取配置文件属性 ##########

############## 完善参数 #############
JVM_ENV_PORT="--server.port=${ENV_PORT}"
JVM_LOG_HONE="-DLOG_HOME=\"${LOG_PATH}\"";
JVM_LOG_LEVEL="-DLOG_LEVEL=\"${LOG_LEVEL}\"";
############## 完善参数 #############

echo "${APP_NAME} ENV_JAVA_OPTS:${ENV_JAVA_OPTS} ENV_PORT:${ENV_PORT} LOG_PATH:${LOG_PATH} LOG_LEVEL:${LOG_LEVEL}"
echo "JVM ENV_JAVA_OPTS:${ENV_JAVA_OPTS} JVM_ENV_PORT:${JVM_ENV_PORT} JVM_LOG_HONE:${JVM_LOG_HONE} JVM_LOG_LEVEL:${JVM_LOG_LEVEL}"
echo "${JAVA_HOME}/bin/java -Djava.awt.headless=true ${ENV_JAVA_OPTS} ${JVM_LOG_HONE} ${JVM_LOG_LEVEL} -jar ${APP_NAME} ${JVM_ENV_PORT} >/dev/null 2>&1 &"