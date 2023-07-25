package ru.tnt.EGTSparser.crc.service;

public interface CRC {

    long calculate8(long[] data);
    long calculate16(long[] data);

}
