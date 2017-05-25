package com.github.oxaoo.mp4ru.syntax.tokenize;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The simple tokenizer, breaking the text to the words.
 *
 * @author Alexander Kuleshov
 * @version 0.1.0
 * @since 11.02.2017
 */
public class SimpleTokenizer implements Tokenizer {

    public SimpleTokenizer() {

    }

    /**
     * To split the text on words.
     *
     * @return list of words
     */
    @Override
    public List<String> tokenization(String text) {
        List<String> tokens = new ArrayList<>();
        Locale ruLocale = new Locale.Builder().setLanguage("ru").setScript("Cyrl").build();
        BreakIterator bi = BreakIterator.getWordInstance(ruLocale);

        text = this.trimming(text);
        bi.setText(text);
        int cur = bi.first();
        int prev = 0;
        while (cur != BreakIterator.DONE) {
            String token = text.substring(prev, cur);
            if (token.replaceAll("[—«»\"`,‚„‘’“”%;:\\p{Z}\\uFEFF-\\uFFFF\\uFEFF]+", "").length() > 0)
                tokens.add(token);
            prev = cur;
            cur = bi.next();
        }
        return tokens;
    }

    /**
     * Deleting characters unrecognizable the BreakIterator.
     *
     * @param text the input text
     * @return the trimmed text
     */
    private String trimming(String text) {
        return text.replace("...", "");
    }
}
