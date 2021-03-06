/**
 * @author Hang Su <hangsu@gatech.edu>.
 */

package edu.gatech.cse8803.clustering

import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD

object Metrics {
  /**
   * Given input RDD with tuples of assigned cluster id by clustering,
   * and corresponding real class. Calculate the purity of clustering.
   * Purity is defined as
   *             \fract{1}{N}\sum_K max_j |w_k \cap c_j|
   * where N is the number of samples, K is number of clusters and j
   * is index of class. w_k denotes the set of samples in k-th cluster
   * and c_j denotes set of samples of class j.
   * @param clusterAssignmentAndLabel RDD in the tuple format
   *                                  (assigned_cluster_id, class)
   * @return purity
   */
  def purity(clusterAssignmentAndLabel: RDD[(Int, Int)]): Double = {
    /**
     * TODO: Remove the placeholder and implement your code here
     */
    val part1 = clusterAssignmentAndLabel.map(x =>(x,1)).keyBy(_._1).reduceByKey((a,b) =>(a._1,a._2+b._2))
    val part2 = part1.map(x => (x._2._1._1,x._2._2)).keyBy(_._1).reduceByKey((a,b) =>(1,Math.max(a._2,b._2))).map(x => x._2._2).reduce((a,b) => a+b)
    val res = part2 / clusterAssignmentAndLabel.count().toDouble
    res
  }
}
