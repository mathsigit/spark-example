package com.island.spark.mllib;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class SparkKMeans {
    public static void main(String[] args) throws MalformedURLException {

        final Logger logger = Logger.getLogger(SparkKMeans.class);
        /*
        * Parameter 1 :  num of clusters
        * Parameter 2 :  num of iterations
        * Parameter 3 :  path of file
        */

        if(args.length != 3) {
            System.out.println("Please check input parameters. " +
                    "Parameter 1 :  num of clusters " +
                    "Parameter 2 :  num of iterations " +
                    "Parameter 3 :  path of file ");
            return;
        }

        // Cluster the data for KMeans
        int numClusters = Integer.parseInt(args[0]);
        int numIterations = Integer.parseInt(args[1]);
        // Load data
        URL sourceURL = args[2].trim().isEmpty()
                ? SparkKMeans.class.getClassLoader().getResource("data/mllib/kmeans_data.txt")
                : new File(args[2]).toURI().toURL();

        SparkConf conf = new SparkConf().setAppName("JavaKMeansExample").setMaster("local[*]");
        JavaSparkContext jsc = new JavaSparkContext(conf);

        //Parse data
        JavaRDD<Vector> parsedData = jsc.textFile(sourceURL.toString()).map(s -> {
            String[] sarray = s.split(" ");
            double[] values = new double[sarray.length];
            for (int i = 0; i < sarray.length; i++) {
                values[i] = Double.parseDouble(sarray[i]);
            }
            return Vectors.dense(values);
        });
        parsedData.cache();

        KMeansModel clusters = KMeans.train(parsedData.rdd(), numClusters, numIterations);

        StringBuilder clusterCenters = new StringBuilder();
        clusterCenters.append("=========Cluster Centers=========");
        for (Vector center: clusters.clusterCenters()) {
            clusterCenters.append(" ").append(center);
        }
        clusterCenters.append("=========");
        logger.info(clusterCenters.toString());
        double cost = clusters.computeCost(parsedData.rdd());
        logger.info("Cost: " + cost);

        // Evaluate clustering by computing Within Set Sum of Squared Errors
        double WSSSE = clusters.computeCost(parsedData.rdd());
        logger.info("Within Set Sum of Squared Errors = " + WSSSE);

        /*
        String buildPath = "build/com/island/spark/mllib/KMeansModel";
        // Save and load model
        clusters.save(jsc.sc(), buildPath);
        KMeansModel.load(
                jsc.sc(),
                buildPath
        );
        */
        jsc.stop();

        logger.info("=========Application Finish=========");
    }
}
