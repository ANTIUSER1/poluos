package tnt.egts.parser.data.analysis;


public interface BitsAnalizer {


    BitFlags optionAnalysis(String data);
    BitFlags compressingAnalysis(String data);

    BitFlags priorityAnalysis(String data);


    BitFlags  encryptionAnalysis(String data);


}
