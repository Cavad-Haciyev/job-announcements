package com.example.jobannouncement.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


  public ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
    return new ResponseEntity<>(apiError, apiError.getHttpStatus());
  }

  @ExceptionHandler(value = UserNotFoundException.class)
  public ResponseEntity<Object> userNotFoundException(UserNotFoundException exception) {
    return buildResponseEntity(
        new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND, 404, "User Not Found"));
  }

//
//  @Override
//  protected ResponseEntity<Object> handleMethodArgumentNotValid(
//      MethodArgumentNotValidException ex,
//      HttpHeaders headers, HttpStatus status, WebRequest request) {
//
//    Map<String, Object> responseBody = new LinkedHashMap<>();
//    responseBody.put("Timestamp", new Date());
//    responseBody.put("Status", status.value());
//
//    List<String> errors = ex.getBindingResult().getFieldErrors()
//        .stream()
//        .map(x -> x.getDefaultMessage())
//        .collect(Collectors.toList());
//
//    responseBody.put("Errors", errors);
//
//    return new ResponseEntity<>(responseBody, headers, status);
//  }

}
