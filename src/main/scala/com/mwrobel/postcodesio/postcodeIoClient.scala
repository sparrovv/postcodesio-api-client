package com.mwrobel.postcodesio

import spray.httpx.SprayJsonSupport
import spray.json.{DefaultJsonProtocol}

import spray.http._
import scala.concurrent.{Future}
import akka.actor.ActorSystem
import spray.client.pipelining._


case class Postcode(postcode: String, country: String)
object Postcode extends DefaultJsonProtocol {
  implicit val format = jsonFormat2(Postcode.apply)
}

case class PostcodeResponse(status: Int, result: Postcode)
object PostcodeResponse extends DefaultJsonProtocol {
  implicit val format = jsonFormat2(PostcodeResponse.apply)
}

class PostcodeIoApiClient extends SprayJsonSupport{
  implicit val actorSystem = ActorSystem("simple-spray-client")
  import actorSystem.dispatcher // execution context

  val baseUri = PostocdeIoApiClient.uri

//  def pipeline[T: FromRequestUnmarshaller](request: HttpRequest): Future[T] = {
//    (sendReceive ~> unmarshal[T]).apply(request)
//  }

  def getPostcode(postcode: String): Future[PostcodeResponse] = {
    val pipeline = sendReceive ~> unmarshal[PostcodeResponse]

      pipeline {
        Get(baseUri.withPath(Uri.Path(s"/postcodes/$postcode")))
      }
  }
}

object PostocdeIoApiClient {
  val uri = Uri("https://api.postcodes.io")
}
