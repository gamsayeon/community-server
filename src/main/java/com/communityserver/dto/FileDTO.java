package com.communityserver.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FileDTO {
    private int fileNumber;
    private int postNumber;
    private String path;
    private String fileName;
    private String extension;

    public void FileDTO(){}

    public void FileDTO(int fileNumber, int postNumber, String path, String fileName, String extension){
        this.fileNumber = fileNumber;
        this.postNumber = postNumber;
        this.path = path;
        this.fileName = fileName;
        this.extension = extension;
    }
}
