package com.github.oxaoo.mp4ru.syntax;

import com.github.oxaoo.mp4ru.exceptions.*;
import com.github.oxaoo.mp4ru.syntax.parse.SyntaxAnalyzer;
import com.github.oxaoo.mp4ru.syntax.tagging.Conll;
import com.github.oxaoo.mp4ru.syntax.tagging.PosTagger;
import com.github.oxaoo.mp4ru.syntax.tokenize.SimpleTokenizer;
import com.github.oxaoo.mp4ru.syntax.tokenize.Tokenizer;
import com.github.oxaoo.mp4ru.ulils.SyntaxUtils;
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

    private final SyntaxUtils utils;

    public RussianParser() {
        utils = new SyntaxUtils();
    }

    public String parsing(String textFilePath, String classifierModel, String treeTaggerHome, String parserConfig)
            throws FailedParsingException {
        try {
            return this.execute(textFilePath, classifierModel, treeTaggerHome, parserConfig);
        } catch (ReadInputTextException
                | IncorrectTokenException
                | FailedStoreTokensException
                | ClassifierModelNotFoundException
                | FailedInitSyntaxAnalyzerException
                | FailedSyntaxAnalysisException e) {
            throw new FailedParsingException("Failed to parse the Russian text.", e);
        } finally {
            this.utils.removeTemporaryFiles();
        }
    }

    private String execute(String textFilePath, String classifierModel, String treeTaggerHome, String parserConfig)
            throws ReadInputTextException,
            IncorrectTokenException,
            ClassifierModelNotFoundException,
            FailedStoreTokensException,
            FailedInitSyntaxAnalyzerException,
            FailedSyntaxAnalysisException {

        //tokenization.
        LOG.info("Tokenization...");
        Tokenizer tokenizer = new SimpleTokenizer(textFilePath);
        List<String> tokens = tokenizer.tokenization();
        LOG.debug("Tokens: {}", tokens);

        //morphological analyze.
        LOG.info("Tagging...");
        PosTagger tagger = new PosTagger(classifierModel, treeTaggerHome);
        List<Conll> taggingTokens = tagger.tagging(tokens);
        tagger.writeTokens(taggingTokens);
        LOG.debug("Tagging tokens: {}", taggingTokens);

        //prepare...
        String parseFilePath = this.utils.makeParseFilePath(textFilePath);

        //syntactic analyze.
        LOG.info("Parsing...");
        SyntaxAnalyzer analyzer = new SyntaxAnalyzer(parserConfig, parseFilePath);
        analyzer.analyze();

        return parseFilePath;
    }
}
