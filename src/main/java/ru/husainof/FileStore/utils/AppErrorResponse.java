package ru.husainof.FileStore.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Data
public class AppErrorResponse {
    private Date timestamp;
    private int status;
    private List<String> errors;
}
