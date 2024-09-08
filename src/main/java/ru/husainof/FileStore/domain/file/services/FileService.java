package ru.husainof.FileStore.domain.file.services;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.husainof.FileStore.domain.file.errors.FileNotFoundException;
import ru.husainof.FileStore.domain.file.models.File;
import ru.husainof.FileStore.domain.file.repositories.FileRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<File> getPageOrderByCreationDate(int page, int size) {
        var sort = Sort.by(Sort.Direction.ASC, "creationDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        return  this.fileRepository.findAll(pageable).stream().toList();
    }

    public File findOne(int id) {
        var optional = fileRepository.findById(id);
        return optional.orElseThrow(FileNotFoundException::new);
    }

    @Transactional
    public int save(File file) {
        return this.fileRepository.save(file).getId();
    }
}
