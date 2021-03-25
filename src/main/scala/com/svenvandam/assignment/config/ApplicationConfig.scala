package com.svenvandam.assignment.config

import com.typesafe.scalalogging.StrictLogging
import pureconfig._
import pureconfig.generic.auto._
import scala.concurrent.duration.FiniteDuration

case class ServerConfig(host: String, port: Int, shutdownTimeout: FiniteDuration)

case class ApplicationConfig(server: ServerConfig)

object ApplicationConfig extends StrictLogging {
  def apply(): ApplicationConfig =
    ConfigSource.default.load[ApplicationConfig] match {
      case Right(c) =>
        logger.info("Loaded config.")
        c

      case Left(e) =>
        val errMsg = s"Failed to parse config file! Errors: ${e.prettyPrint()}"
        logger.error(errMsg)
        throw new RuntimeException(errMsg)
    }
}
