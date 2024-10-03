package com.httphealthcheck.actor.processor

import akka.actor.{Actor, ActorRef, Props}
import akka.http.scaladsl.model.{HttpResponse, StatusCode}
import com.httphealthcheck.actorsystem.GlobalExecutionContext

import scala.concurrent.Future
import scala.util.{Failure, Success}

object HttpFutureProcessor {
  case class Process(endpoint: String, httpResponseFuture: Future[HttpResponse], respondTo: ActorRef)

  class HttpStatusDto
  case class HttpStatus(endpoint: String, code: StatusCode) extends HttpStatusDto
  case class HttpException(endpoint: String, exception: Throwable) extends HttpStatusDto

  def props: Props = Props(new HttpFutureProcessor)
}

class HttpFutureProcessor extends Actor with GlobalExecutionContext {
  import HttpFutureProcessor._

  override def receive: Receive = {
    case Process(endpoint, responseFuture, respondTo) => responseFuture.onComplete {
      case Success(response) =>
        response.discardEntityBytes()
        respondTo ! HttpStatus(endpoint, response.status)
      case Failure(exception) => sender ! HttpException(endpoint, exception)
    }
  }
}
