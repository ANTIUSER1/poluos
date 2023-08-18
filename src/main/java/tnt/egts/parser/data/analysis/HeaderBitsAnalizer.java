package tnt.egts.parser.data.analysis;


public interface HeaderBitsAnalizer {


    boolean routeOptionBitExist(String data);
    boolean compressingAnalysis(String data);

    boolean priorityOptionBitExist(String data);


    boolean encryptionOptionBitExist(String data);


}
