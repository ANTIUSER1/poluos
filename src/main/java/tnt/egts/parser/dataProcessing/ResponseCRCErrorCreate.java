package tnt.egts.parser.dataProcessing;


import tnt.egts.parser.data.HeaderData;
import tnt.egts.parser.data.Outcoming;

public interface ResponseCRCErrorCreate {

    Outcoming createHeaderCRCError(HeaderData hd);
    Outcoming createDataCRCError(HeaderData hd);
}
