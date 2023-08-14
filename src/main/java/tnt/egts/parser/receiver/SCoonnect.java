package tnt.egts.parser.receiver;

import java.io.IOException;

/**
 * opens socket port and activates listining
 */
public interface SCoonnect {
    void connect(int port) throws IOException;
}
