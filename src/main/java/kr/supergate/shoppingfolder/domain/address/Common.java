package kr.supergate.shoppingfolder.domain.address;

import lombok.Data;

@Data
public class Common {
    private int totalCount;
    private int currentPage;
    private int countPerPage;
    private String errorCode;
    private String errorMessage;
}