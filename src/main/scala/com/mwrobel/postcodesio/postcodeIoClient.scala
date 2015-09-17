package com.mwrobel.postcodesio

import akka.util.Timeout
import com.mwrobel.postcodesio.models.Postcode
import spray.httpx.{UnsuccessfulResponseException, SprayJsonSupport}
import spray.httpx.unmarshalling.{FromResponseUnmarshaller}
import spray.json.{JsonFormat, DefaultJsonProtocol}

import spray.http._
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.{Future}
import akka.actor.ActorSystem
import spray.client.pipelining._
import scala.concurrent.duration._

case class PostcodeResponse[T](status: Int, result: T)

case class PostcodeBulkRequest(postcodes: List[String])

case class PostcodeBulkResponse(query: String, result: Option[Postcode])

object PostcodeBulkResponse extends DefaultJsonProtocol {
  implicit val postcodeBulkResponse = jsonFormat2(PostcodeBulkResponse.apply)
}

object PostcodeBulkRequest extends DefaultJsonProtocol {
  implicit val postcodeBulkRequest = jsonFormat1(PostcodeBulkRequest.apply)
}

object PostcodeResponse extends DefaultJsonProtocol {
  implicit def postocdeioResultFormat[T :JsonFormat] = jsonFormat2(PostcodeResponse.apply[T])
}

class PostcodeIoApiClient(timeoutDuration: FiniteDuration = 10.seconds) {
  implicit val actorSystem = ActorSystem("simple-spray-client")
  implicit val timeout = Timeout(timeoutDuration)
  import actorSystem.dispatcher // execution context
  import SprayJsonSupport._

  val baseUri = PostocdeIoApiClient.uri

  def pipeline[T: FromResponseUnmarshaller](request: HttpRequest): Future[T] = {
    (sendReceive ~> unmarshal[T]).apply(request)
  }

  def getPostcode(postcode: String): Future[Option[Postcode]] = {
    pipeline[PostcodeResponse[Postcode]](
      Get(baseUri.withPath(Uri.Path(s"/postcodes/$postcode")))
    ).map(r => Some(r.result)).recover {
     case e: UnsuccessfulResponseException => None
    }
  }

  def getPostcode(postcodes: List[String]): Future[List[Postcode]] = {
    pipeline[PostcodeResponse[List[PostcodeBulkResponse]]](
      Post( baseUri.withPath(Uri.Path("/postcodes")), PostcodeBulkRequest(postcodes))
    ).map( _.result.flatMap(_.result) ).recover{
      case e: UnsuccessfulResponseException => Nil
    }
  }
}

object PostocdeIoApiClient {
  val uri = Uri("https://api.postcodes.io")
}
