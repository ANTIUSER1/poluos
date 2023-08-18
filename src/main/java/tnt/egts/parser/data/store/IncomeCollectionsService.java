package tnt.egts.parser.data.store;

import org.springframework.stereotype.Service;
import tnt.egts.parser.util.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class IncomeCollectionsService {

    List<IncomeDataStorage> incomeDataStorageList = new ArrayList<>();

    public void add(IncomeDataStorage ds) {
        incomeDataStorageList.add(ds);
    }

    public void printCollectionAll(){
        System.out.println(incomeDataStorageList);
    }
    public void printCollectionHeadersOnly(){
        for( IncomeDataStorage  ids:incomeDataStorageList){
            System.out.println(ArrayUtils.arrayPrintToScreen(ids.getPackageHeader()));
        }
    }

    public void printCollectionSFRDOnly(){
        for( IncomeDataStorage  ids:incomeDataStorageList){
            System.out.println(ArrayUtils.arrayPrintToScreen(ids.getPackagSFRD()));
        }
    }

    public void printCollectionFullPacket(){
        for( IncomeDataStorage  ids:incomeDataStorageList){
            System.out.println(ArrayUtils.arrayPrintToScreen(ids.getFullPacket()));
        }
    }


}
