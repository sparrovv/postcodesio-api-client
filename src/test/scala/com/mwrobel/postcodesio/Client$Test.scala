package com.mwrobel.postcodesio

import org.scalatest.FunSuite
import org.scalatest._

import scala.concurrent.Await
import scala.concurrent.duration._

class ClientTest extends FunSuite with Matchers {

  test("getPostcode") {

    val client = new PostcodeIoApiClient
    val response = Await.result(client.getPostcode("sw9 9dj"), 5.seconds)

    response.status should equal(200)
    response.result.postcode should equal("SW9 9DJ")
  }

}