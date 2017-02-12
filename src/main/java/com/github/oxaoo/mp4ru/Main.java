package com.github.oxaoo.mp4ru;

import com.github.oxaoo.mp4ru.exceptions.ReadInputTextException;
import com.github.oxaoo.mp4ru.syntax.RussianParser;
import org.annolab.tt4j.TreeTaggerException;
import org.maltparser.core.exception.MaltChainedException;

import java.io.IOException;

public class Main {
    public static void main(String[] args)
            throws MaltChainedException, IOException, TreeTaggerException, ReadInputTextException {
        new RussianParser().run();
    }
}
