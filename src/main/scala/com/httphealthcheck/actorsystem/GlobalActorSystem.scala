package com.httphealthcheck.actorsystem

import akka.actor.ActorSystem

trait GlobalActorSystem {
  protected implicit val system: ActorSystem = ActorSystem("global")
}
