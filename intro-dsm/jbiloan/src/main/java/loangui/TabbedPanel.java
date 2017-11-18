/*
 * This component represent the tabbed pane items panel
 */
package loangui;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import loanmain.CalcLoanItem;
import loanmain.ChangeListener;
import loanmain.DiffListener;
import loanmain.LoanItem;
import loanutils.FormatterFactory;
import loanutils.FrameUtils;
import static loanutils.MyBundle.translate;

/**
 * This component represent the tabbed pane items panel
 *
 * @author jean-blas imbert
 */
public class TabbedPanel extends JPanel implements ChangeListener, DiffListener {

    private static final long serialVersionUID = 1L;
    /**
     * Monthly without insurance amount label
     */
    private JLabel menLabel = new JLabel("0");
    /**
     * Monthly insurance only label
     */
    private JLabel assLabel = new JLabel("0");
    /**
     * Monthly total amount label
     */
    private JLabel totLabel = new JLabel("0");
    /**
     * Cost value label
     */
    private JLabel menCostLabel = new JLabel("0");
    /**
     * Cost insurance label
     */
    private JLabel assCostLabel = new JLabel("0");
    /**
     * Cost total amount label
     */
    private JLabel totCostLabel = new JLabel("0");
    /**
     * Effective rate value label
     */
    private JLabel effLabel = new JLabel("0");
    /**
     * Percentage of the salary label
     */
    private JLabel pctLabel = new JLabel("0");
    /**
     * Year total amount label
     */
    private JLabel ytaLabel = new JLabel("0");

    /**
     * Constructor
     */
    public TabbedPanel() {
        setLayout(new GridLayout(1, 3, 5, 5));
        add(buildMonthlyPanel());
        add(buildCostPanel());
        add(buildDiversPanel());
    }

