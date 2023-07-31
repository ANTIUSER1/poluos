package tnt.egts.parser.parser;


import tnt.egts.parser.data.Incoming;
import tnt.egts.parser.errors.IncorrectDataException;

@FunctionalInterface
public interface ConvertIncomingData {

    Incoming create(byte[] data) throws IncorrectDataException;

}
