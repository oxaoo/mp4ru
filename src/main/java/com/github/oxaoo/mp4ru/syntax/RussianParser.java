package com.github.oxaoo.mp4ru.syntax;

import com.github.oxaoo.mp4ru.exceptions.*;
import com.github.oxaoo.mp4ru.syntax.parse.SyntaxAnalyzer;
import com.github.oxaoo.mp4ru.syntax.tagging.Conll;
import com.github.oxaoo.mp4ru.syntax.tagging.PosTagger;
import com.github.oxaoo.mp4ru.syntax.tokenize.SimpleTokenizer;
import com.github.oxaoo.mp4ru.syntax.tokenize.Tokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 12.02.2017
 */
public class RussianParser {
    private static final Logger LOG = LoggerFactory.getLogger(RussianParser.class);

    public RussianParser() {
    }

    public void parsing(String textFilePath, String classifierModel) throws FailedParsingException {
        try {
            this.execute(textFilePath, classifierModel);
        } catch (ReadInputTextException
                | IncorrectTokenException
                | FailedStoreTokensException
                | ClassifierModelNotFoundException
                | FailedInitSyntaxAnalyzerException
                | FailedSyntaxAnalysisException e) {
            throw new FailedParsingException("Failed to parse the Russian text.", e);
        }
    }

    private void execute(String textFilePath, String classifierModel) throws ReadInputTextException,
            IncorrectTokenException,
            ClassifierModelNotFoundException,
            FailedStoreTokensException,
            FailedInitSyntaxAnalyzerException,
            FailedSyntaxAnalysisException {

        //tokenization.
        Tokenizer tokenizer = new SimpleTokenizer(textFilePath);
        List<String> tokens = tokenizer.tokenization();
        LOG.info("Tokens: {}", tokens);

        //morphological analyze.
        PosTagger tagger = new PosTagger(classifierModel);
        List<Conll> taggingTokens = tagger.tagging(tokens);
        tagger.writeTokens(taggingTokens);
        LOG.info("Tagging tokens: {}", taggingTokens);

        //syntactic analyze.
        SyntaxAnalyzer analyzer = new SyntaxAnalyzer();
        analyzer.analyze();
    }
}
