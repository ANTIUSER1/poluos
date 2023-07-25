package ru.tnt.EGTSparser.dataProcessing;

import ru.tnt.EGTSparser.data.HeaderData;
import ru.tnt.EGTSparser.data.Outcoming;

public interface ResponseRouteErorCreate {
    Outcoming createRouteNotFound(HeaderData hd);;
    Outcoming createRouteClosed(HeaderData hd);
    Outcoming createRouteDenied(HeaderData hd);
}
