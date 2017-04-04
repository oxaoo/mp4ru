package com.github.oxaoo.mp4ru.syntax.tagging;

import com.github.oxaoo.mp4ru.common.ResourceResolver;
import com.github.oxaoo.mp4ru.exceptions.ClassifierModelNotFoundException;
import com.github.oxaoo.mp4ru.exceptions.IncorrectTokenException;
import com.github.oxaoo.mp4ru.exceptions.InitPosTaggerException;
import com.github.oxaoo.mp4ru.exceptions.ResourceResolverException;
import com.github.oxaoo.mp4ru.syntax.tokenize.FragmentationType;
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
    private final TreeTaggerWrapper<String> tt;

    public PosTagger(String modelFilePath, String treeTaggerHome)
            throws ClassifierModelNotFoundException, InitPosTaggerException, IOException {
        String absoluteModelFilePath;
        try {
            absoluteModelFilePath = ResourceResolver.getAbsolutePath(modelFilePath);
        } catch (ResourceResolverException e) {
            throw new InitPosTaggerException("The classifier's model \'"
                    + modelFilePath + "\' isn't found.", e);
        }
        String absoluteTreeTaggerHome;
        try {
            absoluteTreeTaggerHome = ResourceResolver.getAbsolutePath(treeTaggerHome);
        } catch (ResourceResolverException e) {
            throw new InitPosTaggerException("The TreeTagger home \'"
                    + treeTaggerHome + "\' isn't found.", e);
        }

        this.modelFilePath = absoluteModelFilePath;
        System.setProperty(TREE_TAGGER_HOME_PROPERTY, absoluteTreeTaggerHome);
        this.tt = new TreeTaggerWrapper<>();
        this.tt.setModel(this.modelFilePath);
    }

    /**
     * Part-of-Speech tagging the list of tokens.
     *
     * @param tokens            the list of tokens
     * @param fragmentationType the fragmentation type
     * @return the list of processed tokens in CoNLL format
     * @throws ClassifierModelNotFoundException throw if classifier's model isn't found
     * @throws IncorrectTokenException          throw if there are incorrect tokens
     */
    public synchronized List<Conll> tagging(List<String> tokens, FragmentationType fragmentationType)
            throws ClassifierModelNotFoundException, IncorrectTokenException {
//        TreeTaggerWrapper<String> tt = new TreeTaggerWrapper<>();
        AdvancedTokenHandler<Conll> tokenHandler = new StatefulTokenHandler(fragmentationType);
        try {
//            tt.setModel(this.modelFilePath);
            tt.setHandler(tokenHandler);
            tt.process(tokens);
        } catch (IOException e) {
            throw new ClassifierModelNotFoundException("The classifier's model \'"
                    + this.modelFilePath + "\' isn't found.", e);
        } catch (TreeTaggerException e) {
            throw new IncorrectTokenException("There is an incorrect token.", e);
        } /*finally {
            tt.destroy();
        }*/
        return tokenHandler.getTokens();
    }
}
