package ru.tnt.EGTSparser.data;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * EGTS_PT_SIGNED_APPDATA
 *
 * Container for incoming byte-data
 * the fields name are the same as in EGTS-protocol
 */
@Builder
@Data
@ToString
public class BodyData_SIGNED_APPDATA implements Incoming{
}
