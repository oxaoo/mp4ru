package com.github.oxaoo.mp4ru.syntax.tagging;

import com.github.oxaoo.mp4ru.exceptions.ClassifierModelNotFoundException;
import com.github.oxaoo.mp4ru.exceptions.FailedStoreTokensException;
import com.github.oxaoo.mp4ru.exceptions.IncorrectTokenException;
import org.annolab.tt4j.TreeTaggerException;
import org.annolab.tt4j.TreeTaggerWrapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.github.oxaoo.mp4ru.syntax.GlobalPropertyKeys.CONLL_TEXT_FILE;


/**
 * The class represent the Part-of-Speech tagging.
 *
 * @author Alexander Kuleshov
 * @version 1.0
 * @since 12.02.2017
 */
public class PosTagger {
    private static final String DEFAULT_MODEL_FILE = "src/main/resources/russian-utf8.par";

    static {
        System.setProperty("treetagger.home", "src/main/resources/TreeTagger");
    }

    private final String modelFilePath;

    public PosTagger() {
        this.modelFilePath = DEFAULT_MODEL_FILE;
    }

    public PosTagger(String modelFilePath) {
        this.modelFilePath = modelFilePath;
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

    public void writeTokens(List<Conll> tokens) throws FailedStoreTokensException {
        File file = new File(CONLL_TEXT_FILE);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            for (Conll token : tokens) {
                bw.write(token.toRow());
            }
            bw.close();
        } catch (IOException e) {
            throw new FailedStoreTokensException("Failed to write the tokens to file \'" + CONLL_TEXT_FILE + "\'.", e);
        }
    }
}
