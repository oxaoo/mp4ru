package com.github.oxaoo.mp4ru.syntax.tagging;

import com.github.oxaoo.mp4ru.exceptions.ClassifierModelNotFoundException;
import com.github.oxaoo.mp4ru.exceptions.IncorrectTokenException;
import org.annolab.tt4j.TreeTaggerException;
import org.annolab.tt4j.TreeTaggerWrapper;

import java.io.IOException;
import java.util.List;


/**
 * The class represent the Part-of-Speech tagging.
 *
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 12.02.2017
 */
public class PosTagger {
    private static final String TREE_TAGGER_HOME_PROPERTY = "treetagger.home";

    private final String modelFilePath;

    public PosTagger(String modelFilePath, String treeTaggerHome) {
        this.modelFilePath = modelFilePath;
        System.setProperty(TREE_TAGGER_HOME_PROPERTY, treeTaggerHome);
    }

    /**
     * Part-of-Speech tagging the list of tokens.
     *
     * @param tokens the list of tokens
     * @return the list of processed tokens in CoNLL format
     * @throws ClassifierModelNotFoundException throw if classifier's model isn't found
     * @throws IncorrectTokenException          throw if there are incorrect tokens
     */
    public List<Conll> tagging(List<String> tokens) throws ClassifierModelNotFoundException, IncorrectTokenException {
        TreeTaggerWrapper<String> tt = new TreeTaggerWrapper<>();
        AdvancedTokenHandler<Conll> tokenHandler = new StatefulTokenHandler();
        try {
            tt.setModel(this.modelFilePath);
            tt.setHandler(tokenHandler);
            tt.process(tokens);
        } catch (IOException e) {
            throw new ClassifierModelNotFoundException("The classifier's model \'"
                    + this.modelFilePath + "\' isn't found.", e);
        } catch (TreeTaggerException e) {
            throw new IncorrectTokenException("There is an incorrect token.", e);
        } finally {
            tt.destroy();
        }
        return tokenHandler.getTokens();
    }
}
