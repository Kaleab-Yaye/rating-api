package com.rating.api.web.controller.users.weberr;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler // could be left blank as spring will outematically much the ecption this
  // methode is suppesed to handel
  public ResponseEntity<ProblemDetail> handleArgumentNotValid(MethodArgumentNotValidException ex) {
    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            "Invalid details"); // an object mean tto return error in jason format
    List<String> errors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(err -> err.getField() + ":" + err.getDefaultMessage())
            .toList(); // we travers along all the erros that lead to this exception and store them
    // in list to be returend
    problemDetail.setProperty("errors", errors); // you set the raw proproties of the error
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(problemDetail); // you build the Response Enitey
  }

  @ExceptionHandler
  public ResponseEntity<ProblemDetail> handelHttpMessageNotReadableException(
      HttpMessageNotReadableException ex) {
    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST, "Invalid JSON or Unacceptable Format");
    problemDetail.setProperty("errors", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
  }
}
