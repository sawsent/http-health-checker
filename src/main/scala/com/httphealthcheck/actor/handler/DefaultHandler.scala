package com.httphealthcheck.actor.handler

import akka.actor.ActorRef
import com.httphealthcheck.actorsystem.GlobalActorSystem

trait DefaultHandler extends GlobalActorSystem {
  protected val handler: ActorRef = system.actorOf(HandlerActor.props)
}
