package com.github.oxaoo.mp4ru.syntax.tokenize;

import com.github.oxaoo.mp4ru.exceptions.ReadInputTextException;

import java.util.List;

/**
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 12.02.2017
 */
public interface Tokenizer {

    List<String> tokenization() throws ReadInputTextException;
}
