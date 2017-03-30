package com.github.oxaoo.mp4ru.syntax.tagging;

import com.github.oxaoo.mp4ru.syntax.tokenize.FragmentationType;

import java.util.LinkedList;
import java.util.List;

/**
 * The stateful token handler which implement the TokenHandler class.
 *
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 12.02.2017
 */
public class StatefulTokenHandler implements AdvancedTokenHandler<Conll> {
    private List<Conll> tokens = new LinkedList<>();
    private int counter = 0;
    private final FragmentationType fragmentationType;

    public StatefulTokenHandler(FragmentationType fragmentationType) {
        this.fragmentationType = fragmentationType;
    }

    @Override
    public void token(String token, String pos, String lemma) {
        this.tokens.add(new Conll(++counter, token, lemma, pos));
        if (fragmentationType == FragmentationType.FRAGMENTATION
                && this.isTerminateMarks(token)) this.counter = 0;
    }

    @Override
    public List<Conll> peekTokens() {
        return this.tokens;
    }

    @Override
    public List<Conll> getTokens() {
        List<Conll> tmp = this.tokens;
        tokens = new LinkedList<>();
        return tmp;
    }

    private boolean isTerminateMarks(String token) {
        return token.replaceAll("[.!?]", "").length() == 0;
    }
}

//TODO: think about make Future job as result.
