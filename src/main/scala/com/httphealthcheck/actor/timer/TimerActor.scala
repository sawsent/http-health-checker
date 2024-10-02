package com.httphealthcheck.actor.timer

import akka.actor.{Actor, Props}

import java.time.Duration

object TimerActor {
  private case object Wait
  private case object Act

  case object Stop
  case object Start
  case class Configure(action: () => Unit)

  def props(delay: Duration): Props = Props(new TimerActor(delay))

}

class TimerActor(private val delay: Duration) extends Actor {
  import TimerActor._
  override def receive: Receive = {
    case Configure(action) => context.become(waiting(action))
    case _ => // TODO: Error: Not configured
  }

  private def waiting(action: () => Unit): Receive = {
    case Start =>
      context.become(ready(action))
      self ! Act
    case _ => // TODO: errors
  }

  private def ready(action: () => Unit): Receive = {
    case Wait =>
      Thread.sleep(delay.toMillis)
      self ! Act

    case Act =>
      action()
      self ! Wait

    case Stop => context.become(waiting(action))
  }
}
