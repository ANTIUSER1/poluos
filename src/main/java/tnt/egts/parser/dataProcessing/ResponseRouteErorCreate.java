package tnt.egts.parser.dataProcessing;


import tnt.egts.parser.data.HeaderData;
import tnt.egts.parser.data.Outcoming;

public interface ResponseRouteErorCreate {
    Outcoming createRouteNotFound(HeaderData hd);
    Outcoming createRouteClosed(HeaderData hd);
    Outcoming createRouteDenied(HeaderData hd);
}
