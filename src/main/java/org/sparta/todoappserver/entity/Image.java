package org.sparta.todoappserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "image")
public class Image extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="imagename")
    private String filename;
    @Column(name="extension")
    private String extension;
    @Column(name="size")
    private long size;
    @Column(name = "data",columnDefinition = "blob")
    @Lob
    private byte[] data;

    public Image(String filename, String extension, long size, byte[] bytes) {
        this.filename = filename;
        this.extension = extension;
        this.size = size;
        this.data = bytes;
    }
}
