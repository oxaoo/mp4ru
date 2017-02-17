package com.github.oxaoo.mp4ru.syntax.parse;


import static com.github.oxaoo.mp4ru.syntax.GlobalPropertyKeys.CONLL_TEXT_FILE;

/**
 * Property keys for syntax analyzer.
 *
 * @author Alexander Kuleshov
 * @version 1.0
 * @link http://www.maltparser.org/userguide.html#config
 * @since 12.02.2017
 */
final class SyntaxPropertyKeys {
    static final String CONFIG_WORKINGDIR_KEY = " -w ";
    static final String CONFIG_NAME_MODEL = " -c russian";
    static final String INPUT_INFILE_PATH = " -i " + CONLL_TEXT_FILE;
    static final String OUTPUT_OUTFILE_KEY = " -o ";
    static final String CONFIG_FLOWCHART_PARSE = " -m parse";

    static final int OPTION_CONTAINER = 0;
}
