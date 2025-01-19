#!/bin/bash

# 示例 ./starter.sh -c true -e test -o start

while getopts ":c:e:o" opt
do
    case  $opt in
    c)
        compile="$OPTARG"
      ;;
    e)
       env="$OPTARG"
       ;;
    o)
        op="$OPTARG"
    ;;
esac done

pcompile=${compile:-true}
penv=${env:-test}
pop=${op:-start}

echo "当前环境:$penv"

if [  $compile="true" ]; then
    echo "开始编译 环境:$penv"
   (cd ../ && mvn clean package -P "$penv" -Dmaven.test.skip=true)
fi

if [ $pop="start" ];then
   (cd ../ && java -jar memberclub.starter/target/memberclub-starter-"$penv".jar )
fi

