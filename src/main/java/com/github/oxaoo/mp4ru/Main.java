package com.github.oxaoo.mp4ru;

import com.github.oxaoo.mp4ru.exceptions.FailedParsingException;
import com.github.oxaoo.mp4ru.syntax.RussianParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            new RussianParser().parsing();
            LOG.warn("Successful parsing!");
        } catch (FailedParsingException e) {
            LOG.error("Exception during parsing. Cause: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
