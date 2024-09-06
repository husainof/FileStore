package ru.husainof.FileStore.domain.file.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.husainof.FileStore.domain.file.errors.FileNotFoundException;
import ru.husainof.FileStore.domain.file.models.File;
import ru.husainof.FileStore.domain.file.repositories.FileRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<File> findAllOrderByCreationDate(int page, int size) {
        return  this.fileRepository.findAll(
                PageRequest.of(
                        page,
                        size,
                        Sort.by(Sort.Direction.ASC, "creationDate"))
        ).stream().toList();
    }

    public File findById(int id) {
        var optional = fileRepository.findById(id);
        return optional.orElseThrow(FileNotFoundException::new);
    }

    @Transactional
    public int save(File file) {
        return this.fileRepository.save(file).getId();
    }
}
