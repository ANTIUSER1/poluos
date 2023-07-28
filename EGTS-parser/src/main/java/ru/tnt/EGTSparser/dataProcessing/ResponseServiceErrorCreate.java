package ru.tnt.EGTSparser.dataProcessing;

import ru.tnt.EGTSparser.data.HeaderData;
import ru.tnt.EGTSparser.data.Outcoming;

public interface ResponseServiceErrorCreate {
    Outcoming createServiceNotFound(HeaderData hd);
    Outcoming createServiceDenied(HeaderData hd);
    Outcoming createServiceUnknown(HeaderData hd);
}
