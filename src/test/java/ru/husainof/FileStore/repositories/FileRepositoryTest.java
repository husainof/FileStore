package ru.husainof.FileStore.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileRepositoryTest {
    @Autowired
    private FileRepository repo;

    private File file;
    @BeforeEach
    void setUp(){
        file = new File(
                Base64.getDecoder().decode("SGVsbG8gV29ybGQ="),
                "Test",
                LocalDateTime.now(),
                "Test description"
        );
    }

    @Test
    @Rollback(value = false)
    public void save_shouldSaveFile_whenCalled(){
        repo.save(file);

        final File foundFile = repo.findById(file.getId()).get();

        assertNotNull(foundFile);

        assertThat(foundFile.getId()).isEqualTo(file.getId());
        assertThat(foundFile.getTitle()).isEqualTo("Test");
    }

    @Test
    public void findById_shouldFindFile_whenExists(){
        int id = repo.save(file).getId();

        File actual = repo.findById(id).get();

        assertNotNull(actual);
        assertEquals(file, actual);
    }

    @Test
    public void findAll_shouldFindAllFiles_whenExists(){
        repo.save(file);
        repo.save(file);

        List<File> files = repo.findAll();

        assertNotNull(files);
        assertThat(files.size()).isGreaterThan(0);
    }

    @Test
    public void findAllByOrderByCreationDateAsc_shouldFindAllFilerAndSortByCreationDateDesc_whenExists(){
        repo.save(file);

        file = new File(
                Base64.getDecoder().decode("SGVsbG8gV29ybGQ="),
                "Test",
                LocalDateTime.now().plusDays(1),
                "Test description"
        );
        repo.save(file);

        List<File> files = repo.findAllByOrderByCreationDateAsc();

        assertNotNull(files);
        assertThat(files.size()).isGreaterThan(0);
        assertThat(files.get(0).getCreationDate()).isBefore(files.get(1).getCreationDate());
    }

}