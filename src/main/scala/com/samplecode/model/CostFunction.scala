package com.samplecode.model

import breeze.linalg.{*, DenseMatrix, DenseVector, sum}

/**
  * Cost function will live outside the algorithm to make it more configurable
  *
  * @author vdonets
  */
trait CostFunction extends ((DenseMatrix[Double], DenseVector[Double], DenseVector[Double]) => Double) {


}

object CostFunction {

  //TODO: errors are calculated twice if this cost func is used
  val SquareError = new CostFunction {
    override def apply(X: DenseMatrix[Double], Y: DenseVector[Double], w: DenseVector[Double]): Double = {
      val prediction =
        X(*, ::).map(v => {
          sum(v * w)
        })
      // calculate errors
      val errors = prediction - Y
      var sqErr = 0.0
      for (i <- 0.until(errors.length))
        sqErr += (errors(i) * errors(i))
      return sqErr / 2 * Y.length
    }
  }
}
