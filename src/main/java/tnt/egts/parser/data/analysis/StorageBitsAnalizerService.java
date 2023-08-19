package tnt.egts.parser.data.analysis;

import org.springframework.stereotype.Service;
import tnt.egts.parser.errors.InvalidPriorityException;

@Service
public class StorageBitsAnalizerService implements StorageBitsAnalizer{

    @Override
    public boolean sourceServiceOnDevice(String data) {
         return data.charAt(  7)=='1';
    }

    @Override
    public boolean reciplentServiceOnDevice(String data) {
        return data.charAt(6)=='1';
    }

    @Override
    public boolean inGroup(String data) {
        return data.charAt(5)=='1';
    }

    @Override
    public ProcessingPriority getRecordProcessingPriority(String data) {
        String tmp=""+data.charAt(3) +data.charAt(4);
        if(tmp.equals("00"))return ProcessingPriority.HIGH_TOP;
        else  if(tmp.equals("01"))return ProcessingPriority.HIGH;
        else  if(tmp.equals("10"))return ProcessingPriority.MIDDLE;
        else  if(tmp.equals("00"))return ProcessingPriority.LOW;
        else
           new InvalidPriorityException("Invalid ProcessPriorityData");
        return  null;
    }

    @Override
    public boolean timeFieldExists(String data) {
        return data.charAt(  2)=='1';
    }

    @Override
    public boolean eventFieldExists(String data) {
        return data.charAt(  1)=='1';
    }

    @Override
    public boolean objectFieldExists(String data) {
        return data.charAt(  0)=='1';
    }
}
