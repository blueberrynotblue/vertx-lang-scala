/*
 * Copyright (c) 2011-2016 The original author or authors
 * ------------------------------------------------------
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 *     The Eclipse Public License is available at
 *     http://www.eclipse.org/legal/epl-v10.html
 *
 *     The Apache License v2.0 is available at
 *     http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */

package io.vertx.lang.scala

import io.vertx.core.json.JsonObject
import io.vertx.core.{AbstractVerticle, Future, Verticle}
import io.vertx.scala.core.{Context, Vertx}

import scala.concurrent.{ExecutionContext, Promise}
import scala.util.{Failure, Success}
import scala.collection.JavaConverters._
import scala.collection.mutable

/**
  * Base class for verticle implementiations.
  *
  * @author <a href="mailto:jochen.mader@codecentric.de">Jochen Mader</a
  */
class ScalaVerticle {
  protected implicit var executionContext:ExecutionContext = _
  protected var vertx: Vertx = _
  protected var ctx: Context = _
  private var javaVerticle: AbstractVerticle = _

  /**
    * Initialise the verticle.<p>
    * This is called by Vert.x when the verticle instance is deployed. Don't call it yourself.
    *
    * @param vertx   the deploying Vert.x instance
    * @param context the context of the verticle
    */
  def init(vertx: io.vertx.core.Vertx, context: io.vertx.core.Context, verticle: AbstractVerticle): Unit = {
    this.vertx = new Vertx(vertx)
    this.ctx = new Context(context)
    this.javaVerticle = verticle
    this.executionContext = VertxExecutionContext(this.vertx.getOrCreateContext())
  }

  /**
    * Shortcut to stop the verticle.
    * Use this method if stopping is complete after the method returns. Otherwise use [[stop(stopPromise: concurrent.Promise[Unit])]]
    */
  def stop(): Unit = {}

  /**
    * Shortcut to start the verticle.
    * Use this method if starting is complete after the method returns. Otherwise use [[start(stopPromise: concurrent.Promise[Unit])]]
    */
  def start(): Unit = {}

  /**
    * Stop the verticle.<p>
    * This is called by Vert.x when the verticle instance is un-deployed. Don't call it yourself.<p>
    * If your verticle does things in it's shut-down which take some time then you can override this method
    * and complete the stopPromise some time later when clean-up is complete.
    *
    * @param stopPromise a promise which should be completed when verticle clean-up is complete.
    */
  def stop(stopPromise: concurrent.Promise[Unit]): Unit = {
    stop()
    stopPromise.success(())
  }

  /**
    * Start the verticle.<p>
    * This is called by Vert.x when the verticle instance is deployed. Don't call it yourself.<p>
    * If your verticle does things in it's startup which take some time then you can override this method
    * and complete the startPromise some time later when start up is complete.
    *
    * @param startPromise a promise which should be completed when verticle start-up is complete.
    */
  def start(startPromise: concurrent.Promise[Unit]): Unit = {
    start()
    startPromise.success(())
  }

  /**
    * Get the deployment ID of the verticle deployment
    *
    * @return the deployment ID
    */
  def deploymentID: String = javaVerticle.deploymentID()

  /**
    * Get the configuration of the verticle.
    * <p>
    * This can be specified when the verticle is deployed.
    *
    * @return the configuration
    */
  def config: JsonObject = javaVerticle.config()

  /**
    * Get the arguments used when deploying the Vert.x process.
    *
    * @return the list of arguments
    */
  def processArgs: mutable.Buffer[String] = javaVerticle.processArgs().asScala


  def asJava(): Verticle = new AbstractVerticle {
    private val that = ScalaVerticle.this
    override def init(vertx: io.vertx.core.Vertx, context: io.vertx.core.Context): Unit = {
      super.init(vertx, context)
      ScalaVerticle.this.init(vertx, context, this)
    }

    override def start(startFuture: Future[Void]): Unit = {
      val promise = Promise[Unit]()
      promise.future.onComplete{
        case Success(_) => startFuture.complete()
        case Failure(throwable) => startFuture.fail(throwable)
      }
      that.start(promise)
    }

    override def stop(stopFuture: Future[Void]): Unit = {
      val promise = Promise[Unit]()
      promise.future.onComplete{
        case Success(_) => stopFuture.complete()
        case Failure(throwable) => stopFuture.fail(throwable)
      }
      that.stop(promise)
    }
  }
}
