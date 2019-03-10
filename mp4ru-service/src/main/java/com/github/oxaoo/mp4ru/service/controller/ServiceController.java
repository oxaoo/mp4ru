package com.github.oxaoo.mp4ru.service.controller;

import com.github.oxaoo.mp4ru.exceptions.FailedParsingException;
import com.github.oxaoo.mp4ru.service.configuration.ApplicationConstants;
import com.github.oxaoo.mp4ru.service.service.ParserService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(
        value = "/parse",
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
)
public class ServiceController {

    private ParserService parserService;

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity post(@RequestHeader(
            name = ApplicationConstants.MALT_PARSER_DATA_FORMAT,
            required = false,
            defaultValue = ApplicationConstants.MALT_PARSER_CONLL_X_FORMAT) @NotNull final String dataFormat,
                               @RequestBody final String text) {
        return this.parse(text, dataFormat);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity get(@RequestHeader(
            name = ApplicationConstants.MALT_PARSER_DATA_FORMAT,
            required = false,
            defaultValue = ApplicationConstants.MALT_PARSER_CONLL_X_FORMAT) @NotNull final String dataFormat,
                              @RequestParam("text") final String text) {
        return this.parse(text, dataFormat);
    }

    private ResponseEntity parse(final String text, final String dataFormat) {
        try {
            final List<?> parsedText = this.parserService.parse(text, dataFormat);
            return ResponseEntity.ok(parsedText);
        } catch (final FailedParsingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionUtils.getStackTrace(e));
        }
    }

    @Autowired
    public void setParserService(final ParserService parserService) {
        this.parserService = parserService;
    }
}
