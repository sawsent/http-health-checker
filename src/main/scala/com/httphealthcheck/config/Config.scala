package com.httphealthcheck.config

import com.typesafe.config.ConfigFactory

import java.time.Duration
import scala.jdk.CollectionConverters.ListHasAsScala

object Config {
  private val config = ConfigFactory.load()

  val healthCheckDelay = Duration.ofSeconds(config.getLong("check.delay"))
  val endpoints =  config.getStringList("endpoints").asScala.toList

}
