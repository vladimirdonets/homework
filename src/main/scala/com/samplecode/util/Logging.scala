package com.samplecode.util

import org.slf4j.{Logger, LoggerFactory}

/**
  * Convenience logging helper.
  *
  * @author vdonets
  */
trait Logging {

  protected lazy val logger: Logger =
    LoggerFactory.getLogger(getClass.getName)
}
