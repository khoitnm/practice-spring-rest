package org.tnmk.practicejson.pro05globalerrorhandler.common.tracing;


/**
 * Constants in this class will be used for both MDC Logging and AppDynamics.
 */
public interface TracingConstants {
  String CORRELATION_ID = "correlationId";
  String HTTP_REQUEST_URL = "httpRequestUrl";

}
