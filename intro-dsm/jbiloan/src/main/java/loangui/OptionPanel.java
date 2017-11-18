/*
 * The panels for options components
 */
package loangui;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import loanmain.CalcLoanItem;
import loanmain.ChangeListener;
import loanmain.LoanControler;
import loanmain.LoanItem;
import loanutils.FloatJTextField;
import loanutils.FormatterFactory;
import loanutils.FrameUtils;
import static loanutils.MyBundle.translate;

/**
 * The panels for options components
 *
 * @author jean-blas imbert
 */
public class OptionPanel extends JPanel implements ChangeListener {

    private static final long serialVersionUID = 1L;
    /**
     * Agency fees text field
     */
    private FloatJTextField afeTF = new FloatJTextField(10);
    /**
     * Insurance fees text field
     */
    private FloatJTextField assTF = new FloatJTextField(10);
    /**
     * Notary fees text field
     */
    private FloatJTextField notTF = new FloatJTextField(10);
    /**
     * Salary text field
     */
    private FloatJTextField salTF = new FloatJTextField(10);
    /**
     * The loan controler
     */
    private LoanControler controler = null;
    /**
     * This field stores the current value when the mouse enter a text field
     */
    private Float curValue = null;

    /**
     * Constructor
     */
    public OptionPanel(final LoanControler pControler) {
        controler = pControler;
        layoutComponents();
        //Add text field focus listener
        FocusListener lFocusListener = new FocusListener() {
            @Override
            public void focusGained(FocusEvent pEvent) {
                curValue = ((FloatJTextField) pEvent.getSource()).getValue();
            }

            @Override
            public void focusLost(FocusEvent pEvent) {
                Float lNewValue = ((FloatJTextField) pEvent.getSource()).getValue();
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
    public void itemChanged(final LoanItem pItem) {
        afeTF.setText(FormatterFactory.fmtCurrencyNoSymbol(pItem.getFrais()));
        assTF.setText(FormatterFactory.fmtCurrencyNoSymbol(pItem.getInsurance()));
        Double lNotFee = CalcLoanItem.computeNotaryFee(pItem);
        notTF.setText(FormatterFactory.fmtCurrencyNoSymbol(lNotFee.floatValue()));
        salTF.setText(FormatterFactory.fmtCurrencyNoSymbol(pItem.getSalary()));
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
        FrameUtils.buildPanelGroup(this, new JComponent[][]{{lAfeLabel, afeTF, new JLabel("\u20ac")},
            {lSalLabel, salTF, new JLabel("\u20ac")}, {lAssLabel, assTF, new JLabel("%")},
            {lNotLabel, notTF, new JLabel("\u20ac")}});
    }
}
