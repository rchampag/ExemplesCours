/*
 * JTextField that acceps only float numbers
 */
package jbiloan;

import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * JTextField that acceps only float numbers. According to the FormatterFactory, the format depends on the locale
 * (FRANCE or US).
 * <ul>
 * <li>In FRANCE, we have 100 000,00 (with a white space and a ",")
 * <li>In US, we have 100,000.00 (with a "," and a ".")
 * </ul>
 * And we want finally : 100000.00 (no space nor ",")
 *
 * @author jean-blas imbert
 */
public class Loanutil_FloatJTextField extends JTextField {

    private static final long serialVersionUID = 1L;

    /**
     * Get the current value as a float
     *
     * @return the float value
     */
    public Float getValue() {
        if (getText().isEmpty()) {
            return 0F;
        }
        String lStr = Loanutil_FormatterFactory.currencyToFloat(getText());
        return Float.parseFloat(lStr);
    }

    /**
     * Constructor
     *
     * @param pCols number of columns
     */
    public Loanutil_FloatJTextField(int pCols) {
        super(pCols);
    }

    @Override
    protected Document createDefaultModel() {
        return new NumberDoc();
    }

    /**
     * The underlying text document that accepts only float numbers
     */
    private class NumberDoc extends PlainDocument {

        private static final long serialVersionUID = 1L;

        @Override
        public void insertString(int pOffs, String pStr, AttributeSet pAttr)
                throws BadLocationException {

            if (pStr == null) {
                return;
            }
            try {
                String lStr = Loanutil_FormatterFactory.currencyToFloat(pStr);
                String lText = Loanutil_FormatterFactory.currencyToFloat(Loanutil_FloatJTextField.this.getText());
                Float.parseFloat(lText + lStr);
                super.insertString(pOffs, pStr, pAttr);
            } catch (NumberFormatException lEx) {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }
}
