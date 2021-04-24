package com.szymanski.yamlobjectmapper.structure;

public enum YamlLineType {

    EMPTY_LINE,
    DICTIONARY,
    SCALAR,
    COMMENT_LINE,
    BLOCK_MAPPING,
    BLOCK_SEQUENCE,
    BLOCK_SEQUENCE_ELEMENT,
    FLOW_SEQUENCE
}
