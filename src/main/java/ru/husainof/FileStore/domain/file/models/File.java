package ru.husainof.FileStore.domain.file.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "data")
    private String data;

    @Column(name = "title")
    private String title;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "description")
    private String description;
}
