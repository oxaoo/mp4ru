package com.github.oxaoo.mp4ru.syntax.parse;

import com.github.oxaoo.mp4ru.exceptions.InitSyntaxAnalyzerException;
import com.github.oxaoo.mp4ru.exceptions.WriteToFileException;
import com.github.oxaoo.mp4ru.exceptions.SyntaxAnalysisException;
import com.github.oxaoo.mp4ru.syntax.tagging.Conll;
import org.maltparser.MaltParserService;
import org.maltparser.concurrent.ConcurrentMaltParserModel;
import org.maltparser.concurrent.ConcurrentMaltParserService;
import org.maltparser.core.exception.MaltChainedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
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
    private static final Logger LOG = LoggerFactory.getLogger(SyntaxAnalyzer.class);

    @Deprecated private final MaltParserService maltParserService;
    @Deprecated private final String parserConfigDirectory;
    @Deprecated private final String parseFilePath;

    final ConcurrentMaltParserModel parserModel;

    @Deprecated
    public SyntaxAnalyzer(String parserConfig, String parseFilePath) throws InitSyntaxAnalyzerException {
        try {
            this.maltParserService = new MaltParserService(SyntaxPropertyKeys.OPTION_CONTAINER);
        } catch (MaltChainedException e) {
            throw new InitSyntaxAnalyzerException("Failed to initialize the syntax analyzer.", e);
        }
        this.parserConfigDirectory = SyntaxPropertyKeys.CONFIG_WORKINGDIR_KEY + parserConfig;
        this.parseFilePath = SyntaxPropertyKeys.OUTPUT_OUTFILE_KEY + parseFilePath;

        this.parserModel = null;
    }

    public SyntaxAnalyzer(String parserConfig) throws InitSyntaxAnalyzerException {
        parseFilePath = null;
        maltParserService = null;
        parserConfigDirectory = null;

        try {
            URL parserModelUrl = new File(parserConfig).toURI().toURL();
            this.parserModel = ConcurrentMaltParserService.initializeParserModel(parserModelUrl);
        } catch (MalformedURLException e) {
            throw new InitSyntaxAnalyzerException("Unable to retrieve the file", e);
        } catch (MaltChainedException e) {
            throw new InitSyntaxAnalyzerException("Unable to load the file", e);
        }
    }

    /**
     * Syntax analyze by means of MaltParser.
     *
     * @return <tt>true</tt> if analyze is successful
     */
    @Deprecated
    public boolean analyze() throws SyntaxAnalysisException {
        final String command = this.parserConfigDirectory
                + SyntaxPropertyKeys.CONFIG_NAME_MODEL
                + SyntaxPropertyKeys.INPUT_INFILE_PATH
                + this.parseFilePath
                + SyntaxPropertyKeys.CONFIG_FLOWCHART_PARSE;
        try {
            this.maltParserService.runExperiment(command.trim());
            return true;
        } catch (MaltChainedException e) {
            throw new SyntaxAnalysisException("Failed to syntax analysis.", e);
        }
    }


    public boolean analyze(List<Conll> taggingTokens, String parseFilePath)
            throws SyntaxAnalysisException, WriteToFileException {
        String[] inputTokens = taggingTokens.stream().map(Conll::toRow).toArray(String[]::new);
        try {
            String[] outputTokens = parserModel.parseTokens(inputTokens);
            this.writeParsedText(parseFilePath, outputTokens);
        } catch (MaltChainedException e) {
            throw new SyntaxAnalysisException("Failed to syntax analysis.", e);
        }
        return true;
    }

    private void writeParsedText(String fileName, String[] strings) throws WriteToFileException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String string : strings) {
                bw.write(string + "\n");
            }
        } catch (IOException e) {
            throw new WriteToFileException("Failed to write the parsed text into a file.", e);
        }
    }
}
