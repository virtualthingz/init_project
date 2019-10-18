package kr.supergate.shoppingfolder.exception.model;

import lombok.Data;

@Data
public class ApiError {
    private String code;
    private String message;

    public ApiError(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
