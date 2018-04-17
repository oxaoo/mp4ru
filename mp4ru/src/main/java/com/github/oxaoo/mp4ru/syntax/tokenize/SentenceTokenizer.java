package com.github.oxaoo.mp4ru.syntax.tokenize;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

/**
 * The sentence tokenizer, breaking the text to the sentences.
 *
 * @author Alexander Kuleshov
 * @version 0.2.0
 * @since 18.08.2017
 */
public class SentenceTokenizer implements Tokenizer {

    @Override
    public List<String> tokenization(String text) {
        List<String> sentences = new ArrayList<>();
        BreakIterator iterator = BreakIterator.getSentenceInstance();
        iterator.setText(text);
        int start = iterator.first();
        for (int end = iterator.next();
             end != BreakIterator.DONE;
             start = end, end = iterator.next()) {
            sentences.add(text.substring(start, end));
        }
        return sentences;
    }
}
