package ru.tnt.EGTSparser.dataProcessing;

import ru.tnt.EGTSparser.data.Outcoming;

public interface NormalProcessingCreate {

    Outcoming prepareData(byte[] data);
}
