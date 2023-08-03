package tnt.egts.parser.data;


import tnt.egts.parser.data.Incoming;
import tnt.egts.parser.errors.IncorrectDataException;
import tnt.egts.parser.errors.NumberArrayDataException;

@FunctionalInterface
public interface ConvertIncomingData {

    Incoming create(byte[] income) throws IncorrectDataException, NumberArrayDataException;

}
