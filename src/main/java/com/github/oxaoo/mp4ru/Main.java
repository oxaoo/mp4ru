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

    @Parameter(names = {"-cm", "--classifierModel"}, description = "The path to the classifier model")
    public String classifierModel;

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
        if (textFilePath == null || classifierModel == null) {
            LOG.error("Missing required parameters.");
            return;
        }

        try {
            new RussianParser().parsing(textFilePath, classifierModel);
            LOG.info("Successful parsing!");
        } catch (FailedParsingException e) {
            LOG.error("Exception during parsing. Cause: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
