package ru.tnt.EGTSparser.dataProcessing;

import ru.tnt.EGTSparser.data.HeaderData;
import ru.tnt.EGTSparser.data.Outcoming;

public interface ResponseModuleError {

    Outcoming createModuleFault(HeaderData hd);
    Outcoming createModulePowerFault(HeaderData hd);
    Outcoming createModuleProcessingFault(HeaderData hd);
    Outcoming createModuleProgramFault(HeaderData hd);
    Outcoming createModuleIOFault(HeaderData hd);
    Outcoming createModuleMemoryFault(HeaderData hd);

}
