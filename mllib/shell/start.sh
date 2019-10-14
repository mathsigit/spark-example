#! /bin/bash

export SPARK_HOME=/Users/stana/Downloads/spark-2.4.4-bin-hadoop2.7
export PATH=$PATH:$SPARK_HOME/bin

spark-submit ../build/libs/mllib-0.0.1-SNAPSHOT-all.jar $1 $2 $3