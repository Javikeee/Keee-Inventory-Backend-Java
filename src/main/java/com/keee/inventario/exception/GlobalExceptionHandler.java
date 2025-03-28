package com.keee.inventario.exception;

import com.keee.inventario.exception.entity.ApiError;
import com.keee.inventario.helper.MessageHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final MessageHelper messageHelper;

    public GlobalExceptionHandler(MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

    /**
     * ❌ Manejo de recurso no encontrado (ejemplo: categoría no existe)
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(
            ResourceNotFoundException ex, WebRequest request,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        String message = messageHelper.getMessage("error.notfound", locale);

        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                message,
                request.getDescription(false)
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    /**
     * ❌ Manejo de datos inválidos en las solicitudes
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        String message = messageHelper.getMessage("error.validation", locale);

        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        error -> error.getField(),
                        error -> error.getDefaultMessage(),
                        (existing, replacement) -> existing // Para evitar colisiones en campos repetidos
                ));

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.put("message", message);
        response.put("errors", errors);
        response.put("path", request.getDescription(false));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * ❌ Manejo de cualquier otra excepción no controlada
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(
            Exception ex, WebRequest request,
            @RequestHeader(value = "Accept-Language", defaultValue = "en") String language) {

        Locale locale = Locale.forLanguageTag(language);
        String message = messageHelper.getMessage("error.unexpected", locale);

        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                message,
                request.getDescription(false)
        );
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
