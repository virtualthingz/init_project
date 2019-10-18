package kr.supergate.shoppingfolder.domain.address;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "results")
public class Address {
    private Common common;
    private List<Juso> juso;
}