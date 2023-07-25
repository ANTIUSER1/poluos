package ru.transteft.tnt.crc.CRCmaker.service;

public interface CRC {

    long calculate8(long[] data);
    long calculate16(long[] data);

}
