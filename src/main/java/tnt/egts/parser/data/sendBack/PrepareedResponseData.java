package tnt.egts.parser.data.sendBack;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import tnt.egts.parser.commontasks.OutcomeIdent;
import tnt.egts.parser.util.ArrayUtils;
import tnt.egts.parser.util.ByteFixPositions;

@Builder
@Data
@ToString
@Slf4j
public class PrepareedResponseData implements OutcomeIdent {

    private byte[] responseHead;

    /**
     * only PackageHead -- скс8=free
     */
    private byte[] properPackageHeader;

    private byte[] sfrd;

    private byte[] data;

    private byte code;

    @Override
    public void prepareAuthData() {
        log.info("Create AUTH-Answer data start");
        short sffdLength = (short) sfrd.length;

        byte[] sfrdArray = ArrayUtils.shortToByteArray(sffdLength);
        responseHead[ByteFixPositions.PACKAGE_TYPE_INDEX] = code;
        responseHead[5] = sfrdArray[1];
        responseHead[6] = sfrdArray[0];
        properPackageHeader = ArrayUtils.getFixedLengthSubArray(responseHead, 0, responseHead.length - 1);
        data = ArrayUtils.joinArrays(responseHead, sfrd);
        log.info("Answer Data: \n " + ArrayUtils.arrayPrintToScreen(data) + " \n");
        log.info("Create Answer data finish");
    }
}
