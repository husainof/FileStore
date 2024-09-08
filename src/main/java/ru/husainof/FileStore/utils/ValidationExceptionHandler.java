package ru.husainof.FileStore.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.husainof.FileStore.domain.file.dto.FileDTO;
import ru.husainof.FileStore.domain.file.models.File;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        StringBuilder errorMsg = new StringBuilder();
        for(FieldError error: ex.getFieldErrors()) {
            String jsonAlias = getJsonAlias(error.getField());
            var fieldName = jsonAlias != null ? jsonAlias : error.getField();
            errorMsg.append(fieldName)
                    .append(" - ")
                    .append(error.getDefaultMessage())
                    .append(";  ");
        }


        var appError = new AppErrorResponse(
                new Date(),
                HttpStatus.BAD_REQUEST.value(),
                List.of(errorMsg.toString())
        );

        return new ResponseEntity<>(appError, HttpStatus.BAD_REQUEST);
    }

    private String getJsonAlias(String fieldName) {
        try {
            Field field = FileDTO.class.getDeclaredField(fieldName);
            JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
            return jsonProperty != null ? jsonProperty.value() : null;
        } catch (NoSuchFieldException e) {
            return null;
        }
    }
}
