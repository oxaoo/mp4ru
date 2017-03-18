package com.github.oxaoo.mp4ru.syntax;

import com.github.oxaoo.mp4ru.exceptions.*;
import com.github.oxaoo.mp4ru.syntax.parse.SyntaxAnalyzer;
import com.github.oxaoo.mp4ru.syntax.tagging.Conll;
import com.github.oxaoo.mp4ru.syntax.tagging.PosTagger;
import com.github.oxaoo.mp4ru.syntax.tokenize.SimpleTokenizer;
import com.github.oxaoo.mp4ru.syntax.tokenize.Tokenizer;
import com.github.oxaoo.mp4ru.syntax.utils.ParserUtils;
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

    /**
     * Parse Russian text.
     *
     * @param textFilePath    the text file path (*.txt)
     * @param classifierModel the classifier model (*.par)
     * @param treeTaggerHome  the tree tagger home (*\bin\)
     * @param parserConfig    the parser config (*.mco)
     * @return the path to parseFromFile text
     * @throws FailedParsingException the failed parseFromFile exception
     */
    public String parseFromFile(String textFilePath,
                                String classifierModel,
                                String treeTaggerHome,
                                String parserConfig) throws FailedParsingException {
        try {
            //prepare...
            String text = ParserUtils.readText(textFilePath);
            String parseFilePath = ParserUtils.makeParseFilePath(textFilePath);

            List<String> tokens = this.tokenization(text);
            List<Conll> taggedTokens = this.tagging(tokens, classifierModel, treeTaggerHome);
            List<String> parsedTokens = this.analyze(taggedTokens, parserConfig);

            ParserUtils.writeParsedText(parseFilePath, parsedTokens);
            return parseFilePath;
        } catch (ReadInputTextException
                | IncorrectTokenException
                | ClassifierModelNotFoundException
                | InitSyntaxAnalyzerException
                | SyntaxAnalysisException
                | WriteToFileException e) {
            throw new FailedParsingException("Failed to parse the Russian text.", e);
        }
    }


    /**
     * Tokenization text.
     *
     * @param text the text
     * @return the list of tokens
     */
    private List<String> tokenization(String text) {
        LOG.info("Tokenization...");
        Tokenizer tokenizer = new SimpleTokenizer();
        List<String> tokens = tokenizer.tokenization(text);
        LOG.debug("Tokens: {}", tokens);
        return tokens;
    }

    /**
     * Morphological analyze.
     *
     * @param tokens              the tokens
     * @param classifierModelPath the classifier model
     * @param treeTaggerHome      the tree tagger home
     * @return the list of tagging tokens
     */
    private List<Conll> tagging(List<String> tokens, String classifierModelPath, String treeTaggerHome)
            throws IncorrectTokenException, ClassifierModelNotFoundException {
        LOG.info("Tagging...");
        PosTagger tagger = new PosTagger(classifierModelPath, treeTaggerHome);
        List<Conll> taggingTokens = tagger.tagging(tokens);
        LOG.debug("Tagged tokens: {}", taggingTokens);
        return taggingTokens;
    }

    /**
     * Syntactic analyze - parsing text.
     *
     * @param taggedTokens     the tagged tokens
     * @param parserConfigPath the parser config path
     * @return the list of parsed tokens
     * @throws InitSyntaxAnalyzerException the init syntax analyzer exception
     * @throws SyntaxAnalysisException     the syntax analysis exception
     */
    private List<String> analyze(List<Conll> taggedTokens, String parserConfigPath)
            throws InitSyntaxAnalyzerException, SyntaxAnalysisException {
        //syntactic analyze.
        LOG.info("Parsing...");
        SyntaxAnalyzer analyzer = SyntaxAnalyzer.getInstance(parserConfigPath);
        return analyzer.analyze(taggedTokens);
    }
}
