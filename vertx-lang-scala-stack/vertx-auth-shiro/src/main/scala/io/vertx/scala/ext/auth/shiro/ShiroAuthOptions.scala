/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.vertx.scala.ext.auth.shiro

import io.vertx.lang.scala.json.Json._
import io.vertx.core.json.JsonObject
import scala.collection.JavaConverters._
import io.vertx.scala.ext.auth.{AuthOptions => ExtAuthOptions}
import io.vertx.ext.auth.shiro.{ShiroAuthOptions => JShiroAuthOptions}
import io.vertx.core.json.JsonObject

/**
  * Shiro auth configuration options, see Vert.x Auth Shiro component and/or Apache Shiro project.
  */
class ShiroAuthOptions(private val _asJava: JShiroAuthOptions) 
    extends ExtAuthOptions {

  def asJava = _asJava

  /**
    * Set the Shiro auth config.
    */
  def setConfig(value: io.vertx.core.json.JsonObject) = {
    asJava.setConfig(value)
    this
  }
  def getConfig: io.vertx.core.json.JsonObject = {
    asJava.getConfig()
  }

  /**
    * Set the Shiro auth options type.
    */
  def setType(value: io.vertx.ext.auth.shiro.ShiroAuthRealmType) = {
    asJava.setType(value)
    this
  }
  def getType: io.vertx.ext.auth.shiro.ShiroAuthRealmType = {
    asJava.getType()
  }
}

object ShiroAuthOptions {
  
  def apply() = {
    new ShiroAuthOptions(new JShiroAuthOptions(emptyObj()))
  }
  
  def apply(t: JShiroAuthOptions) = {
    if (t != null) {
      new ShiroAuthOptions(t)
    } else {
      new ShiroAuthOptions(new JShiroAuthOptions(emptyObj()))
    }
  }
  
  def fromJson(json: JsonObject): ShiroAuthOptions = {
    if (json != null) {
      new ShiroAuthOptions(new JShiroAuthOptions(json))
    } else {
      new ShiroAuthOptions(new JShiroAuthOptions(emptyObj()))
    }
  }
}
