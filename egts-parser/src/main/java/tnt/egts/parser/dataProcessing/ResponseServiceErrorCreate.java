package tnt.egts.parser.dataProcessing;


import tnt.egts.parser.data.HeaderData;
import tnt.egts.parser.data.Outcoming;

public interface ResponseServiceErrorCreate {
    Outcoming createServiceNotFound(HeaderData hd);
    Outcoming createServiceDenied(HeaderData hd);
    Outcoming createServiceUnknown(HeaderData hd);
}
