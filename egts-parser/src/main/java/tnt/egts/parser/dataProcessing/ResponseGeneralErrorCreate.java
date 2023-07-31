package tnt.egts.parser.dataProcessing;


import tnt.egts.parser.data.*;

public interface ResponseGeneralErrorCreate {

    Outcoming createDecryptError(HeaderData hd);
    Outcoming createIncorrectHeadForm(HeaderData hd);
    Outcoming createIncorrectDataForm(BodyData_APPDATA bda);
    Outcoming createUnsupportedProtocol(HeaderData hd);
    Outcoming createIncorrectParamsNumber(HeaderData hd);
    Outcoming createInvalidDataLength(HeaderData hd);
    Outcoming createProcDenied(HeaderData hd);
    Outcoming createInvalidAddress(HeaderData hd);
    Outcoming createTtlExpired(HeaderData hd);
    Outcoming createNoConfirmation(HeaderData hd);
    Outcoming createNoObjectFound(HeaderData hd);
    Outcoming createNoEventFound(HeaderData hd);
    Outcoming createAllreadyExists(HeaderData hd);
    Outcoming createAuthDenied(HeaderData hd);
    Outcoming createIdNotFound(HeaderData hd);
    Outcoming createIncorrectDataTime(HeaderData hd);
    Outcoming createIOError(HeaderData hd);
    Outcoming createNoResourceAvailable(HeaderData hd);
    Outcoming createTestFailed(HeaderData hd);




}
