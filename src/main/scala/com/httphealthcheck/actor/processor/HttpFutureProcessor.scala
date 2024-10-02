package com.httphealthcheck.actor.processor

import akka.actor.{Actor, ActorRef, Props}
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, StatusCode}
import com.httphealthcheck.actorsystem.GlobalActorSystem

import scala.concurrent.Future
import scala.util.{Failure, Success}

object HttpFutureProcessor {
  case class Process(endpoint: String, httpResponseFuture: Future[HttpResponse], respondTo: ActorRef)

  case class HttpStatus(endpoint: String, code: StatusCode)
  case class HttpException(endpoint: String, exception: Throwable)

  def props: Props = Props(new HttpFutureProcessor)
}

class HttpFutureProcessor extends Actor with GlobalActorSystem {
  import HttpFutureProcessor._

  override def receive: Receive = {
    case Process(endpoint, f, respondTo) => f.onComplete {
      case Success(response) =>
        response.discardEntityBytes()
        respondTo ! HttpStatus(endpoint, response.status)
      case Failure(exception) => sender ! HttpException(endpoint, exception)
    }
  }
}
