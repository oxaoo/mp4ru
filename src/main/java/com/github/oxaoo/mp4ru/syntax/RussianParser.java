package com.github.oxaoo.mp4ru.syntax;

import com.github.oxaoo.mp4ru.exceptions.ReadInputTextException;
import com.github.oxaoo.mp4ru.syntax.parse.SyntaxAnalyzer;
import com.github.oxaoo.mp4ru.syntax.tagging.Conll;
import com.github.oxaoo.mp4ru.syntax.tagging.PosTagger;
import com.github.oxaoo.mp4ru.syntax.tokenize.SimpleTokenizer;
import com.github.oxaoo.mp4ru.syntax.tokenize.Tokenizer;
import org.annolab.tt4j.TreeTaggerException;
import org.maltparser.core.exception.MaltChainedException;

import java.io.IOException;
import java.util.List;

/**
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 12.02.2017
 */
public class RussianParser {

    public RussianParser() {
    }

    public void run() throws ReadInputTextException, IOException, TreeTaggerException, MaltChainedException {
        //tokenization.
        Tokenizer tokenizer = new SimpleTokenizer();
        List<String> tokens = tokenizer.tokenization();

        //morphological analyze.
        PosTagger tagger = new PosTagger();
        List<Conll> taggingTokens = tagger.tagging(tokens);
        tagger.writeTokens(taggingTokens);

        //syntactic analyze.
        SyntaxAnalyzer sa = new SyntaxAnalyzer();
        sa.analyze();
    }
}
