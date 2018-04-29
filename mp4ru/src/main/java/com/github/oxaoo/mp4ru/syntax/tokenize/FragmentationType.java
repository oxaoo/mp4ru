package com.github.oxaoo.mp4ru.syntax.tokenize;

/**
 * The enum Tokenization type.
 *
 * @author Alexander Kuleshov
 * @version 1.0.0
 * @since 30.03.2017
 */
public enum FragmentationType {
    /**
     * Tokenization by text tokenization type.
     */
    NO_FRAGMENTATION,
    /**
     * Tokenization by sentence, which new sentence begin token with id:#0
     */
    FRAGMENTATION
}
