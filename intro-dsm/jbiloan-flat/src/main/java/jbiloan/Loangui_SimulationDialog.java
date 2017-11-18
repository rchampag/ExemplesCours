/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbiloan;

import static jbiloan.Loanutil_MyBundle.translate;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author jean-blasimbert
 */
public class Loangui_SimulationDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    /**
     * The current simulation model that is displayed in this table
     */
    private Loanmain_SimulModel model = null;

    /**
     * Constructor
     *
     * @param pOwner the frame owner (should be the main frame)
     * @param pItem the simulated loan item
     */
    public Loangui_SimulationDialog(final Window pOwner, final Loanmain_LoanItem pItem) {
        super(pOwner);
        init(pItem);
    }

    /**
     * Initialise the frame
     *
     * @param pItem the simulated loan item
     */
    private void init(final Loanmain_LoanItem pItem) {
        setTitle(translate("simulation") + pItem.getName());
        add(constructWestPanel(pItem), BorderLayout.WEST);
        add(constructSouthPanel(), BorderLayout.SOUTH);
        add(new JScrollPane(buildTable(pItem)), BorderLayout.CENTER);
        pack();
        setSize(600, 500);
        setResizable(true);
        Loanutil_FrameUtils.screenCenter(this);
    }

    /**
     * Construct the table model and its associated table
     *
     * @param pItem the simulated loan item
     * @return the filled table
     */
    private JTable buildTable(final Loanmain_LoanItem pItem) {
        model = new Loanmain_SimulModel(pItem);
        JTable lTable = new JTable(model);
        class MyRenderer extends DefaultTableCellRenderer {

            private static final long serialVersionUID = 1L;

            @Override
            public void setValue(Object pValue) {
                if (pValue instanceof Double) {
                    setText(Loanutil_FormatterFactory.fmtCurrencyNoSymbol(((Double) pValue).floatValue()));
                } else {
                    super.setValue(pValue);
                }
            }
        }
        lTable.setDefaultRenderer(Object.class, new MyRenderer());
        return lTable;
    }

    /**
     * Construct the west panel with the loan item characteristics
     *
     * @param pItem the simulated loan item
     * @return the filled panel
     */
    private JPanel constructWestPanel(final Loanmain_LoanItem pItem) {
        JPanel lPanel = new JPanel();
        Loanutil_FrameUtils.buildPanelGroup(lPanel, new JComponent[][]{
            {new JLabel(translate("montant")), new JLabel(Loanutil_FormatterFactory.fmtCurrencyNoSymbol(pItem.getAmount())), new JLabel("\u20ac")},
            {new JLabel(translate("taux")), new JLabel(Loanutil_FormatterFactory.fmtCurrencyNoSymbol(pItem.getTaux())), new JLabel("%")},
            {new JLabel(translate("mensualite")), new JLabel(Loanutil_FormatterFactory.fmtCurrencyNoSymbol(pItem.getMensualite())), new JLabel("\u20ac")},
            {new JLabel(translate("duree")), new JLabel(Loanutil_FormatterFactory.fmtCurrencyNoSymbol(pItem.getDuree())), new JLabel(translate("ans"))},
            {new JLabel(translate("assurance")), new JLabel(Loanutil_FormatterFactory.fmtCurrencyNoSymbol(pItem.getInsurance())), new JLabel("%")}});
        lPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 50));
        return lPanel;
    }

    /**
     * Contruct the south panel with buttons
     *
     * @return the filled panel
     */
    private JPanel constructSouthPanel() {
        JButton lCsvBtn = Loanutil_JbiBtnFactory.PRINT.create(new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent pEvent) {
                try {
                    Loanutil_FrameUtils.printToCsv(Loangui_SimulationDialog.this, model.toCsv());
                } catch (IOException lEx) {
                    JOptionPane.showMessageDialog(null, translate("UnableToCsv") + lEx.getLocalizedMessage(),
                            translate("erreur"), JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton lCloseBtn = Loanutil_JbiBtnFactory.CLOSE.create(new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent pEvent) {
                Loangui_SimulationDialog.this.setVisible(false);
            }
        });
        return Loanutil_FrameUtils.createSouthPanel(new JComponent[]{lCsvBtn, lCloseBtn});
     }
}
