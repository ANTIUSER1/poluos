package ru.tnt.EGTSparser.dataProcessing;

import ru.tnt.EGTSparser.data.HeaderData;
import ru.tnt.EGTSparser.data.Outcoming;

public interface ResponseCRCErrorCreate {

    Outcoming createHeaderCRCError(HeaderData hd);
    Outcoming createDataCRCError(HeaderData hd);
}
