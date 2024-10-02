package com.httphealthcheck.app

import com.httphealthcheck.actor.ControllerActor.Init
import com.httphealthcheck.actor.{ControllerActor, HealthCheckerActor}
import com.httphealthcheck.actorsystem.GlobalActorSystem
import com.httphealthcheck.config.Config

object App extends App with GlobalActorSystem {

  val controller = system.actorOf(ControllerActor.props(Config.endpoints.map(ep => system.actorOf(HealthCheckerActor.props(ep))).toSet), "controller")

  controller ! Init


}
