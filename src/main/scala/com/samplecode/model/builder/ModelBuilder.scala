package com.samplecode.model.builder

import breeze.linalg._
import com.samplecode.model.Model

/**
  * Base trait for any model builder.
  *
  * @author vdonets
  */
trait ModelBuilder {

  /**
    * Builds a model from given data to predict given labels
    *
    * @param X input data into the training algorithm this builder implements
    * @param Y the labels this builder will try to predict
    * @return the resulting model
    */
  def build(X: DenseMatrix[Double], Y: DenseVector[Double]): Model
}

object ModelBuilder {

  /**
    * Regression model builder implementation
    * @return
    */
  def regression = new MultipleRegressionBuilder
}
