# Spark MLLib Example

## Environmental Requirements

* Java: JDK 8, version 1.8.0_212 or latter is better
* OS: Linux (CentOS would be better) or MacOS (10.13+ would be better)

## K-means Sample Data

``` vim
0.0 0.0 0.0
0.1 0.1 0.1
0.2 0.2 0.2
9.0 9.0 9.0
9.1 9.1 9.1
9.2 9.2 9.2
```

## How To Compiler source code to jar file

```shell script
cd mllib

# Linux OS
./gradlew clean shadowJar

# Window OS
gradlew.bat clean shadowJar
``` 

## How To Start Up The Application:

* [Download Spark 2.4.4](https://www.apache.org/dyn/closer.lua/spark/spark-2.4.4/spark-2.4.4-bin-hadoop2.7.tgz) and unzip file to $HOME, as well as you would have the path of spark as: 
`$HOME/spark-2.4.4-bin-hadoop2.7`, for example: `/home/user1/spark-2.4.4-bin-hadoop2.7`
* Execute `shell/start.sh` with parameter as below:  
  ```shell script
  cd shell
  bash start.sh $NumClusters $NumIterations $FilePath
  
  e.q.
  bash start.sh 3 50 /home/user1/datra/kmeans_data.txt 
  ```
  
  If successfully, you would see the result as below in command window:
  ```shell script
  INFO SparkKMeans: =========Cluster Centers========= [0.05,0.05,0.05] [9.1,9.1,9.1] [0.2,0.2,0.2] =========
  ```