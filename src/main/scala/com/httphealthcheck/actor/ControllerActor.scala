package com.httphealthcheck.actor

import akka.actor.{Actor, ActorRef, Props}
import com.httphealthcheck.actor.handler.HandlerActor.Handle
import com.httphealthcheck.actor.processor.HttpFutureProcessor.HttpStatusDto
import com.httphealthcheck.opts.defaults.DefaultOpts

object ControllerActor extends DefaultOpts {
  case object Init
  private case object HealthCheckAll

  def props(checkers: Set[ActorRef]): Props = Props(new ControllerActor(checkers, timer, processor, handler))
  def props(checkers: Set[ActorRef], timer: ActorRef): Props = Props(new ControllerActor(checkers, timer, processor, handler))
  def props(checkers: Set[ActorRef], timer: ActorRef, processor: ActorRef): Props = Props(new ControllerActor(checkers, timer, processor, handler))
  def props(checkers: Set[ActorRef], timer: ActorRef, processor: ActorRef, handler: ActorRef): Props = Props(new ControllerActor(checkers, timer, processor, handler))
}

class ControllerActor(
                private val checkers: Set[ActorRef],
                private val timer: ActorRef,
                private val processor: ActorRef,
                private val handler: ActorRef
                     ) extends Actor
  {
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

    case s: HttpStatusDto => handler ! Handle(s)
  }
}
