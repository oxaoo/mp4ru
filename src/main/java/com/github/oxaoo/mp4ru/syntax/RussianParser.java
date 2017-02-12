package com.github.oxaoo.mp4ru.syntax;

import com.github.oxaoo.mp4ru.exceptions.*;
import com.github.oxaoo.mp4ru.syntax.parse.SyntaxAnalyzer;
import com.github.oxaoo.mp4ru.syntax.tagging.Conll;
import com.github.oxaoo.mp4ru.syntax.tagging.PosTagger;
import com.github.oxaoo.mp4ru.syntax.tokenize.SimpleTokenizer;
import com.github.oxaoo.mp4ru.syntax.tokenize.Tokenizer;

import java.util.List;

/**
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 12.02.2017
 */
public class RussianParser {

    public RussianParser() {
    }

    public void parsing() throws FailedParsingException {
        try {
            this.start();
        } catch (ReadInputTextException
                | IncorrectTokenException
                | FailedStoreTokensException
                | ClassifierModelNotFoundException
                | FailedInitSyntaxAnalyzerException
                | FailedSyntaxAnalysisException e) {
            throw new FailedParsingException("Failed to parse the Russian text.", e);
        }
    }

    private void start() throws ReadInputTextException,
            IncorrectTokenException,
            ClassifierModelNotFoundException,
            FailedStoreTokensException,
            FailedInitSyntaxAnalyzerException,
            FailedSyntaxAnalysisException {

        //tokenization.
        Tokenizer tokenizer = new SimpleTokenizer();
        List<String> tokens = tokenizer.tokenization();

        //morphological analyze.
        PosTagger tagger = new PosTagger();
        List<Conll> taggingTokens = tagger.tagging(tokens);
        tagger.writeTokens(taggingTokens);

        //syntactic analyze.
        SyntaxAnalyzer analyzer = new SyntaxAnalyzer();
        analyzer.analyze();
    }
}
