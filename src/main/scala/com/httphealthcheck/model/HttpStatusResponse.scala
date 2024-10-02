package com.httphealthcheck.model

import akka.http.scaladsl.model.StatusCode

case class HttpSuccessStatusResponse(endpoint: String, code: StatusCode)
case class HttpExceptionResponse(endpoint: String, exception: Throwable)