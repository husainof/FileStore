package ru.husainof.FileStore.adapters.file.api;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.husainof.FileStore.domain.file.dto.FileDTO;
import ru.husainof.FileStore.domain.file.errors.FileNotFoundException;
import ru.husainof.FileStore.domain.file.models.File;
import ru.husainof.FileStore.domain.file.services.FileService;
import ru.husainof.FileStore.utils.AppErrorResponse;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/files")
public class FileController {
    private final FileService fileService;
    private final ModelMapper mapper;

    @Autowired
    public FileController(FileService fileService, ModelMapper mapper) {
        this.fileService = fileService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public FileDTO findOne(@PathVariable int id) {
        return this.convertToDTO(fileService.findOne(id));
    }

    @GetMapping
    public List<FileDTO> getPageOrderByCreationDate(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size
    ) {
    return this.fileService.getPageOrderByCreationDate(page, size)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<Integer> createFile(
           @Valid @RequestBody FileDTO fileDTO
    ) {
        var id = fileService.save(this.convertToFile(fileDTO));
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @ExceptionHandler
    private ResponseEntity<AppErrorResponse> handleNotFoundException(
            FileNotFoundException fileNotFoundException
    ) {
        var response = new AppErrorResponse(
                new Date(),
                HttpStatus.NOT_FOUND.value(),
                List.of("file not found")
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    private File convertToFile(FileDTO fileDTO ) {
        return mapper.map(fileDTO, File.class);
    }

    private FileDTO convertToDTO(File file) {
        return mapper.map(file, FileDTO.class);
    }
}
