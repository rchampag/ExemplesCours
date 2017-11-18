/*
 * The panels for entries components
 */
package jbiloan;

import static jbiloan.Loanutil_MyBundle.translate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The panels for entries components
 *
 * @author jean-blas imbert
 */
public class Loangui_EntryPanel extends JPanel implements Loanmain_ChangeListener {

    private static final long serialVersionUID = 1L;
    /**
     * Amount text field
     */
    private Loanutil_FloatJTextField amoTF = new Loanutil_FloatJTextField(10);
    /**
     * Taux text field
     */
    private Loanutil_FloatJTextField tauTF = new Loanutil_FloatJTextField(10);
    /**
     * Monthly fee text field
     */
    private Loanutil_FloatJTextField monTF = new Loanutil_FloatJTextField(10);
    /**
     * Duration text field
     */
    private Loanutil_FloatJTextField timTF = new Loanutil_FloatJTextField(10);
    /**
     * Checkbox to select the amount
     */
    private JCheckBox amoCB = new JCheckBox();
    /**
     * Checkbox to select the rate
     */
    private JCheckBox tauCB = new JCheckBox();
    /**
     * Checkbox to select the month length
     */
    private JCheckBox monCB = new JCheckBox();
    /**
     * Checkbox to select the duration
     */
    private JCheckBox timCB = new JCheckBox();
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
    public Loangui_EntryPanel(final Loanmain_LoanControler pControler) {
        controler = pControler;
        layoutComponents();
        synchronizeCBandTF();
        //Add the check box action listener
        ActionListener lAL = buildCBActionListener();
        amoCB.addActionListener(lAL);
        tauCB.addActionListener(lAL);
        monCB.addActionListener(lAL);
        timCB.addActionListener(lAL);
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
                    controler.updateEntry(amoTF.getValue(), tauTF.getValue(), monTF.getValue(), timTF.getValue());
                }
                curValue = null;
            }
        };
        amoTF.addFocusListener(lFocusListener);
        tauTF.addFocusListener(lFocusListener);
        monTF.addFocusListener(lFocusListener);
        timTF.addFocusListener(lFocusListener);
    }

    /**
     * The check box action listener (same for all checkbox)
     *
     * @return the filled action listener
     */
    private ActionListener buildCBActionListener() {
        ActionListener lAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent pEvent) {
                JCheckBox lCB = ((JCheckBox) pEvent.getSource());
                if (amoCB.isSelected() && tauCB.isSelected() && monCB.isSelected() && timCB.isSelected()) {
                    JOptionPane.showMessageDialog(null, translate("only3over4"), translate("warning"), JOptionPane.WARNING_MESSAGE);
                    lCB.setSelected(false);
                }
                synchronizeCBandTF();
            }
        };
        return lAL;
    }

    /**
     * Synchronize the checkbox and the text fields. A text field is enable if and only if its corresponding checkbox is
     * selected.
     */
    public final void synchronizeCBandTF() {
        amoTF.setEnabled(amoCB.isSelected());
        tauTF.setEnabled(tauCB.isSelected());
        monTF.setEnabled(monCB.isSelected());
        timTF.setEnabled(timCB.isSelected());
        Loanmain_LoanItem.LoanType lType = Loanmain_LoanItem.LoanType.MENSUALITE;
        if (!amoCB.isSelected()) {
            lType = Loanmain_LoanItem.LoanType.MONTANT;
        } else if (!tauCB.isSelected()) {
            lType = Loanmain_LoanItem.LoanType.TAUX;
        } else if (!timCB.isSelected()) {
            lType = Loanmain_LoanItem.LoanType.DUREE;
        }
        controler.setLoanType(lType);
    }

    /**
     * Lay out the components
     */
    private void layoutComponents() {
        JLabel lAmoLabel = new JLabel(translate("montant"));
        lAmoLabel.setToolTipText(translate("montanttooltip"));
        amoTF.setToolTipText(translate("montanttooltip"));
        JLabel lTauLabel = new JLabel(translate("taux"));
        lTauLabel.setToolTipText(translate("tauxtooltip"));
        tauTF.setToolTipText(translate("tauxtooltip"));
        JLabel lMenLabel = new JLabel(translate("mensualite"));
        lMenLabel.setToolTipText(translate("mensualitetooltip"));
        monTF.setToolTipText(translate("mensualitetooltip"));
        JLabel lTimLabel = new JLabel(translate("duree"));
        lTimLabel.setToolTipText(translate("dureetooltip"));
        timTF.setToolTipText(translate("dureetooltip"));
        setBorder(BorderFactory.createTitledBorder(translate("entrees")));
        setToolTipText(translate("entreestooltip"));
        Loanutil_FrameUtils.buildPanelGroup(this, new JComponent[][]{{amoCB, lAmoLabel, amoTF, new JLabel("\u20ac")},
            {tauCB, lTauLabel, tauTF, new JLabel("%")}, {monCB, lMenLabel, monTF, new JLabel("\u20ac")},
            {timCB, lTimLabel, timTF, new JLabel(translate("an"))}});
    }

    /**
     * Fill the components with their respective values
     *
     * @param pItem the Loan item corresponding to this panel
     */
    @Override
    public void itemChanged(final Loanmain_LoanItem pItem) {
        monTF.setText(Loanutil_FormatterFactory.fmtCurrencyNoSymbol(pItem.getMensualite()));
        tauTF.setText(Loanutil_FormatterFactory.fmtCurrencyNoSymbol(pItem.getTaux()));
        timTF.setText(Loanutil_FormatterFactory.fmtCurrencyNoSymbol(pItem.getDuree()));
        amoTF.setText(Loanutil_FormatterFactory.fmtCurrencyNoSymbol(pItem.getAmount()));
        amoCB.setSelected(pItem.getLoanType() != Loanmain_LoanItem.LoanType.MONTANT);
        tauCB.setSelected(pItem.getLoanType() != Loanmain_LoanItem.LoanType.TAUX);
        timCB.setSelected(pItem.getLoanType() != Loanmain_LoanItem.LoanType.DUREE);
        monCB.setSelected(pItem.getLoanType() != Loanmain_LoanItem.LoanType.MENSUALITE);
        monTF.setEditable(!controler.isDiffed());
        tauTF.setEditable(!controler.isDiffed());
        timTF.setEditable(!controler.isDiffed());
        amoTF.setEditable(!controler.isDiffed());
        amoCB.setEnabled(!controler.isDiffed());
        tauCB.setEnabled(!controler.isDiffed());
        timCB.setEnabled(!controler.isDiffed());
        monCB.setEnabled(!controler.isDiffed());
    }
}
