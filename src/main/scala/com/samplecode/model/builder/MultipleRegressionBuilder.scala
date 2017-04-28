package com.samplecode.model.builder

import breeze.linalg._
import com.samplecode.model.{CostFunction, Model, SimpleModel}
import com.samplecode.util.Logging

/**
  * Multiple regression implementation of model builder
  *
  * @author vdonets
  */
class MultipleRegressionBuilder private[builder]() extends ModelBuilder with Logging {

  private implicit val canTranspose = breeze.linalg.support.CanTranspose
  private implicit val opMult = breeze.linalg.operators.OpMulMatrix

  private var rate = 0.0001
  private var w: DenseVector[Double] = null
  private var iterations = -1
  private var callback: CostFunction = CostFunction.SquareError
  private var target = 0.0

  /**
    * Sets max number of iterations this builder will perform
    * while training model.
    *
    * @param iterations max num iterations
    * @return this
    */
  def setIterations(iterations: Int): MultipleRegressionBuilder = {
    this.iterations = iterations
    return this
  }

  /**
    * Sets initial weights. If not set, will be initialized to 0 vector
    *
    * @param weights initial weights
    * @return this
    */
  def setWeights(weights: DenseVector[Double]): MultipleRegressionBuilder = {
    this.w = weights
    return this
  }

  /**
    * Sets learning rate
    *
    * @param rate new learning rate
    * @return
    */
  def setRate(rate: Double): MultipleRegressionBuilder = {
    this.rate = rate;
    return this
  }

  /**
    * Sets cost function
    *
    * @param costFunc new cost function
    * @param target   the target error to stop iterating it
    * @return
    */
  def setCostFunc(costFunc: CostFunction, target: Double): MultipleRegressionBuilder = {
    this.callback = costFunc
    this.target = target
    return this
  }

  override def build(X: DenseMatrix[Double], Y: DenseVector[Double]): Model = {
    if (X == null || Y == null)
      throw new IllegalStateException("Data and labels may not be null")
    if (X.rows != Y.length)
      throw new IllegalStateException("Data and labels must have same number of rows")
    val weights: DenseVector[Double] =
      if (w == null)
        DenseVector.zeros(X.cols)
      else if (w.length == X.cols)
        w
      else throw new IllegalStateException("Weights must have same number of elements as features")

    var i = 0;
    var error = 0.0
    do {
      // calculate prediction with current weights
      val prediction =
        X(*, ::).map(v => {
          sum(v * weights)
        })
      // calculate errors
      val errors = prediction - Y
      //update each weight
      for (j <- 0.until(weights.length)) {
        val featureJ = X(::, j)
        //adjust each weight
        weights.update(j, weights(j) -
          (sum(errors.toDenseMatrix * featureJ.toDenseMatrix.t)
            * rate / Y.length))
      }
      i += 1
      error = callback.apply(X, Y, weights)
    } while (error > target && i <= iterations)
    return new SimpleModel(weights)
  }
}
