package com.samplecode.data

import breeze.linalg.{*, Axis, DenseMatrix, DenseVector}
import com.samplecode.util.Logging

import scala.collection.mutable.ArrayBuffer
import scala.io.{BufferedSource, Source}

/**
  * Simple datasource that builds a matrix from a file
  * @author vdonets
  */
class FileDataSource private[data](s: BufferedSource) extends DataSource with Logging {

  private val m: (DenseMatrix[Double], DenseVector[Double]) = {
    try {
      val data = DenseMatrix(
        s.getLines().toStream.map(
          line => {
            val buffer = new ArrayBuffer[String]()
            buffer.append("1")
            buffer.appendAll(line.split(","))
            buffer
          })
          .map(_.map(_.toDouble))  : _*)
      val v = data(::, data.cols - 1)
      (data.delete(data.cols - 1, Axis._1), v)
    } catch{
      case t: Throwable =>
        t.printStackTrace()
        null
    }finally {
      s.close()
    }
  }

  override def getTrainingData: (DenseMatrix[Double], DenseVector[Double]) = m
}
