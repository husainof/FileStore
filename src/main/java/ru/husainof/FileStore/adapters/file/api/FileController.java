package ru.husainof.FileStore.adapters.file.api;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.husainof.FileStore.domain.file.dto.FileDTO;
import ru.husainof.FileStore.domain.file.errors.FileNotFoundException;
import ru.husainof.FileStore.domain.file.models.File;
import ru.husainof.FileStore.domain.file.services.FileService;
import ru.husainof.FileStore.utils.AppErrorResponse;

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
        return this.convertToDTO(fileService.findById(id));
    }

    @GetMapping
    public List<FileDTO> findAllOrderByCreationDate() {
        return this.fileService.findAllOrderByCreationDate()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public int createFile(@RequestBody @Validated FileDTO fileDTO) {
        var id = this.fileService.save(this.convertToFile(fileDTO));
        return id;
    }

    @ExceptionHandler
    private ResponseEntity<AppErrorResponse> handleNotFoundException(
            FileNotFoundException fileNotFoundException
    ) {
        var response = new AppErrorResponse();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    private File convertToFile(FileDTO fileDTO ) {
        return mapper.map(fileDTO, File.class);
    }

    private FileDTO convertToDTO(File file) {
        return mapper.map(file, FileDTO.class);
    }
}
