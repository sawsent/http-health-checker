package com.httphealthcheck.actor

import akka.actor.{Actor, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import com.httphealthcheck.actorsystem.GlobalActorSystem

import scala.concurrent.Future

object HealthCheckerActor {
  case object CheckRequest
  case class CheckResponse(endpoint: String, responseFuture: Future[HttpResponse])

  def props(endpoint: String): Props = Props(new HealthCheckerActor(endpoint))
}

class HealthCheckerActor(private val endpoint: String) extends Actor with GlobalActorSystem {
  import HealthCheckerActor._

  override def receive: Receive = {
    case CheckRequest =>
      val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = endpoint))
      sender ! CheckResponse(endpoint, responseFuture)

    case _ => println(endpoint)
  }
}
