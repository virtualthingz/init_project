package kr.supergate.shoppingfolder.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel(value = "Shoppingmall", description = "shoppingmall ")
public class Shoppingmall extends WithImage{

    private String id;
    private String name;
    private String bannerUrl;
    private String detailUrl;
    private int displayOrder;
    private String createdDate;
    private String updatedDate;

    @Override
    public String getImagePath() {
        return this.getId();
    }
}

