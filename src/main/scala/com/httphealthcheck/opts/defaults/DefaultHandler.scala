package com.httphealthcheck.opts.defaults

import akka.actor.ActorRef
import com.httphealthcheck.actor.handler.HandlerActor
import com.httphealthcheck.actorsystem.GlobalActorSystem

trait DefaultHandler extends GlobalActorSystem {
  protected val handler: ActorRef = system.actorOf(HandlerActor.props)
}
