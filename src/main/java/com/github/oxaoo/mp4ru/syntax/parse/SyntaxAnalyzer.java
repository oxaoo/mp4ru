package com.github.oxaoo.mp4ru.syntax.parse;

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
    private static final Logger LOG = LoggerFactory.getLogger(SyntaxAnalyzer.class);

    private final MaltParserService maltParserService;

    public SyntaxAnalyzer() throws MaltChainedException {
        this.maltParserService = new MaltParserService(SyntaxPropertyKeys.OPTION_CONTAINER);
    }

    /**
     * Syntax analyze by means of MaltParser.
     *
     * @return <tt>true</tt> if analyze is successful
     */
    public boolean analyze() {
        final String command = SyntaxPropertyKeys.CONFIG_WORKINGDIR_PATH
                + SyntaxPropertyKeys.CONFIG_NAME_MODEL
                + SyntaxPropertyKeys.INPUT_INFILE_PATH
                + SyntaxPropertyKeys.OUTPUT_OUTFILE_PATH
                + SyntaxPropertyKeys.CONFIG_FLOWCHART_PARSE;

        try {
            this.maltParserService.runExperiment(command.trim());
        } catch (MaltChainedException e) {
            LOG.error("Failed to syntax analyze: [{}]", e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
