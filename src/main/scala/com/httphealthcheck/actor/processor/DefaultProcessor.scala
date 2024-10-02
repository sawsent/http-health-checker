package com.httphealthcheck.actor.processor

import com.httphealthcheck.actorsystem.GlobalActorSystem

trait DefaultProcessor extends GlobalActorSystem {
  protected val processor = system.actorOf(HttpFutureProcessor.props)
}
