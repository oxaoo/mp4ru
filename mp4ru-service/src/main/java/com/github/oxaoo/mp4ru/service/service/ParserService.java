package com.github.oxaoo.mp4ru.service.service;

import com.github.oxaoo.mp4ru.exceptions.FailedParsingException;
import com.github.oxaoo.mp4ru.service.configuration.ApplicationConstants;
import com.github.oxaoo.mp4ru.syntax.RussianParser;
import com.github.oxaoo.mp4ru.syntax.tagging.Conll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParserService {

    private RussianParser parser;

    public List<?> parse(final String text, final String dataFormat) throws FailedParsingException {
        final String normalizedDataFormat = dataFormat.replaceAll("-", "").toUpperCase();
        switch (normalizedDataFormat) {
            case ApplicationConstants.MALT_PARSER_CONLL_X_FORMAT:
                return this.parser.parse(text, Conll.class);
            case ApplicationConstants.MALT_PARSER_MALT_TAB_FORMAT:
                return this.parser.parse(text, String.class);
            default:
                throw new FailedParsingException(String.format("Unsupported data format '%s", dataFormat));
        }
    }

    @Autowired
    public void setParser(final RussianParser parser) {
        this.parser = parser;
    }
}
