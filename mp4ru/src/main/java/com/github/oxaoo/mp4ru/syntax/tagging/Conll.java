package com.github.oxaoo.mp4ru.syntax.tagging;

import com.github.oxaoo.mp4ru.exceptions.FailedConllMapException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * The class represents a CoNLL-X data format.
 * The main purpose of which is the multi-lingual dependency parseFromFile.
 *
 * @author Alexander Kuleshov
 * @version 0.1.0
 * @see <a href="http://ilk.uvt.nl/conll/#dataformat">CoNLL-X data format</a>
 * @since 12.02.2017
 */
public class Conll {
    private static final Logger LOG = LoggerFactory.getLogger(Conll.class);

    private static final char UNDERSCORE = '_';
    private static final int UNKNOWN_HEAD = -1;
    private static final int UNKNOWN_PROJECTIVE_HEAD = -2;

    /**
     * Token counter, starting at 1 for each new sentence.
     */
    private final int id;

    /**
     * Word form or punctuation symbol.
     */
    private final String form;

    /**
     * Lemma or stem (depending on particular data set) of word form, or an underscore if not available.
     */
    private final String lemma;

    /**
     * Coarse-grained part-of-speech tag, where tagset depends on the language.
     */
    private final char cPosTag;

    /**
     * Fine-grained part-of-speech tag, where the tagset depends on the language,
     * or identical to the coarse-grained part-of-speech tag if not available.
     */
    private final char posTag;

    /**
     * Set of morphological-syntactic features.
     * It is of the MorphoSyntactic Descriptions (MSDs).
     * In which it's fixed attributes are present:
     * - Category,
     * - Type,
     * - Gender,
     * - Number,
     * - Case,
     * - Animate
     *
     * @see <a href="http://corpus.leeds.ac.uk/mocky/back.1_div.4.html"> Lexical MSDs</a>
     * @see <a href="http://corpus.leeds.ac.uk/mocky/"> Russian statistical taggers and parsers</a>
     */
    private final String feats;

    /**
     * Head of the current token, which is either a value of ID or zero ('0').
     */
    private final int head;

    /**
     * Dependency relation to the HEAD. The set of dependency relations depends on the particular language.
     */
    private final String depRel;

    /**
     * Projective head of current token, which is either a value of ID or zero ('0'), or an underscore if not available.
     * The dependency structure resulting from the PHEAD column is guaranteed to be projective,
     * whereas the structures resulting from the HEAD column will be non-projective
     * for some sentences of some languages.
     */
    private final int pHead;

    /**
     * Dependency relation to the PHEAD, or an underscore if not available.
     * The set of dependency relations depends on the particular language.
     */
    private final String pDepRel;


    /**
     * The constructor suitable for use after the morphological analysis.
     *
     * @param form  the word form
     * @param lemma the lemma of word
     * @param feats the set of morpho-syntactic features
     */
    public Conll(int id, String form, String lemma, String feats) {
        char featsPrefix = this.getPrefix(feats);

        this.id = id;
        this.form = form;
        this.lemma = lemma;
        this.cPosTag = featsPrefix;
        this.posTag = featsPrefix;
        this.feats = feats;

        this.head = UNKNOWN_HEAD;
        this.depRel = String.valueOf(UNDERSCORE);
        this.pHead = UNKNOWN_PROJECTIVE_HEAD;
        this.pDepRel = String.valueOf(UNDERSCORE);
    }

    public Conll(int id, String form, String lemma,
                 char cPosTag, char posTag, String feats,
                 int head, String depRel, int pHead, String pDepRel) {
        this.id = id;
        this.form = form;
        this.lemma = lemma;
        this.cPosTag = cPosTag;
        this.posTag = posTag;
        this.feats = feats;
        this.head = head;
        this.depRel = depRel;
        this.pHead = pHead;
        this.pDepRel = pDepRel;
    }

    public char getPrefix(String feats) {
        if (feats == null || feats.startsWith("\\W")) {
            return UNDERSCORE;
        }
        return feats.charAt(0);
    }

    public String toRow() {

        return id + "\t"
                + form + "\t"
                + lemma + "\t"
                + cPosTag + "\t"
                + posTag + "\t"
                + feats + "\t"
                + (head == UNKNOWN_HEAD ? "_" : head) + "\t"
                + depRel + "\t"
                + (pHead == UNKNOWN_PROJECTIVE_HEAD ? "_" : pHead) + "\t"
                + pDepRel + "\n";
    }

    public static Conll safeMap(String params) {
        try {
            return map(params);
        } catch (FailedConllMapException e) {
            LOG.warn("Failed mapping token from string to conll. Cause: {}", e.getMessage());
            return null;
        }
    }

    public static Conll map(String params) throws FailedConllMapException {
        if (params == null || params.isEmpty()) {
            throw new FailedConllMapException("Empty parameter string.");
        }
        return map(Arrays.asList(params.trim().split("\\s")));
    }

    public static Conll map(List<String> params) throws FailedConllMapException {
        //fixme can be '!=10'
        if (params.size() != 10) {
            throw new FailedConllMapException("Not enough arguments to map. Size: " + params.size());
        }

        try {
            int id = Integer.valueOf(params.get(0));
            String form = params.get(1);
            String lemma = params.get(2);
            char cPosTag = params.get(3).charAt(0);
            char posTag = params.get(4).charAt(0);
            String feats = params.get(5);
            int head = Integer.valueOf(params.get(6));
            String depRel = params.get(7);
            int pHead = params.get(8).equals(String.valueOf(UNDERSCORE))
                    ? UNKNOWN_PROJECTIVE_HEAD
                    : Integer.valueOf(params.get(8));
            String pDepRel = params.get(9);
            return new Conll(id, form, lemma, cPosTag, posTag, feats, head, depRel, pHead, pDepRel);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new FailedConllMapException("Incorrect parameters for mapping to an conll.", e);
        }
    }

    public int getId() {
        return id;
    }

    public int getHead() {
        return head;
    }

    public String getForm() {
        return form;
    }

    public String getLemma() {
        return lemma;
    }

    public char getPosTag() {
        return posTag;
    }

    public String getFeats() {
        return feats;
    }

    public String getDepRel() {
        return depRel;
    }

    @Override
    public String toString() {
        return "Conll{" +
                "id=" + id +
                ", form='" + form + '\'' +
                ", lemma='" + lemma + '\'' +
                ", cPosTag=" + cPosTag +
                ", posTag=" + posTag +
                ", feats='" + feats + '\'' +
                ", head=" + (head == UNKNOWN_HEAD ? "_" : head) +
                ", depRel='" + depRel + '\'' +
                ", pHead=" + (pHead == UNKNOWN_PROJECTIVE_HEAD ? "_" : pHead) +
                ", pDepRel='" + pDepRel + '\'' +
                '}';
    }
}
