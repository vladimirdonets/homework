package com.samplecode.model

import breeze.linalg.DenseVector

/**
  * Base trait for a model. A model can take
  * an input observation and estimate its
  * associated output
  *
  * @author vdonets
  */
trait Model {
  def weights: Array[Double]

  def predict(observation: DenseVector[Double]): Double
}
