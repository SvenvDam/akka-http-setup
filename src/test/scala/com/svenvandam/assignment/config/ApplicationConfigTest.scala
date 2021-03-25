package com.svenvandam.assignment.config

import com.svenvandam.assignment.BaseTestKit
import scala.concurrent.duration._

class ApplicationConfigTest extends BaseTestKit {
  "ApplicationConfig" should {
    "construct from resource" in {
      ApplicationConfig() shouldBe ApplicationConfig(
        ServerConfig("0.0.0.0", 8080, 5 seconds)
      )
    }
  }
}
