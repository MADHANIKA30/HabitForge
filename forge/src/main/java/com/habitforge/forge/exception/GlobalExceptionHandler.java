package com.habitforge.forge.exception;
import jakarta.servlet.http.HttpServletRequest; import org.springframework.dao.DataIntegrityViolationException; import org.springframework.http.*; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*; import java.time.LocalDateTime; import java.util.*;
@RestControllerAdvice public class GlobalExceptionHandler {
 @ExceptionHandler(NotFoundException.class) ResponseEntity<ApiError> notFound(NotFoundException e,HttpServletRequest r){return build(HttpStatus.NOT_FOUND,e.getMessage(),r.getRequestURI(),Map.of());}
 @ExceptionHandler(ConflictException.class) ResponseEntity<ApiError> conflict(ConflictException e,HttpServletRequest r){return build(HttpStatus.CONFLICT,e.getMessage(),r.getRequestURI(),Map.of());}
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ApiError> validation(MethodArgumentNotValidException e,HttpServletRequest r){Map<String,String> m=new LinkedHashMap<>();e.getBindingResult().getFieldErrors().forEach(x->m.put(x.getField(),x.getDefaultMessage()));return build(HttpStatus.BAD_REQUEST,"Validation failed",r.getRequestURI(),m);}
 @ExceptionHandler(DataIntegrityViolationException.class) ResponseEntity<ApiError> integrity(DataIntegrityViolationException e,HttpServletRequest r){return build(HttpStatus.CONFLICT,"The request conflicts with existing data",r.getRequestURI(),Map.of());}
 @ExceptionHandler(IllegalArgumentException.class) ResponseEntity<ApiError> bad(IllegalArgumentException e,HttpServletRequest r){return build(HttpStatus.BAD_REQUEST,e.getMessage(),r.getRequestURI(),Map.of());}
 @ExceptionHandler(Exception.class) ResponseEntity<ApiError> generic(Exception e,HttpServletRequest r){return build(HttpStatus.INTERNAL_SERVER_ERROR,"Unexpected server error",r.getRequestURI(),Map.of());}
 private ResponseEntity<ApiError> build(HttpStatus s,String msg,String path,Map<String,String> v){return ResponseEntity.status(s).body(new ApiError(LocalDateTime.now(),s.value(),s.getReasonPhrase(),msg,path,v));}
}
