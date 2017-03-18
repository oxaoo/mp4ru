package com.github.oxaoo.mp4ru.utils;

import com.github.oxaoo.mp4ru.ulils.ParserUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 17.02.2017
 */
public class ParserUtilsTest {
    private ParserUtils utils;

    @Before
    public void init(){
        this.utils = new ParserUtils();
    }

    @Test
    public void makeParseFilePathCurrentDirTest() {
        String textFile = "testTextFile.txt";
        String parseFile = this.utils.makeParseFilePath(textFile);
        Assert.assertEquals("testTextFile.parse", parseFile);
    }

    @Test
    public void makeParseFilePathNextDirTest() {
        String textFile = "../dir/testTextFile.txt";
        String parseFile = this.utils.makeParseFilePath(textFile);
        Assert.assertEquals("../dir/testTextFile.parse", parseFile);
    }
}
