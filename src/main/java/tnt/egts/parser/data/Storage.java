package tnt.egts.parser.data;

import tnt.egts.parser.data.store.BNSODataStorage;
import tnt.egts.parser.data.store.ResponseDataStorage;
import tnt.egts.parser.errors.NumberArrayDataException;

/**
 * Creating storage for BNSO && Response
 */
public interface Storage {

     /**
      * Creating Response data
      * @param income
      * @return
      * @throws NumberArrayDataException
      */
     ResponseDataStorage createResponseStorage(byte[] income) throws NumberArrayDataException;


     /**
      * Creating BNSO data
      * @param income
      * @return
      * @throws NumberArrayDataException
      */
     BNSODataStorage createBNSOStorage(byte[] income) throws NumberArrayDataException;


}
