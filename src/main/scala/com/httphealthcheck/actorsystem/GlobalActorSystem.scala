package com.httphealthcheck.actorsystem

import akka.actor.ActorSystem

import scala.concurrent.ExecutionContext

trait GlobalActorSystem {
  protected implicit val system: ActorSystem = ActorSystem("global")
  protected implicit val executionContext: ExecutionContext = system.dispatcher
}
