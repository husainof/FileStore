package ru.husainof.FileStore.domain.file.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class FileDTO {
    @NotNull
    private byte[] data;

    @NotEmpty
    private String title;

    @NotNull
    private Date creationDate;

    @NotNull
    private String description;
}
