package com.github.oxaoo.mp4ru.syntax.parse;

import com.github.oxaoo.mp4ru.exceptions.FailedInitSyntaxAnalyzerException;
import com.github.oxaoo.mp4ru.exceptions.FailedSyntaxAnalysisException;
import org.maltparser.MaltParserService;
import org.maltparser.core.exception.MaltChainedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The syntax analyzer.
 *
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 12.02.2017
 */
public class SyntaxAnalyzer {
    private final MaltParserService maltParserService;

    public SyntaxAnalyzer() throws FailedInitSyntaxAnalyzerException {
        try {
            this.maltParserService = new MaltParserService(SyntaxPropertyKeys.OPTION_CONTAINER);
        } catch (MaltChainedException e) {
            throw new FailedInitSyntaxAnalyzerException("Failed to initialize the syntax analyzer.", e);
        }
    }

    /**
     * Syntax analyze by means of MaltParser.
     *
     * @return <tt>true</tt> if analyze is successful
     */
    public boolean analyze() throws FailedSyntaxAnalysisException {
        final String command = SyntaxPropertyKeys.CONFIG_WORKINGDIR_PATH
                + SyntaxPropertyKeys.CONFIG_NAME_MODEL
                + SyntaxPropertyKeys.INPUT_INFILE_PATH
                + SyntaxPropertyKeys.OUTPUT_OUTFILE_PATH
                + SyntaxPropertyKeys.CONFIG_FLOWCHART_PARSE;
        try {
            this.maltParserService.runExperiment(command.trim());
            return true;
        } catch (MaltChainedException e) {
            throw new FailedSyntaxAnalysisException("Failed to syntax analysis.", e);
        }
    }
}
