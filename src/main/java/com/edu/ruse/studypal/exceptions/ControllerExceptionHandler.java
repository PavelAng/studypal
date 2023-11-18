package com.edu.ruse.studypal.exceptions;

import com.edu.ruse.studypal.security.responses.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.FileNotFoundException;

@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {
    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public MessageResponse fileNotFoundException(FileNotFoundException ex, WebRequest request) {
        return new MessageResponse(ex.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
    public MessageResponse maxUploadSizeExceededException(MaxUploadSizeExceededException ex, WebRequest request) {
        return new MessageResponse("File Size Exceeded");
    }
}
