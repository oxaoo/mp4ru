package com.github.oxaoo.mp4ru.syntax.parse;

import com.github.oxaoo.mp4ru.exceptions.FailedInitSyntaxAnalyzerException;
import com.github.oxaoo.mp4ru.exceptions.FailedSyntaxAnalysisException;
import com.github.oxaoo.mp4ru.syntax.RussianParser;
import org.maltparser.MaltParserService;
import org.maltparser.concurrent.ConcurrentMaltParserModel;
import org.maltparser.concurrent.ConcurrentMaltParserService;
import org.maltparser.concurrent.ConcurrentUtils;
import org.maltparser.core.exception.MaltChainedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

/**
 * The syntax analyzer.
 *
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 12.02.2017
 */
public class SyntaxAnalyzer {
    private static final Logger LOG = LoggerFactory.getLogger(SyntaxAnalyzer.class);

    private final MaltParserService maltParserService;
    private final String parserConfigDirectory;
    private final String parseFilePath;

    public SyntaxAnalyzer(String parserConfig, String parseFilePath) throws FailedInitSyntaxAnalyzerException {
        try {
            this.maltParserService = new MaltParserService(SyntaxPropertyKeys.OPTION_CONTAINER);
        } catch (MaltChainedException e) {
            throw new FailedInitSyntaxAnalyzerException("Failed to initialize the syntax analyzer.", e);
        }
        this.parserConfigDirectory = SyntaxPropertyKeys.CONFIG_WORKINGDIR_KEY + parserConfig;
        this.parseFilePath = SyntaxPropertyKeys.OUTPUT_OUTFILE_KEY + parseFilePath;
    }

    /**
     * Syntax analyze by means of MaltParser.
     *
     * @return <tt>true</tt> if analyze is successful
     */
    public boolean analyze() throws FailedSyntaxAnalysisException {
        final String command = this.parserConfigDirectory
                + SyntaxPropertyKeys.CONFIG_NAME_MODEL
                + SyntaxPropertyKeys.INPUT_INFILE_PATH
                + this.parseFilePath
                + SyntaxPropertyKeys.CONFIG_FLOWCHART_PARSE;
        try {
            this.maltParserService.runExperiment(command.trim());
            return true;
        } catch (MaltChainedException e) {
            throw new FailedSyntaxAnalysisException("Failed to syntax analysis.", e);
        }
    }


    public boolean runtimeAnalyze() {
        URL maltModelUrl = null;
        ConcurrentMaltParserModel model;
        try {
            maltModelUrl = new File("res/russian.mco").toURI().toURL();
            model = ConcurrentMaltParserService.initializeParserModel(maltModelUrl);
        } catch (MalformedURLException e) {
            LOG.error("Error while load maltparser model");
            return false;
        } catch (MaltChainedException e) {
            LOG.error("Error while init maltparser model");
            return false;
        }

        BufferedReader bf;
        try {
            bf = new BufferedReader(new InputStreamReader(new File("res/text.conll")
                            .toURI().toURL().openStream(), "UTF-8"));
        } catch (IOException e) {
            LOG.error("Error while load prepare tokens for maltparser");
            return false;
        }

        String[] inputTokens = null;
        do {
            try {
                inputTokens = ConcurrentUtils.readSentence(bf);
                String[] outputTokens = model.parseTokens(inputTokens);
                for (String t : outputTokens) {
                    LOG.info("Tokens: {}", t);
                }
            } catch (IOException e) {
                LOG.error("Error while read tokens");
                return false;
            } catch (MaltChainedException e) {
                LOG.error("Error while parse tokens");
                return false;
            }
        } while (inputTokens.length > 0);

        return true;
    }
}
