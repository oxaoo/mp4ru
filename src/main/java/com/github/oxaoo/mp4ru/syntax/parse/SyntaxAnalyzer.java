package com.github.oxaoo.mp4ru.syntax.parse;

import com.github.oxaoo.mp4ru.exceptions.InitSyntaxAnalyzerException;
import com.github.oxaoo.mp4ru.exceptions.SyntaxAnalysisException;
import com.github.oxaoo.mp4ru.exceptions.WriteToFileException;
import com.github.oxaoo.mp4ru.syntax.tagging.Conll;
import org.maltparser.concurrent.ConcurrentMaltParserModel;
import org.maltparser.concurrent.ConcurrentMaltParserService;
import org.maltparser.core.exception.MaltChainedException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The syntax analyzer.
 *
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 12.02.2017
 */
public class SyntaxAnalyzer {
    private static volatile SyntaxAnalyzer syntaxAnalyzer;
    private final ConcurrentMaltParserModel parserModel;

    private SyntaxAnalyzer(String parserConfig) throws InitSyntaxAnalyzerException {
        try {
            URL parserModelUrl = new File(parserConfig).toURI().toURL();
            this.parserModel = ConcurrentMaltParserService.initializeParserModel(parserModelUrl);
        } catch (MalformedURLException e) {
            throw new InitSyntaxAnalyzerException("Unable to retrieve the file.", e);
        } catch (MaltChainedException e) {
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

    public String analyze(List<Conll> taggingTokens, String parseFilePath)
            throws SyntaxAnalysisException, WriteToFileException {
        List<String> outputTokens = this.analyze(taggingTokens);
        this.writeParsedText(parseFilePath, outputTokens);
        return parseFilePath;
    }

    public List<String> analyze(List<Conll> taggingTokens) throws SyntaxAnalysisException {
        List<String> outputTokens = new ArrayList<>();
        List<String> sentenceInputTokens = new ArrayList<>();
        Conll lastToken = taggingTokens.get(taggingTokens.size() - 1);
        for (Conll taggingToken : taggingTokens) {
            //if it is token of new sentence
            if (taggingToken.getId() == 1 && !sentenceInputTokens.isEmpty()
                    || taggingToken.equals(lastToken)) {
                try {
                    String[] sentenceOutputTokens = this.parserModel
                            .parseTokens(sentenceInputTokens.stream()
                                    .toArray(String[]::new));
                    outputTokens.addAll(Arrays.asList(sentenceOutputTokens));
                } catch (MaltChainedException e) {
                    throw new SyntaxAnalysisException("Failed to syntax analysis.", e);
                }
                sentenceInputTokens = new ArrayList<>();
            }
            sentenceInputTokens.add(taggingToken.toRow());
        }
        return outputTokens;
    }

    @Deprecated
    private void writeParsedText(String fileName, List<String> strings) throws WriteToFileException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String string : strings) {
                bw.write(string + "\n");
            }
        } catch (IOException e) {
            throw new WriteToFileException("Failed to write the parsed text into a file.", e);
        }
    }
}
