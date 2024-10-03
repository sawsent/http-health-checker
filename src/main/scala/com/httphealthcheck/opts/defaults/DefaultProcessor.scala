package com.httphealthcheck.opts.defaults

import com.httphealthcheck.actor.processor.HttpFutureProcessor
import com.httphealthcheck.actorsystem.GlobalActorSystem

trait DefaultProcessor extends GlobalActorSystem {
  protected val processor = system.actorOf(HttpFutureProcessor.props)
}
