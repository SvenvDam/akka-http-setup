package com.svenvandam.assignment.http

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.svenvandam.assignment.BaseTestKit

class StatusRouteTest extends BaseTestKit with ScalatestRouteTest {
  "route" should {
    "complete a GET to /status with 200" in {
      Get("/status") ~> StatusRoute.route ~> check {
        responseAs[String] shouldBe "OK"
        status shouldBe StatusCodes.OK
      }
    }

    "reject non GET requests with 405" in {
      Post("/status") ~> Route.seal(StatusRoute.route) ~> check {
        status shouldBe StatusCodes.MethodNotAllowed
      }
    }

    "reject downstream resources with 404" in {
      Get("/status/downstram-resource") ~> Route.seal(StatusRoute.route) ~> check {
        status shouldBe StatusCodes.NotFound
      }
    }
  }
}
