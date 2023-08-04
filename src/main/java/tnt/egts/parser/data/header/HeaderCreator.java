package tnt.egts.parser.data.header;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tnt.egts.parser.data.ConvertIncomingData;
import tnt.egts.parser.data.Incoming;
import tnt.egts.parser.data.appdata.APPDATAService;
import tnt.egts.parser.errors.IncorrectDataException;
import tnt.egts.parser.util.ArrayUtils;
import tnt.egts.parser.util.ByteFixValues;
import tnt.egts.parser.util.ByteFixedPositions;
import tnt.egts.parser.util.StringFixedBeanNames;


@Component (StringFixedBeanNames.HEADER_CREATOR_BEAN)
@Slf4j
public class HeaderCreator implements ConvertIncomingData {

    @Autowired
    private APPDATAService appdataService;



    @Override
    public Incoming create(byte[] income) throws IncorrectDataException {
        log.info("Start parsing incoming data");
        int hcsPos = ByteFixedPositions.getHCSIndex(income);
        HeaderData hd = HeaderData.builder()
                .hasOptions(income[ByteFixedPositions.HEAD_LENGTH_INDEX] == ByteFixValues.HEAD_MAX_LENGTH)

                .packageHead(ArrayUtils.getSubArrayFromTo(income, 0, hcsPos+1))
                .content(ArrayUtils.getSubArrayFromTo(income, 0, hcsPos))
                .build();

         appdataService.setPackageHead(hd.getPackageHead());

        log.info(hcsPos + "  Finish parsing incoming data header normally  " +
                 "\n" + hd);
        System.out.println("\n ||||||///// hd.getContent()  \n   "+ArrayUtils.arrayPrintToScreen(hd.getContent()  )+"\n"
        +"  hd.getPackageHead()  \n   "+ArrayUtils.arrayPrintToScreen(hd.getPackageHead()  )+"\n");

        return hd;
    }
}
