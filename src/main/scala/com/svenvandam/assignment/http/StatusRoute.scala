package com.svenvandam.assignment.http

import akka.http.scaladsl.server.Directives._

object StatusRoute {
  val route = pathPrefix("status") {
    pathEndOrSingleSlash {
      get {
        complete("OK")
      }
    }
  }
}
