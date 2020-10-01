package br.com.harisson.core.support;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorDetail implements Serializable {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SS")
    private LocalDateTime timestamp;
    private String message;
    private int status;
    private String error;
}
