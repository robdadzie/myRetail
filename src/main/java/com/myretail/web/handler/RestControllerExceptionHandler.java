package com.myretail.web.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class RestControllerExceptionHandler {
  private static final Logger LOG = LoggerFactory.getLogger(RestControllerExceptionHandler.class);


  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(value = HttpClientErrorException.class)
  @ResponseBody
  public ErrorData handleHttpClientExceptions(HttpClientErrorException ex) {
    HttpStatus httpStatus = HttpStatus.valueOf(ex.getRawStatusCode());
    String message = "Oops!! something went wrong, please try again later.";

    if (httpStatus != null) {
      switch (httpStatus) {
        case BAD_REQUEST:
          message = "Please verify the URL syntax, refer to the API's documentation.";
          break;
        case UNAUTHORIZED:
          message = "The credentials specified is incorrect.";
          break;
        case NOT_FOUND:
          message = "The product ID specified is either invalid or discontinued.";
        default:
          break;
      }
    }
    ErrorData errorData = new ErrorData(httpStatus.value(), message);
    LOG.error("Error", ex);
    return errorData;
  }


  public class ErrorData {
    private int    status;
    private String message;

    public ErrorData(int status, String message) {
      this.status = status;
      this.message = message;
    }

    public int getStatus() {
      return status;
    }

    public void setStatus(int status) {
      this.status = status;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }
  }


}
