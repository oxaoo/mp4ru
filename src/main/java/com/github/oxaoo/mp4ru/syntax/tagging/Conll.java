package com.github.oxaoo.mp4ru.syntax.tagging;

/**
 * The class represents a CoNLL-X data format.
 * The main purpose of which is the multi-lingual dependency parsing.
 *
 * @author Alexander Kuleshov
 * @version 1.0
 * @see <a href="http://ilk.uvt.nl/conll/#dataformat">CoNLL-X data format</a>
 * @since 12.02.2017
 */
public class Conll {
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

    private char getPrefix(String feats) {
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

    public int getId() {
        return id;
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
