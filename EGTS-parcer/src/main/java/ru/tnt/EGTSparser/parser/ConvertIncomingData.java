package ru.tnt.EGTSparser.parser;

import ru.tnt.EGTSparser.data.Incoming;
import ru.tnt.EGTSparser.errors.IncorrectDataException;

@FunctionalInterface
public interface ConvertIncomingData {

    Incoming create(byte[] data) throws IncorrectDataException;

}
