package com.httphealthcheck.actorsystem

import scala.concurrent.ExecutionContext

trait GlobalExecutionContext extends GlobalActorSystem {
  protected implicit val context: ExecutionContext = system.dispatcher
}
