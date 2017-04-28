package com.samplecode


import breeze.linalg.*
import com.samplecode.data.DataSource
import com.samplecode.model.builder.ModelBuilder
import com.samplecode.util.StatUtils
import org.scalatest.FlatSpec

/**
  * Regression tests
  * @author vdonets
  */
class Regression extends FlatSpec{


  "ModelBuilder" should "train simple regression model" in {
    val data = DataSource.fromClasspath("ex1data1.txt").getTrainingData
    //train model
    val model = ModelBuilder
      //using regression
      .regression
      //continuing for 1500 iterations
      .setIterations(1500)
      //with learning rate 0.01
      .setRate(0.01)
      //build model using loaded ata
      .build(data._1, data._2)

    assert(java.util.Arrays.equals(model.weights,
      Array(-3.6307700095575353, 1.166410427898432)))
  }

  it should "train a multiple regression model with normalized data" in {
    val data = DataSource.fromClasspath("ex1data2.txt").getTrainingData
    //normalize X
    val i = data._1(::,*).iterator
    //skip first (constant) feature
    i.next()
    //normalize every other in place
    i.foreach(feature => StatUtils.normalizeInPlace(feature))
    //train model
    val model = ModelBuilder
      //using regression
      .regression
      //running for 400 iterations
      .setIterations(400)
      //with learning rate 0.01
      .setRate(0.01)
      //build model using normalized data
      .build(data._1, data._2)

    assert(java.util.Arrays.equals(model.weights,
      Array(20585.858449815485, 100686.56588681099, 15225.667867251932)))
  }
}
