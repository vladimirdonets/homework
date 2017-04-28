package com.samplecode.data

import breeze.linalg.{DenseMatrix, DenseVector}

import scala.io.Source

/**
  * Base abstraction for any form of datasource. A datasource will create
  * and expose a matrix.
  *
  * @author vdonets
  */
trait DataSource {
  /**
    * Returns training data in this data source as X, Y
    *
    * @return input (X) matrix and tags (Y) vector
    */
  def getTrainingData: (DenseMatrix[Double], DenseVector[Double])

}

object DataSource {

  def fromFileSystem(fileName: String): FileDataSource =
    new FileDataSource(Source.fromFile(fileName))

  def fromClasspath(fileName: String): FileDataSource =
    new FileDataSource(Source.fromInputStream(getClass.getClassLoader.getResourceAsStream(fileName)))
}
