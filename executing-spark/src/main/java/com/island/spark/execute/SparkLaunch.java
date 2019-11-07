package com.island.spark.execute;

import org.apache.log4j.Logger;
import org.apache.spark.launcher.SparkLauncher;

public class SparkLaunch {
    static final Logger logger = Logger.getLogger(SparkLaunch.class);

    public static void main(String[] arguments) throws Exception {
        long startTime = System.currentTimeMillis();
        test(); // ... the code being measured ...
        long estimatedTime = System.currentTimeMillis() - startTime;
        logger.info("estimatedTime (millis)=" + estimatedTime);
    }

    static void test() throws Exception {

        final String javaHome = "/Users/stana/.sdkman/candidates/java/current";
        final String sparkHome = "/Users/stana/Downloads/spark-2.4.4-bin-hadoop2.7";
        final String appResource = "/Users/stana/Downloads/mllib-0.0.1-SNAPSHOT-all.jar";
        final String mainClass = "com.island.spark.mllib.SparkKMeans";

        // parameters passed to the  SparkKMeans
        final String[] appArgs = new String[]{
                //"--arg",
                "3",
                //"--arg",
                "50",
                //"--arg",
                "/Users/stana/Downloads/kmeans_data.txt"
        };
        SparkLauncher spark = new SparkLauncher()
                .setVerbose(true)
                .setJavaHome(javaHome)
                .setSparkHome(sparkHome)
                .setAppResource(appResource)    // "/my/app.jar"
                .setMainClass(mainClass)        // "my.spark.app.Main"
                .setMaster("local")
                .setConf(SparkLauncher.DRIVER_MEMORY, "1g")
                .addAppArgs(appArgs);

        // Launches a sub-process that will start the configured Spark application.
        Process proc = spark.launch();

        InputStreamReaderRunnable inputStreamReaderRunnable = new InputStreamReaderRunnable(proc.getInputStream(), "input");
        Thread inputThread = new Thread(inputStreamReaderRunnable, "LogStreamReader input");
        inputThread.start();

        InputStreamReaderRunnable errorStreamReaderRunnable = new InputStreamReaderRunnable(proc.getErrorStream(), "output");
        Thread errorThread = new Thread(errorStreamReaderRunnable, "LogStreamReader");
        errorThread.start();

        logger.info("Waiting for finish...");
        int exitCode = proc.waitFor();
        logger.info("Finished! Exit code:" + exitCode);
    }
}
