package com.github.oxaoo.mp4ru.syntax.tokenize;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The word tokenizer, breaking the text to the words.
 *
 * @author Alexander Kuleshov
 * @version 1.0.0
 * @since 11.02.2017
 */
public class WordTokenizer implements Tokenizer {

    public WordTokenizer() {

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
            String pureToken = token.replaceAll("[^\\p{IsCyrillic}\\w.!?]", "");
            if (pureToken.length() > 0)
                tokens.add(pureToken);
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
