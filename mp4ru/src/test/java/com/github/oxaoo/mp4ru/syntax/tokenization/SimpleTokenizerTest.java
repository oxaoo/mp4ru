package com.github.oxaoo.mp4ru.syntax.tokenization;

import com.github.oxaoo.mp4ru.syntax.tokenize.WordTokenizer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Alexander Kuleshov
 * @version 1.0.0
 * @since 31.03.2017
 */
public class SimpleTokenizerTest {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleTokenizerTest.class);

    @Test
    public void tokenizerTest() {
        WordTokenizer tokenizer = new WordTokenizer();
        String str = "Прах женщины был развеян над морем с причала в порту Саутгемптона, откуда \"Титаник\" в 1912 году отправился в свое последнее плавание.  ";
        List<String> tokens = tokenizer.tokenization(str);
        LOG.info("Tokens: {}", tokens.toString());
    }
}
