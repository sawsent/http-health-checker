package com.httphealthcheck.actor

import akka.actor.{Actor, ActorRef, Props}
import akka.http.scaladsl.model.HttpResponse
import com.httphealthcheck.timer.DefaultTimer
import com.httphealthcheck.actor.handler.DefaultHandler
import com.httphealthcheck.actor.handler.HandlerActor.Handle
import com.httphealthcheck.model.{HttpExceptionResponse, HttpSuccessStatusResponse}

import scala.util.{Failure, Success}

object ControllerActor {
  case object Init
  case object HealthCheckAll


  def props(checkers: Set[ActorRef]): Props = Props(new ControllerActor(checkers))
}

class ControllerActor(private val checkers: Set[ActorRef]) extends Actor with DefaultTimer with DefaultHandler {
  import ControllerActor._
  import TimerActor.{Start, Stop, Configure}
  import HealthCheckerActor._

  override def receive: Receive = {
    case Init =>
      timer ! Configure(() => self ! HealthCheckAll)
      timer ! Start

    case HealthCheckAll =>
      checkers.foreach(_ ! CheckRequest)

    case CheckResponse(endpoint, responseFuture) => responseFuture.onComplete {
        case Success(response) =>
          response.discardEntityBytes()
          handler ! Handle(HttpSuccessStatusResponse(endpoint, response.status))
        case Failure(exception) => handler ! Handle(HttpExceptionResponse(endpoint, exception))
      }
  }
}
