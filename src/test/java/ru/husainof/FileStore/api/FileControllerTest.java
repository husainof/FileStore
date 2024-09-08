package ru.husainof.FileStore.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.validation.BindingResult;
import ru.husainof.FileStore.adapters.file.api.FileController;
import ru.husainof.FileStore.domain.file.dto.FileDTO;
import ru.husainof.FileStore.domain.file.models.File;
import ru.husainof.FileStore.domain.file.services.FileService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FileStoreControllerTest {

    private final static int ID = 1;

    @Mock
    private FileService service;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private FileController controller;

    @Test
    void createFile() {
        final File file = mock(File.class);
        final FileDTO fileDTO = mock(FileDTO.class);
        when(modelMapper.map(fileDTO,File.class)).thenReturn(file);
        //final BindingResult bindingResult = mock(BindingResult.class);

        controller.createFile(fileDTO);

        verify(modelMapper).map(fileDTO, File.class);
        verify(service).save(file);
    }

    @Test
    void getAllFilesOrderByCreationDate() {
        final File file1 = mock(File.class);
        final File file2 = mock(File.class);
        List<File> files = new ArrayList<>(List.of(file1, file2));
        when(service.findAllOrderByCreationDate()).thenReturn(files);

        final FileDTO fileDTO1 = mock(FileDTO.class);
        when(modelMapper.map(file1, FileDTO.class)).thenReturn(fileDTO1);
        final FileDTO fileDTO2 = mock(FileDTO.class);
        when(modelMapper.map(file2, FileDTO.class)).thenReturn(fileDTO2);
        List<FileDTO> filesDTO = new ArrayList<>(List.of(fileDTO1, fileDTO2));

        final List<FileDTO> actual = controller.getAllFilesOrderByCreationDate();


        assertNotNull(actual);
        assertEquals(filesDTO, actual);
        verify(service).findAllOrderByCreationDate();
    }

    @Test
    void getFile() {
        final File file = mock(File.class);
        when(service.findOne(ID)).thenReturn(file);
        final FileDTO fileDTO = mock(FileDTO.class);
        when(modelMapper.map(file, FileDTO.class)).thenReturn(fileDTO);

        final FileDTO actual = controller.getFile(ID);

        assertNotNull(actual);
        assertEquals(fileDTO, actual);
        verify(service).findOne(ID);
        verify(modelMapper).map(file, FileDTO.class);
    }

}