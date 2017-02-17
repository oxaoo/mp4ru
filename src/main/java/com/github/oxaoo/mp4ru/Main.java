package com.github.oxaoo.mp4ru;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.github.oxaoo.mp4ru.exceptions.FailedParsingException;
import com.github.oxaoo.mp4ru.syntax.RussianParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    @Parameter(names = {"-tf", "--textFile"}, description = "The path to the text file")
    public String textFilePath;

    @Parameter(names = {"-cm", "--classifierModel"},
            description = "The path to the classifier model of morphological analysis")
    public String classifierModel;

    @Parameter(names = {"-tt", "--TreeTagger"},
            description = "The path to the home directory of TreeTagger executor. It must located in '*/bin/' directory")
    public String treeTaggerHome;

    @Parameter(names = {"-pc", "--ParserConfig"}, description = "The path to the home directory of parser configuration")
    public String parserConfig;

    @Parameter(names = {"-h", "--help"}, description = "Information on use of mp4ru", help = true)
    public boolean help = false;


    public static void main(String[] args) {
        Main app = new Main();
        JCommander jcmd = new JCommander(app, args);
        if (app.help) {
            jcmd.usage();
        } else {
            app.run();
        }
    }

    private void run() {
        if (textFilePath == null || classifierModel == null || treeTaggerHome == null || parserConfig == null) {
            LOG.error("Missing required parameters.");
            return;
        }

        String dir = System.getProperty("user.dir");
        LOG.debug("Current dir: " + dir);

        try {
            String resultParseFile = new RussianParser().parsing(textFilePath, classifierModel, treeTaggerHome, parserConfig);
            LOG.info("Successful parsing! The result of parsing is presented in the {} file.", resultParseFile);
        } catch (FailedParsingException e) {
            LOG.error("Exception during parsing. Cause: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
