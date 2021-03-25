package com.svenvandam.assignment

import java.util.concurrent.Executors
import akka.actor.CoordinatedShutdown
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import com.svenvandam.assignment.config.ApplicationConfig
import com.svenvandam.assignment.http.StatusRoute
import com.typesafe.scalalogging.StrictLogging
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object Main extends App with StrictLogging {
  val blockingContext = ExecutionContext.fromExecutor(Executors.newCachedThreadPool())

  val config = ApplicationConfig()

  val routes = StatusRoute.route

  val rootBehavior = Behaviors.setup[Nothing] { ctx =>
    implicit val ec = ctx.executionContext
    implicit val sys = ctx.system

    Http()
      .newServerAt(config.server.host, config.server.port)
      .bind(routes)
      .onComplete {
        case Success(binding) =>
          binding.addToCoordinatedShutdown(config.server.shutdownTimeout)
          logger.info(s"Started server! [host=${config.server.host}] [port=${config.server.port}]")

        case Failure(e) =>
          logger.error(s"Failed to start server!", e)
          ctx.system.terminate()
      }

    Behaviors.empty
  }

  val system = ActorSystem[Nothing](rootBehavior, "server")

  CoordinatedShutdown(system).addJvmShutdownHook {
    logger.warn("Shutting down JVM!")
  }
}
