package com.httphealthcheck.timer

import akka.actor.ActorRef
import com.httphealthcheck.actor.TimerActor
import com.httphealthcheck.actorsystem.GlobalActorSystem

trait DefaultTimer extends GlobalActorSystem {
  protected val timer: ActorRef = system.actorOf(TimerActor.props, "default-timer")
}
