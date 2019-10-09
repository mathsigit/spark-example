package com.island.spark.mllib;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SparkKMeans {
    public static void main(String[] args) {

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

        Arrays.stream(args)
                .filter(Objects::nonNull)
                .collect(Collectors.toList())
                .size()


        SparkConf conf = new SparkConf().setAppName("JavaKMeansExample").setMaster("local[*]");
        JavaSparkContext jsc = new JavaSparkContext(conf);

        // Load and parse data
        URL url = SparkKMeans.class.getClassLoader().getResource("data/mllib/kmeans_data.txt");
        JavaRDD<Vector> parsedData = jsc.textFile(url.toString()).map(s -> {
            String[] sarray = s.split(" ");
            double[] values = new double[sarray.length];
            for (int i = 0; i < sarray.length; i++) {
                values[i] = Double.parseDouble(sarray[i]);
            }
            return Vectors.dense(values);
        });
        parsedData.cache();

        // Cluster the data into two classes using KMeans
        int numClusters = 2;
        int numIterations = 20;
        KMeansModel clusters = KMeans.train(parsedData.rdd(), numClusters, numIterations);

        System.out.println("Cluster centers:");
        for (Vector center: clusters.clusterCenters()) {
            System.out.println(" " + center);
        }
        double cost = clusters.computeCost(parsedData.rdd());
        System.out.println("Cost: " + cost);

        // Evaluate clustering by computing Within Set Sum of Squared Errors
        double WSSSE = clusters.computeCost(parsedData.rdd());
        System.out.println("Within Set Sum of Squared Errors = " + WSSSE);

        String buildPath = "build/com/island/spark/mllib/KMeansModel";
        // Save and load model
        clusters.save(jsc.sc(), buildPath);
        KMeansModel.load(
                jsc.sc(),
                buildPath
        );

        jsc.stop();
    }
}
