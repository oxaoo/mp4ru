package com.github.oxaoo.mp4ru;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.github.oxaoo.mp4ru.exceptions.FailedParsingException;
import com.github.oxaoo.mp4ru.exceptions.InitRussianParserException;
import com.github.oxaoo.mp4ru.syntax.RussianParser;
import com.github.oxaoo.mp4ru.syntax.utils.RussianParserBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
    Program arguments to run example:
    -cm res/russian-utf8.par
    -tf res/text.txt
    -tt res/
    -pc res/russian.mco
 */
public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    @Parameter(
            names = {"-tf", "--textFile"},
            description = "The path to the text file [*.txt]")
    public String textFilePath;

    @Parameter(
            names = {"-cm", "--classifierModel"},
            description = "The path to the classifier model of morphological analysis [*.par]")
    public String classifierModel;

    @Parameter(
            names = {"-tt", "--treeTagger"},
            description = "The path to the home directory of TreeTagger executor. " +
                    "It must located in '*/bin/' directory")
    public String treeTaggerHome;

    @Parameter(
            names = {"-pc", "--parserConfig"},
            description = "The path to the home directory of parser configuration [*.mco]")
    public String parserConfig;

    @Parameter(
            names = {"-cs", "--commonSource"},
            description = "Represents the common data source. "
                    + "It is possible to use instead of 'classifierModel', 'treeTagger' and 'parserConfig' "
                    + "if in this place there are all necessary resources")
    public String commonSource;

    @Parameter(
            names = {"-h", "--help"},
            description = "Information on use of the mp4ru",
            help = true)
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
        if (textFilePath == null
                || (commonSource == null
                && (classifierModel == null || treeTaggerHome == null || parserConfig == null))) {
            LOG.error("Missing required parameters.");
            return;
        }

        final RussianParser parser;
        try {
            if (this.commonSource != null) {
                parser = RussianParserBuilder.build(this.commonSource);
            } else {
                parser = RussianParserBuilder.build(this.classifierModel, this.treeTaggerHome, this.parserConfig);
            }
        } catch (final InitRussianParserException e) {
            LOG.error("Failed to initialize the Russian parser. Cause: {}", e.getMessage());
            e.printStackTrace();
            return;
        }

        try {
            String resultParseFile = parser.parseFromFile(textFilePath);
            LOG.info("Successful parse from file! The result is presented in the '{}'.", resultParseFile);
        } catch (FailedParsingException e) {
            LOG.error("Exception during parseFromFile. Cause: {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
