package com.github.oxaoo.mp4ru.syntax.parse;

import com.github.oxaoo.mp4ru.exceptions.InitSyntaxAnalyzerException;
import com.github.oxaoo.mp4ru.exceptions.SyntaxAnalysisException;
import com.github.oxaoo.mp4ru.syntax.tagging.Conll;
import org.maltparser.concurrent.ConcurrentMaltParserModel;
import org.maltparser.concurrent.ConcurrentMaltParserService;
import org.maltparser.core.exception.MaltChainedException;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The syntax analyzer.
 *
 * @author Alexander Kuleshov
 * @version 0.1.0
 * @since 12.02.2017
 */
public class SyntaxAnalyzer {
    private static volatile SyntaxAnalyzer syntaxAnalyzer;
    private final ConcurrentMaltParserModel parserModel;

    private SyntaxAnalyzer(String parserConfig) throws InitSyntaxAnalyzerException {
        try {
            this.parserModel = ConcurrentMaltParserService.initializeParserModel(new File(parserConfig));
        } catch (MaltChainedException | MalformedURLException e) {
            throw new InitSyntaxAnalyzerException("Unable to load the file.", e);
        }
    }

    public static SyntaxAnalyzer getInstance(String parserConfig) throws InitSyntaxAnalyzerException {
        if (syntaxAnalyzer == null) {
            synchronized (SyntaxAnalyzer.class) {
                if (syntaxAnalyzer == null) {
                    syntaxAnalyzer = new SyntaxAnalyzer(parserConfig);
                }
            }
        }
        return syntaxAnalyzer;
    }

    public List<String> analyze(List<Conll> taggingTokens) throws SyntaxAnalysisException {
        List<String> outputTokens = new ArrayList<>();
        List<String> sentenceInputTokens = new ArrayList<>();
        Conll lastToken = taggingTokens.get(taggingTokens.size() - 1);
        int prevTokenId = 0;
        for (Conll taggingToken : taggingTokens) {
            boolean isLastToken = taggingToken.equals(lastToken);
            //if it is token of new sentence
            if (taggingToken.getId() <= prevTokenId || isLastToken) {
                if (isLastToken) sentenceInputTokens.add(taggingToken.toRow());
                try {
                    String[] sentenceOutputTokens = this.parserModel
                            .parseTokens(sentenceInputTokens.stream().toArray(String[]::new));
                    outputTokens.addAll(Arrays.asList(sentenceOutputTokens));
                } catch (MaltChainedException e) {
                    throw new SyntaxAnalysisException("Failed to syntax analysis.", e);
                }
                if (isLastToken) continue;
                sentenceInputTokens = new ArrayList<>();
            }
            sentenceInputTokens.add(taggingToken.toRow());
            prevTokenId = taggingToken.getId();
        }
        return outputTokens;
    }
}
