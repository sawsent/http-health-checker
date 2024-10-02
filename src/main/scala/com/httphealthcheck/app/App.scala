package com.httphealthcheck.app

import akka.actor.ActorRef
import com.httphealthcheck.actor.ControllerActor.Init
import com.httphealthcheck.actor.{ControllerActor, HealthCheckerActor}
import com.httphealthcheck.actorsystem.GlobalActorSystem
import com.httphealthcheck.config.Config

object App extends App with GlobalActorSystem {

  private val checkers: Set[ActorRef] = Config.endpoints.map(ep => system.actorOf(HealthCheckerActor.props(ep))).toSet
  private val controller = system.actorOf(ControllerActor.props(checkers), "controller")
  controller ! Init

}
