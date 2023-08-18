package tnt.egts.parser.data.store;

import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Component;
import tnt.egts.parser.commontasks.IncomeIdent;

import java.util.*;


@Component
@Getter
@ToString
public class ComponentsStoring {

     List<IncomeIdent> incomes = new ArrayList<>();

    public void append(IncomeIdent  obj){
        incomes.add(  obj);
    }
}
