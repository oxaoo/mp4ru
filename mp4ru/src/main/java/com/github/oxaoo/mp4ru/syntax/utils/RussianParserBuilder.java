package com.github.oxaoo.mp4ru.syntax.utils;


import com.github.oxaoo.mp4ru.exceptions.InitRussianParserException;
import com.github.oxaoo.mp4ru.syntax.RussianParser;

/**
 * @author Alexander Kuleshov
 * @version 1.0.1
 * @since 10.05.2018
 */
public final class RussianParserBuilder {
    private static final String DEFAULT_CLASSIFIER_MODEL_FILE = "russian-utf8.par";
    private static final String DEFAULT_PARSER_CONFIG_FILE = "russian.mco";

    private RussianParserBuilder() {
    }


    public static RussianParser build(final String commonParserSource) throws InitRussianParserException {
        final String normalizedPath = normalizeSourcePath(commonParserSource);
        final String classifierModelPath = normalizedPath + DEFAULT_CLASSIFIER_MODEL_FILE;
        final String parserConfigPath = normalizedPath + DEFAULT_PARSER_CONFIG_FILE;
        //normalized path use as treeTaggerHome
        return build(classifierModelPath, normalizedPath, parserConfigPath);
    }

    public static RussianParser build(final String classifierModelPath,
                                      final String treeTaggerHome,
                                      final String parserConfigPath) throws InitRussianParserException {
        return new RussianParser(classifierModelPath, treeTaggerHome, parserConfigPath);
    }

    private static String normalizeSourcePath(final String path) {
        return path.endsWith("/") ? path : path + "/";
    }
}
