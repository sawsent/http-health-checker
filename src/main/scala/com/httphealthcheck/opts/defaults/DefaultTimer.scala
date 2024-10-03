package com.httphealthcheck.opts.defaults

import akka.actor.ActorRef
import com.httphealthcheck.actorsystem.GlobalActorSystem
import com.httphealthcheck.config.Config

trait DefaultTimer extends GlobalActorSystem {
  protected val timer: ActorRef = system.actorOf(TimerActor.props(Config.healthCheckDelay), "default-timer")
}
