package com.github.oxaoo.mp4ru.syntax.tokenize;

import com.github.oxaoo.mp4ru.exceptions.ReadInputTextException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The simple tokenizer, breaking the text to the words.
 *
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 11.02.2017
 */
public class SimpleTokenizer implements Tokenizer {
    //    private static final Logger LOG = LoggerFactory.getLogger(SimpleTokenizer.class);
    private static final String DEFAULT_TEXT_FILE = "src/main/resources/input/text.txt";

    private final String textFilePath;

    public SimpleTokenizer() {
        this.textFilePath = DEFAULT_TEXT_FILE;
    }

    public SimpleTokenizer(String textFilePath) {
        this.textFilePath = textFilePath;
    }

    /**
     * Read text from file.
     *
     * @return the text
     */
    private String readText() throws ReadInputTextException {
        try (BufferedReader br = new BufferedReader(new FileReader(this.textFilePath))) {
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            return sb.toString();
        } catch (IOException e) {
            throw new ReadInputTextException("Failed to read the text file \'" + this.textFilePath + "\'.", e);
        }
    }

    /**
     * To split the text on words.
     *
     * @return list of words
     */
    @Override
    public List<String> tokenization() throws ReadInputTextException {
        String text = this.readText();

        List<String> tokens = new ArrayList<>();
        Locale ruLocale = new Locale.Builder().setLanguage("ru").setScript("Cyrl").build();
        BreakIterator bi = BreakIterator.getWordInstance(ruLocale);

        text = this.trimming(text);
        bi.setText(text);
        int cur = bi.first();
        int prev = 0;
        while (cur != BreakIterator.DONE) {
            String token = text.substring(prev, cur);
            if (token.replaceAll("[—«»\"`‚„‘’“”%;:\\s]+", "").length() > 0)
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
