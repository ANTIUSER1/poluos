package ru.tnt.EGTSparser.data;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class SDR {

    private Byte[ ] content;
}
