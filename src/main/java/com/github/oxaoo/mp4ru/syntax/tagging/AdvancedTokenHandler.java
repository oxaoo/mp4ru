package com.github.oxaoo.mp4ru.syntax.tagging;

import org.annolab.tt4j.TokenHandler;

import java.util.List;

/**
 * @author Alexander Kuleshov
 * @version 0.1.0
 * @since 12.02.2017
 */
public interface AdvancedTokenHandler<T> extends TokenHandler<String> {
    List<T> peekTokens();

    List<T> getTokens();
}
