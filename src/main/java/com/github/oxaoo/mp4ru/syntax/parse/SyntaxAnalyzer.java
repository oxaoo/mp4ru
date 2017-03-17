package com.github.oxaoo.mp4ru.syntax.parse;

import com.github.oxaoo.mp4ru.exceptions.InitSyntaxAnalyzerException;
import com.github.oxaoo.mp4ru.exceptions.SyntaxAnalysisException;
import com.github.oxaoo.mp4ru.exceptions.WriteToFileException;
import com.github.oxaoo.mp4ru.syntax.tagging.Conll;
import org.maltparser.concurrent.ConcurrentMaltParserModel;
import org.maltparser.concurrent.ConcurrentMaltParserService;
import org.maltparser.core.exception.MaltChainedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * The syntax analyzer.
 *
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 12.02.2017
 */
//todo added SyntaxAnalyzerFactory
public class SyntaxAnalyzer {
    private final ConcurrentMaltParserModel parserModel;

    public SyntaxAnalyzer(String parserConfig) throws InitSyntaxAnalyzerException {
        try {
            URL parserModelUrl = new File(parserConfig).toURI().toURL();
            this.parserModel = ConcurrentMaltParserService.initializeParserModel(parserModelUrl);
        } catch (MalformedURLException e) {
            throw new InitSyntaxAnalyzerException("Unable to retrieve the file.", e);
        } catch (MaltChainedException e) {
            throw new InitSyntaxAnalyzerException("Unable to load the file.", e);
        }
    }

    public String analyze(List<Conll> taggingTokens, String parseFilePath)
            throws SyntaxAnalysisException, WriteToFileException {
        List<String> outputTokens = this.analyze(taggingTokens);
        this.writeParsedText(parseFilePath, outputTokens);
        return parseFilePath;
    }

    //Syntax analyze by means of MaltParser.
    public List<String> analyze(List<Conll> taggingTokens) throws SyntaxAnalysisException {
        String[] inputTokens = taggingTokens.stream().map(Conll::toRow).toArray(String[]::new);
        try {
            String[] outputTokens = parserModel.parseTokens(inputTokens);
            return Arrays.asList(outputTokens);
        } catch (MaltChainedException e) {
            throw new SyntaxAnalysisException("Failed to syntax analysis.", e);
        }
    }

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
