package com.mwrobel.postcodesio

import org.scalatest.FunSuite
import org.scalatest._

import scala.concurrent.Await
import scala.concurrent.duration._


class ClientTest extends FunSuite with Matchers {

  test("getPostcode(postcodeStr)") {

    val client = new PostcodeIoApiClient
    val postcode = Await.result(client.getPostcode("sw9 9dj"), 5.seconds)

    postcode.get.postcode should equal("SW9 9DJ")
  }

  test("getPostcode(postcodeStr) invalid postcode") {

    val client = new PostcodeIoApiClient
    val postcode = Await.result(client.getPostcode("xxxx"), 5.seconds)

    postcode should be(None)
  }

  test("getPostcode(List())") {

    val client = new PostcodeIoApiClient
    val postcodes = Await.result(client.getPostcode(List("sw9 9dj", "ec1a 4hd")), 5.seconds)

    postcodes.size should equal(2)
    postcodes.find(_.postcode == "SW9 9DJ").get.postcode should equal("SW9 9DJ")
    postcodes.find(_.postcode == "EC1A 4HD").get.postcode should equal("EC1A 4HD")
  }

  test("getPostcode(List()) with one invalid postcode") {

    val client = new PostcodeIoApiClient
    val postcodes = Await.result(client.getPostcode(List("9dj", "ec1a 4hd")), 5.seconds)

    postcodes.size should equal(1)
    postcodes(0).postcode should equal("EC1A 4HD")
  }
}