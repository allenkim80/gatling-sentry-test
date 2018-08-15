package com.github.allenkim80.gatling

import com.ea.spearhead.gatling.sentry.Predef._
import com.google.gson.JsonParser
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol._

import scala.concurrent.duration._

class TestSimulation extends Simulation {

  startSentry()

  val scn = scenario("Sentry")
    .exec(
      http("/events")
        .get("/events")
        .transformResponse {case response if response.isReceived => sendSentryLog(response, List("Error1", "Error2"), "message")}
    ).exec()
    .exec(sentry("").sendStringLogByAction("test2", "message"))
    .pause(3)

  def createHttpProtocol() : HttpProtocolBuilder = {
    http
      .baseURL("http://server-address")
      .acceptHeader("*/*")
      .headers(Map(
        "Content-Type" -> "application/json"
      ))
      .disableWarmUp
  }

  def sendSentryLog(response: Response, validErrors:List[String], message:String = "") = {
    val resultObject = new JsonParser().parse(response.body.string).getAsJsonObject.get("result")

    resultObject match {
      case result if result != null && result.getAsString == "true" => /* do nothing */
      case _ =>
        val reasonObject = new JsonParser().parse(response.body.string).getAsJsonObject.get("reason")

        if (!validErrors.contains(reasonObject.getAsString)) {
          sentry("").sendHttpLog(response)
        }
    }
    response
  }

  setUp(scn.inject(rampUsers(2) over (1 minute))).protocols(createHttpProtocol())

  stopSentry()
}
