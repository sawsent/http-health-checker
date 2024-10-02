package com.httphealthcheck.actor.handler

import akka.actor.{Actor, Props}
import com.httphealthcheck.actor.processor.HttpFutureProcessor.{HttpException, HttpStatus}

object HandlerActor {
  case class Handle(com: Any)

  def props: Props = Props(new HandlerActor)
}

class HandlerActor extends Actor {
  import HandlerActor._

  override def receive: Receive = {
    case Handle(HttpStatus(ep, code)) => println(s"$ep - $code")
    case Handle(HttpException(ep, exception)) => println(s"$ep - $exception")

  }
}