    /**
     * Fill the components with their respective values
     *
     * @param pItem the Loan item corresponding to this panel
     */
    @Override
    public void itemChanged(final LoanItem pItem) {
        Double lMensHorsAss = CalcLoanItem.computeMensHorsAss(pItem);
        if (lMensHorsAss == null) {
            lMensHorsAss = 0D;
        }
        menLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lMensHorsAss.floatValue()));
        Double lMensAss = CalcLoanItem.computeMensAss(pItem);
        if (lMensAss == null) {
            lMensAss = 0D;
        }
        assLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lMensAss.floatValue()));
        Double lMens = lMensHorsAss + lMensAss;
        totLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lMens.floatValue()));
        Double lCoutHorsAss = lMensHorsAss * pItem.getDuree() * 12D - pItem.getAmount();
        menCostLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lCoutHorsAss.floatValue()));
        Double lCoutAss = lMensAss * pItem.getDuree() * 12D;
        assCostLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lCoutAss.floatValue()));
        Double lCout = lCoutHorsAss + lCoutAss + (pItem.getFrais() == null? 0D : pItem.getFrais());
        totCostLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lCout.floatValue()));
        Double lTauxEff = CalcLoanItem.calcTauxEff(pItem);
        effLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lTauxEff == null ? 0F : lTauxEff.floatValue()));
        Double lPctSalary = pItem.getSalary().equals(0F) ? 0F : lMens / pItem.getSalary() * 100D;
        pctLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lPctSalary.floatValue()));
        Double lPerYear = lMens * 12D;
        ytaLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lPerYear.floatValue()));
    }

    /**
     * Compute the real difference between two loan items
     *
     * @param pItem1 the first loan item
     * @param pItem2 the second loan item
     */
    @Override
    public void itemDiffed(final LoanItem pItem1, final LoanItem pItem2) {
        Double lMensHorsAss1 = CalcLoanItem.computeMensHorsAss(pItem1);
        if (lMensHorsAss1 == null) {
            lMensHorsAss1 = 0D;
        }
        Double lMensHorsAss2 = CalcLoanItem.computeMensHorsAss(pItem2);
        if (lMensHorsAss2 == null) {
            lMensHorsAss2 = 0D;
        }
        Double lDiffMensHorsAss = lMensHorsAss1 - lMensHorsAss2;
        menLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lDiffMensHorsAss.floatValue()));
        Double lMensAss1 = CalcLoanItem.computeMensAss(pItem1);
        if (lMensAss1 == null) {
            lMensAss1 = 0D;
        }
        Double lMensAss2 = CalcLoanItem.computeMensAss(pItem2);
        if (lMensAss2 == null) {
            lMensAss2 = 0D;
        }
        Double lDiffMensAss = lMensAss1 - lMensAss2;
        assLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lDiffMensAss.floatValue()));
        Double lMens1 = lMensHorsAss1 + lMensAss1;
        Double lMens2 = lMensHorsAss2 + lMensAss2;
        Double lDiffMens = lMens1 - lMens2;
        totLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lDiffMens.floatValue()));
        Double lCoutHorsAss1 = lMensHorsAss1 * pItem1.getDuree() * 12D - pItem1.getAmount();
        Double lCoutHorsAss2 = lMensHorsAss2 * pItem2.getDuree() * 12D - pItem2.getAmount();
        Double lDiffCoutHorsAss = lCoutHorsAss1 - lCoutHorsAss2;
        menCostLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lDiffCoutHorsAss.floatValue()));
        Double lCoutAss1 = lMensAss1 * pItem1.getDuree() * 12D;
        Double lCoutAss2 = lMensAss2 * pItem2.getDuree() * 12D;
        Double lDiffCoutAss = lCoutAss1 - lCoutAss2;
        assCostLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lDiffCoutAss.floatValue()));
        Double lCout1 = lCoutHorsAss1 + lCoutAss1;
        Double lCout2 = lCoutHorsAss2 + lCoutAss2;
        Double lDiffCout = lCout1 - lCout2 + pItem1.getFrais() - pItem2.getFrais();
        totCostLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lDiffCout.floatValue()));
        Double lTauxEff1 = CalcLoanItem.calcTauxEff(pItem1);
        Double lTauxEff2 = CalcLoanItem.calcTauxEff(pItem2);
        Double lDiffTauxEff = (lTauxEff1 == null ? 0D : lTauxEff1) - (lTauxEff2 == null ? 0D : lTauxEff2);
        effLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lDiffTauxEff.floatValue()));
        Double lPctSalary1 = pItem1.getSalary().equals(0F) ? 0F : lMens1 / pItem1.getSalary() * 100D;
        Double lPctSalary2 = pItem2.getSalary().equals(0F) ? 0F : lMens2 / pItem2.getSalary() * 100D;
        Double lDiffPctSalary = lPctSalary1 - lPctSalary2;
        pctLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lDiffPctSalary.floatValue()));
        Double lPerYear1 = lMens1 * 12D;
        Double lPerYear2 = lMens2 * 12D;
        Double lDiffPerYear = lPerYear1 - lPerYear2;
        ytaLabel.setText(FormatterFactory.fmtCurrencyNoSymbol(lDiffPerYear.floatValue()));
    }

    /**
     * Construct the panel that contains the monthly result components
     *
     * @return the filled panel
     */
    private JPanel buildMonthlyPanel() {
        JLabel lMenLabel = new JLabel(translate("mensualitesvalue"));
        lMenLabel.setToolTipText(translate("mensualitesvaluetooltip"));
        menLabel.setToolTipText(translate("mensualitesvaluetooltip"));
        JLabel lAssLabel = new JLabel(translate("mensualiteassurance"));
        lAssLabel.setToolTipText(translate("mensualiteassurancetooltip"));
        assLabel.setToolTipText(translate("mensualiteassurancetooltip"));
        JLabel lTotLabel = new JLabel(translate("mensualitetotal"));
        lTotLabel.setToolTipText(translate("mensualitetotaltooltip"));
        totLabel.setToolTipText(translate("mensualitetotaltooltip"));
        JPanel lPanel = new JPanel();
        lPanel.setBorder(BorderFactory.createTitledBorder(translate("mensualites")));
        lPanel.setToolTipText(translate("mensualitestooltip"));
        FrameUtils.buildPanelGroup(lPanel, new JComponent[][]{{lMenLabel, menLabel, new JLabel("\u20ac")},
            {lAssLabel, assLabel, new JLabel("\u20ac")},
            {lTotLabel, totLabel, new JLabel("\u20ac")}});
        return lPanel;
    }

    /**
     * Construct the panel that contains the cost result components
     *
     * @return the filled panel
     */
    private JPanel buildCostPanel() {
        JLabel lMenCostLabel = new JLabel(translate("costvalue"));
        lMenCostLabel.setToolTipText(translate("costvaluetooltip"));
        menCostLabel.setToolTipText(translate("costvaluetooltip"));
        JLabel lAssCostLabel = new JLabel(translate("costassurance"));
        lAssCostLabel.setToolTipText(translate("costassurancetooltip"));
        assCostLabel.setToolTipText(translate("costassurancetooltip"));
        JLabel lTotCostLabel = new JLabel(translate("costtotal"));
        lTotCostLabel.setToolTipText(translate("costtotaltooltip"));
        totCostLabel.setToolTipText(translate("costtotaltooltip"));
        JPanel lPanel = new JPanel();
        lPanel.setBorder(BorderFactory.createTitledBorder(translate("cost")));
        lPanel.setToolTipText(translate("costtooltip"));
        FrameUtils.buildPanelGroup(lPanel, new JComponent[][]{{lMenCostLabel, menCostLabel, new JLabel("\u20ac")},
            {lAssCostLabel, assCostLabel, new JLabel("\u20ac")},
            {lTotCostLabel, totCostLabel, new JLabel("\u20ac")}});
        return lPanel;
    }

    /**
     * Construct the panel that contains the divers result components
     *
     * @return the filled panel
     */
    private JPanel buildDiversPanel() {
        JLabel lEffLabel = new JLabel(translate("effvalue"));
        lEffLabel.setToolTipText(translate("effvaluetooltip"));
        effLabel.setToolTipText(translate("effvaluetooltip"));
        JLabel lPctLabel = new JLabel(translate("pctsalary"));
        lPctLabel.setToolTipText(translate("pctsalarytooltip"));
        pctLabel.setToolTipText(translate("pctsalarytooltip"));
        JLabel lYtaLabel = new JLabel(translate("yta"));
        lYtaLabel.setToolTipText(translate("ytatooltip"));
        ytaLabel.setToolTipText(translate("ytatooltip"));
        JPanel lPanel = new JPanel();
        lPanel.setBorder(BorderFactory.createTitledBorder(translate("divers")));
        lPanel.setToolTipText(translate("diverstooltip"));
        FrameUtils.buildPanelGroup(lPanel, new JComponent[][]{{lEffLabel, effLabel, new JLabel("%")},
            {lPctLabel, pctLabel, new JLabel("%")},
            {lYtaLabel, ytaLabel, new JLabel("\u20ac")}});
        return lPanel;
    }
}
