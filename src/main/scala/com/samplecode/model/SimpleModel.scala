package com.samplecode.model

import breeze.linalg._


/**
  * A simple model implementation.
  *
  * @author vdonets
  */
private[model] class SimpleModel(private val w: DenseVector[Double]) extends Model {

  private implicit val canTranspose = breeze.linalg.support.CanTranspose
  private implicit val opMult = breeze.linalg.operators.OpMulMatrix

  override def weights = w.data

  override def predict(observation: DenseVector[Double]): Double = w.t * observation
}
