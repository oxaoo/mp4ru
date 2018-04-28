package com.github.oxaoo.mp4ru.service.controller;

import com.github.oxaoo.mp4ru.exceptions.FailedParsingException;
import com.github.oxaoo.mp4ru.syntax.RussianParser;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/parse")
public class ServiceController {

    private RussianParser parser;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity post(@RequestBody String text) {
        try {
            final List<String> parsedText = this.parser.parse(text);
            return ResponseEntity.ok(parsedText);
        } catch (final FailedParsingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionUtils.getStackTrace(e));
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity get(@RequestParam("text") String text) {
        try {
            final List<String> parsedText = this.parser.parse(text);
            return ResponseEntity.ok(parsedText);
        } catch (final FailedParsingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionUtils.getStackTrace(e));
        }
    }

    @Autowired
    public void setParser(final RussianParser parser) {
        this.parser = parser;
    }
}
