package com.mwrobel.postcodesio

import org.scalatest.FunSuite
import org.scalatest._

import scala.concurrent.Await
import scala.concurrent.duration._

class ClientTest extends FunSuite with Matchers {

  test("getPostcode(postcodeStr)") {

    val client = new PostcodeIoApiClient
    val response = Await.result(client.getPostcode("sw9 9dj"), 5.seconds)

    response.status should equal(200)
    response.result.postcode should equal("SW9 9DJ")
  }

  test("getPostcode(List())") {

    val client = new PostcodeIoApiClient
    val response = Await.result(client.getPostcode(List("sw9 9dj", "ec1a 4hd")), 5.seconds)

    response.status should equal(200)

    response.result.size should equal(2)
    response.result.find(_.query == "sw9 9dj").get.result.postcode should equal("SW9 9DJ")
    response.result.find(_.query == "ec1a 4hd").get.result.postcode should equal("EC1A 4HD")
  }
}