package tnt.egts.parser.data.analysis;

/**
 * Flags enum controller
 * use for control bit flags? placed in some bytes
 */
public enum BitFlags {
    HOPTIONS_EXISTS, HOPTIONS_NOT_EXISTS,
    USE_COMPRESSION,  NO_USE_COMPRESSION,
    USE_ENCRYPTION,  NO_USE_ENCRYPTION,
    PRIORITY_LEVEL_SUPERHARD, PRIORITY_LEVEL_HARD,
    PRIORITY_LEVEL_MIDDLE, PRIORITY_LEVEL_LOW
}
