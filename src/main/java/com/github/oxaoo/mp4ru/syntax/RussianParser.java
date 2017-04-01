package com.github.oxaoo.mp4ru.syntax;

import com.github.oxaoo.mp4ru.exceptions.*;
import com.github.oxaoo.mp4ru.syntax.parse.SyntaxAnalyzer;
import com.github.oxaoo.mp4ru.syntax.tagging.Conll;
import com.github.oxaoo.mp4ru.syntax.tagging.PosTagger;
import com.github.oxaoo.mp4ru.syntax.tokenize.FragmentationType;
import com.github.oxaoo.mp4ru.syntax.tokenize.SimpleTokenizer;
import com.github.oxaoo.mp4ru.syntax.tokenize.Tokenizer;
import com.github.oxaoo.mp4ru.syntax.utils.ParserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 12.02.2017
 */
public class RussianParser {
    private static final Logger LOG = LoggerFactory.getLogger(RussianParser.class);

    private final String classifierModelPath;
    private final String treeTaggerHome;
    private final String parserConfigPath;

    /**
     * Instantiates a new Russian parser.
     *
     * @param classifierModelPath the classifier model path (*.par)
     * @param treeTaggerHome      the tree tagger home (*\bin\)
     * @param parserConfigPath    the parser config path (*.mco)
     */
    public RussianParser(String classifierModelPath, String treeTaggerHome, String parserConfigPath) {
        this.classifierModelPath = classifierModelPath;
        this.treeTaggerHome = treeTaggerHome;
        this.parserConfigPath = parserConfigPath;
    }

    /**
     * Parse Russian text from file.
     *
     * @param textFilePath the text file path (*.txt)
     * @return the path to parseFromFile text
     * @throws FailedParsingException the failed parseFromFile exception
     */
    public String parseFromFile(String textFilePath) throws FailedParsingException {
        try {
            //prepare...
            String text = ParserUtils.readText(textFilePath);
            String parseFilePath = ParserUtils.makeParseFilePath(textFilePath);

            List<String> parsedTokens = this.parse(text);

            ParserUtils.writeParsedText(parseFilePath, parsedTokens);
            return parseFilePath;
        } catch (ReadInputTextException | WriteToFileException e) {
            throw new FailedParsingException("Failed to parse the Russian text.", e);
        }
    }

    /**
     * Parse Russian text.
     *
     * @param text the text
     * @return the list of parsed tokens
     * @throws FailedParsingException the failed parsing exception
     */
    public List<String> parse(String text) throws FailedParsingException {
        return this.parse(text, String.class);
    }

    /**
     * Parse Russian text.
     *
     * @param <T>  the type parameter
     * @param text the text
     * @param t    the t
     * @return the list of parsed tokens
     * @throws FailedParsingException the failed parsing exception
     */
    public <T> List<T> parse(String text, Class<T> t) throws FailedParsingException {
        return this.parse(text, t, FragmentationType.FRAGMENTATION);
    }

    public <T> List<T> parseSentence(String text, Class<T> t) throws FailedParsingException {
        return this.parse(text, t, FragmentationType.NO_FRAGMENTATION);
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> parse(String text, Class<T> t, FragmentationType fragmentationType)
            throws FailedParsingException {
        List<String> parsedTokens = this.parse(text, fragmentationType);
        if (t == String.class) return (List<T>) parsedTokens;
        else if (t == Conll.class) {
            return (List<T>) parsedTokens.stream().map(Conll::safeMap).collect(Collectors.toList());
        } else {
            throw new IllegalStateException("An incorrect type of parsing result is specified: " + t.getSimpleName());
        }
    }

    private List<String> parse(String text, FragmentationType fragmentationType) throws FailedParsingException {
        try {
            List<String> tokens = this.tokenization(text);
            List<Conll> taggedTokens = this.tagging(tokens, fragmentationType);
            return this.analyze(taggedTokens);
        } catch (IncorrectTokenException
                | ClassifierModelNotFoundException
                | InitPosTaggerException
                | InitSyntaxAnalyzerException
                | SyntaxAnalysisException e) {
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
     * @param tokens            the tokens
     * @param fragmentationType
     * @return the list of tagging tokens
     */
    private List<Conll> tagging(List<String> tokens, FragmentationType fragmentationType)
            throws IncorrectTokenException, ClassifierModelNotFoundException, InitPosTaggerException {
        LOG.info("Tagging...");
        PosTagger tagger = new PosTagger(this.classifierModelPath, this.treeTaggerHome);
        List<Conll> taggingTokens = tagger.tagging(tokens, fragmentationType);
        LOG.debug("Tagged tokens: {}", taggingTokens);
        return taggingTokens;
    }

    /**
     * Syntactic analyze - parsing text.
     *
     * @param taggedTokens the tagged tokens
     * @return the list of parsed tokens
     * @throws InitSyntaxAnalyzerException the init syntax analyzer exception
     * @throws SyntaxAnalysisException     the syntax analysis exception
     */
    private List<String> analyze(List<Conll> taggedTokens)
            throws InitSyntaxAnalyzerException, SyntaxAnalysisException {
        LOG.info("Parsing...");
        SyntaxAnalyzer analyzer = SyntaxAnalyzer.getInstance(this.parserConfigPath);
        return analyzer.analyze(taggedTokens);
    }
}
