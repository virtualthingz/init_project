package kr.supergate.shoppingfolder.domain;

import lombok.Data;

@Data
public class AgreementClause {
    private Integer id;
    private String title;
    private String description;
    private Boolean isMandatory;
}
