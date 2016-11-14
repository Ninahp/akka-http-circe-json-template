/*
 * Copyright 2016 Vitor Vieira
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vitorsvieira.http.config

import akka.actor.ActorSystem
import akka.event.{ LogSource, Logging }
import akka.stream.ActorMaterializer
import com.typesafe.config.{ Config, ConfigFactory }

import scala.concurrent.ExecutionContextExecutor

trait ServerSettingsTemplate {

  lazy private val config: Config = ConfigFactory.load()
  private val httpConfig: Config = config.getConfig("http")
  val httpInterface: String = httpConfig.getString("interface")
  val httpPort: Int = httpConfig.getInt("port")

  implicit val actorSystem: ActorSystem = ActorSystem("akka-http-circe-json")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = actorSystem.dispatcher
  private implicit val logSource: LogSource[ServerSettingsTemplate] = (t: ServerSettingsTemplate) ⇒ t.getClass.getSimpleName
  private def logger(implicit logSource: LogSource[_ <: ServerSettingsTemplate]) = Logging(actorSystem, this.getClass)

  implicit val log = logger
}

object ServerSettingsTemplate extends ServerSettingsTemplate
