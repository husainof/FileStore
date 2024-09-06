package ru.husainof.FileStore.domain.file.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class FileDTO {
    @NotNull
    @JsonProperty("data")
    private byte[] data;

    @JsonProperty("title")
    @NotEmpty()
    private String title;

    @JsonProperty("creation_date")
    @NotNull
    private Date creationDate;

    @JsonProperty("description")
    @NotNull
    private String description;
}
