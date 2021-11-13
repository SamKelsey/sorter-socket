package com.samkelsey.sortersocket.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class ApiException {

    @NotNull(message = "Http status cannot be null")
    @JsonProperty("status")
    HttpStatus status;

    @JsonProperty("message")
    String message;

}
