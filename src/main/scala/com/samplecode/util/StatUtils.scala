package com.samplecode.util

import breeze.linalg.{*, DenseMatrix, DenseVector, sum}

/**
  * Holds general useful util functions
  *
  * @author vdonets
  */
object StatUtils {

  /**
    * Normalizes a vector
    *
    * @param feature vector to normalize
    * @return a normalized version of given vector
    */
  def normalize(feature: DenseVector[Double]): DenseVector[Double] = {
    val mean = breeze.stats.mean(feature)
    val std = breeze.stats.stddev(feature - mean)
    return feature / std
  }

  /**
    * Normalizes a vector
    *
    * @param feature vector to normalize
    */
  def normalizeInPlace(feature: DenseVector[Double]): Unit = {
    val mean = breeze.stats.mean(feature)
    val std = breeze.stats.stddev(feature - mean)
    for (i <- 0.until(feature.length))
      feature.update(i, feature(i) / std)
  }

}
