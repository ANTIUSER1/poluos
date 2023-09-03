package tnt.egts.parser.data.store;

import org.springframework.stereotype.Service;
import tnt.egts.parser.util.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class IncomeCollectionsService {

    List<ResponseDataStorage> responseDataStorageList = new ArrayList<>();

    public void add(ResponseDataStorage ds) {
        responseDataStorageList.add(ds);
    }

    public void printCollectionAll(){
        System.out.println(responseDataStorageList);
    }
    public void printCollectionHeadersOnly(){
        for( ResponseDataStorage ids: responseDataStorageList){
            System.out.println(ArrayUtils.arrayPrintToScreen(ids.getPackageHeader()));
        }
    }

    public void printCollectionSFRDOnly(){
        for( ResponseDataStorage ids: responseDataStorageList){
            System.out.println(ArrayUtils.arrayPrintToScreen(ids.getPackagSFRD()));
        }
    }

    public void printCollectionFullPacket(){
        for( ResponseDataStorage ids: responseDataStorageList){
            System.out.println(ArrayUtils.arrayPrintToScreen(ids.getFullPacket()));
        }
    }


}
