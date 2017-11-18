/*
 * The panels for options components
 */
package jbiloan;

import static jbiloan.Loanutil_MyBundle.translate;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The panels for options components
 *
 * @author jean-blas imbert
 */
public class Loangui_OptionPanel extends JPanel implements Loanmain_ChangeListener {

    private static final long serialVersionUID = 1L;
    /**
     * Agency fees text field
     */
    private Loanutil_FloatJTextField afeTF = new Loanutil_FloatJTextField(10);
    /**
     * Insurance fees text field
     */
    private Loanutil_FloatJTextField assTF = new Loanutil_FloatJTextField(10);
    /**
     * Notary fees text field
     */
    private Loanutil_FloatJTextField notTF = new Loanutil_FloatJTextField(10);
    /**
     * Salary text field
     */
    private Loanutil_FloatJTextField salTF = new Loanutil_FloatJTextField(10);
    /**
     * The loan controler
     */
    private Loanmain_LoanControler controler = null;
    /**
     * This field stores the current value when the mouse enter a text field
     */
    private Float curValue = null;

    /**
     * Constructor
     */
    public Loangui_OptionPanel(final Loanmain_LoanControler pControler) {
        controler = pControler;
        layoutComponents();
        //Add text field focus listener
        FocusListener lFocusListener = new FocusListener() {
            @Override
            public void focusGained(FocusEvent pEvent) {
                curValue = ((Loanutil_FloatJTextField) pEvent.getSource()).getValue();
            }

            @Override
            public void focusLost(FocusEvent pEvent) {
                Float lNewValue = ((Loanutil_FloatJTextField) pEvent.getSource()).getValue();
                if ((curValue == null && lNewValue != null) || (curValue != null && !curValue.equals(lNewValue))) {
                    controler.updateOption(afeTF.getValue(), assTF.getValue(), notTF.getValue(), salTF.getValue());
                }
                curValue = null;
            }
        };
        afeTF.addFocusListener(lFocusListener);
        assTF.addFocusListener(lFocusListener);
        notTF.addFocusListener(lFocusListener);
        salTF.addFocusListener(lFocusListener);
    }

    
    /**
     * Fill the components with their respective values
     *
     * @param pItem the Loan item corresponding to this panel
     */
    @Override
    public void itemChanged(final Loanmain_LoanItem pItem) {
        afeTF.setText(Loanutil_FormatterFactory.fmtCurrencyNoSymbol(pItem.getFrais()));
        assTF.setText(Loanutil_FormatterFactory.fmtCurrencyNoSymbol(pItem.getInsurance()));
        Double lNotFee = Loanmain_CalcLoanItem.computeNotaryFee(pItem);
        notTF.setText(Loanutil_FormatterFactory.fmtCurrencyNoSymbol(lNotFee.floatValue()));
        salTF.setText(Loanutil_FormatterFactory.fmtCurrencyNoSymbol(pItem.getSalary()));
        afeTF.setEditable(!controler.isDiffed());
        assTF.setEditable(!controler.isDiffed());
        notTF.setEditable(!controler.isDiffed());
        salTF.setEditable(!controler.isDiffed());
    }

    /**
     * Lay out the components
     */
    private void layoutComponents() {
        JLabel lAfeLabel = new JLabel(translate("fraisdossier"));
        lAfeLabel.setToolTipText(translate("fraisdossiertooltip"));
        afeTF.setToolTipText(translate("fraisdossiertooltip"));
        JLabel lSalLabel = new JLabel(translate("salaire"));
        lSalLabel.setToolTipText(translate("salairetooltip"));
        salTF.setToolTipText(translate("salairetooltip"));
        JLabel lNotLabel = new JLabel(translate("notaire"));
        lNotLabel.setToolTipText(translate("notairetooltip"));
        notTF.setToolTipText(translate("notairetooltip"));
        JLabel lAssLabel = new JLabel(translate("assurance"));
        lAssLabel.setToolTipText(translate("assurancetooltip"));
        assTF.setToolTipText(translate("assurancetooltip"));
        setBorder(BorderFactory.createTitledBorder(translate("options")));
        setToolTipText(translate("optionstooltip"));
        Loanutil_FrameUtils.buildPanelGroup(this, new JComponent[][]{{lAfeLabel, afeTF, new JLabel("\u20ac")},
            {lSalLabel, salTF, new JLabel("\u20ac")}, {lAssLabel, assTF, new JLabel("%")},
            {lNotLabel, notTF, new JLabel("\u20ac")}});
    }
}
