package com.httphealthcheck.actor

import akka.actor.{Actor, ActorRef, Props}
import com.httphealthcheck.actor.handler.DefaultHandler
import com.httphealthcheck.actor.handler.HandlerActor.Handle
import com.httphealthcheck.actor.processor.DefaultProcessor
import com.httphealthcheck.actor.processor.HttpFutureProcessor.{HttpException, HttpStatus}
import com.httphealthcheck.actor.timer.DefaultTimer

object ControllerActor {
  case object Init
  case object HealthCheckAll


  def props(checkers: Set[ActorRef]): Props = Props(new ControllerActor(checkers))
}

class ControllerActor(private val checkers: Set[ActorRef]) extends Actor with DefaultTimer with DefaultHandler with DefaultProcessor {
  import ControllerActor._
  import HealthCheckerActor._
  import com.httphealthcheck.actor.processor.HttpFutureProcessor.Process
  import com.httphealthcheck.actor.timer.TimerActor.{Configure, Start}

  override def receive: Receive = {
    case Init =>
      timer ! Configure(() => self ! HealthCheckAll)
      timer ! Start

    case HealthCheckAll =>
      checkers.foreach(_ ! CheckRequest)

    case CheckResponse(endpoint, responseFuture) => processor ! Process(endpoint, responseFuture, self)

    case s: HttpStatus => handler ! Handle(s)
    case e: HttpException => handler ! Handle(e)
  }
}
