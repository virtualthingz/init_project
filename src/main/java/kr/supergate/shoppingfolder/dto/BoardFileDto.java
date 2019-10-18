package kr.supergate.shoppingfolder.dto;

import lombok.Data;

@Data
public class BoardFileDto {

    private int idx;

    private int boardIdx;

    private String originalFileName;

    private String storedFilePath;

    private long fileSize;
}
