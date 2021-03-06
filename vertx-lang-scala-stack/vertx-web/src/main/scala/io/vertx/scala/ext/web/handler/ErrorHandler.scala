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

package io.vertx.scala.ext.web.handler

import io.vertx.lang.scala.HandlerOps._
import scala.reflect.runtime.universe._
import io.vertx.lang.scala.Converter._
import io.vertx.ext.web.{RoutingContext => JRoutingContext}
import io.vertx.scala.ext.web.RoutingContext
import io.vertx.core.Handler
import io.vertx.ext.web.handler.{ErrorHandler => JErrorHandler}

/**
  * A pretty error handler for rendering error pages.
  */
class ErrorHandler(private val _asJava: Object)
    extends io.vertx.core.Handler[RoutingContext] {

  def asJava = _asJava


  override def handle(arg0: RoutingContext): Unit = {
    asJava.asInstanceOf[JErrorHandler].handle(arg0.asJava.asInstanceOf[JRoutingContext])
  }

}

object ErrorHandler {
  def apply(asJava: JErrorHandler) = new ErrorHandler(asJava)  
  /**
    * Create an error handler using defaults
    * @return the handler
    */
  def create(): ErrorHandler = {
    ErrorHandler(JErrorHandler.create())
  }

  /**
    * Create an error handler
    * @param errorTemplateName the error template name - will be looked up from the classpath
    * @param displayExceptionDetails true if exception details should be displayed
    * @return the handler
    */
  def create(errorTemplateName: String, displayExceptionDetails: Boolean): ErrorHandler = {
    ErrorHandler(JErrorHandler.create(errorTemplateName.asInstanceOf[java.lang.String], displayExceptionDetails.asInstanceOf[java.lang.Boolean]))
  }

  /**
    * Create an error handler
    * @param displayExceptionDetails true if exception details should be displayed
    * @return the handler
    */
  def create(displayExceptionDetails: Boolean): ErrorHandler = {
    ErrorHandler(JErrorHandler.create(displayExceptionDetails.asInstanceOf[java.lang.Boolean]))
  }

  /**
    * Create an error handler
    * @param errorTemplateName the error template name - will be looked up from the classpath
    * @return the handler
    */
  def create(errorTemplateName: String): ErrorHandler = {
    ErrorHandler(JErrorHandler.create(errorTemplateName.asInstanceOf[java.lang.String]))
  }

}
