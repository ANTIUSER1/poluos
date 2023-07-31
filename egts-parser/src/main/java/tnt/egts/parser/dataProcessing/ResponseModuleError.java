package tnt.egts.parser.dataProcessing;


import tnt.egts.parser.data.*;

public interface ResponseModuleError {

    Outcoming createModuleFault(HeaderData hd);
    Outcoming createModulePowerFault(HeaderData hd);
    Outcoming createModuleProcessingFault(HeaderData hd);
    Outcoming createModuleProgramFault(HeaderData hd);
    Outcoming createModuleIOFault(HeaderData hd);
    Outcoming createModuleMemoryFault(HeaderData hd);

}
