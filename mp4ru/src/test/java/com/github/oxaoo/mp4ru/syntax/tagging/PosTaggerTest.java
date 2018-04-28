package com.github.oxaoo.mp4ru.syntax.tagging;

import com.github.oxaoo.mp4ru.exceptions.ClassifierModelNotFoundException;
import com.github.oxaoo.mp4ru.exceptions.IncorrectTokenException;
import com.github.oxaoo.mp4ru.exceptions.InitPosTaggerException;
import com.github.oxaoo.mp4ru.syntax.tokenize.FragmentationType;
import com.github.oxaoo.mp4ru.syntax.tokenize.WordTokenizer;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class PosTaggerTest {
    private PosTagger tagger;
    private WordTokenizer tokenizer;


    public PosTaggerTest() throws ClassifierModelNotFoundException, InitPosTaggerException, IOException {
        String classifierModel = "../res/russian-utf8.par";
        String treeTaggerHome = "../res/";
        this.tagger = new PosTagger(classifierModel, treeTaggerHome);
        this.tokenizer = new WordTokenizer();
    }

    /**
     * Tagging test.
     * The tagger don't throw the exception during tagging special-char [u200e].
     *
     * @throws IncorrectTokenException          the incorrect token exception
     * @throws ClassifierModelNotFoundException the classifier model not found exception
     */
    @Test
    public void taggingTest() throws IncorrectTokenException, ClassifierModelNotFoundException {
        String text = "Отменить подписку на канал \"ByDaniel \u200E\u200E\u200E\u200E\u200E\u200E\u200E\u200E\u200E\u200E\"?";
        List<String> tokens = this.tokenizer.tokenization(text);
        List<Conll> conlls = this.tagger.tagging(tokens, FragmentationType.NO_FRAGMENTATION);
        Assert.assertFalse(conlls.isEmpty());
    }

    @Test
    public void taggingTest2() throws IncorrectTokenException, ClassifierModelNotFoundException {
        String text = "Политологи прогнозируют завинчивание гаек в качестве реакции властей Фото \u200B";
        List<String> tokens = this.tokenizer.tokenization(text);
        List<Conll> conlls = this.tagger.tagging(tokens, FragmentationType.NO_FRAGMENTATION);
        Assert.assertFalse(conlls.isEmpty());
    }
}
