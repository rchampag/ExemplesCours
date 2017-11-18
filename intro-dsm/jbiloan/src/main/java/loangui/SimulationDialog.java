/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package loangui;

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
import loanmain.LoanItem;
import loanmain.SimulModel;
import loanutils.FormatterFactory;
import loanutils.FrameUtils;
import loanutils.JbiBtnFactory;
import static loanutils.MyBundle.translate;

/**
 *
 * @author jean-blasimbert
 */
public class SimulationDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    /**
     * The current simulation model that is displayed in this table
     */
    private SimulModel model = null;

    /**
     * Constructor
     *
     * @param pOwner the frame owner (should be the main frame)
     * @param pItem the simulated loan item
     */
    public SimulationDialog(final Window pOwner, final LoanItem pItem) {
        super(pOwner);
        init(pItem);
    }

    /**
     * Initialise the frame
     *
     * @param pItem the simulated loan item
     */
    private void init(final LoanItem pItem) {
        setTitle(translate("simulation") + pItem.getName());
        add(constructWestPanel(pItem), BorderLayout.WEST);
        add(constructSouthPanel(), BorderLayout.SOUTH);
        add(new JScrollPane(buildTable(pItem)), BorderLayout.CENTER);
        pack();
        setSize(600, 500);
        setResizable(true);
        FrameUtils.screenCenter(this);
    }

    /**
     * Construct the table model and its associated table
     *
     * @param pItem the simulated loan item
     * @return the filled table
     */
    private JTable buildTable(final LoanItem pItem) {
        model = new SimulModel(pItem);
        JTable lTable = new JTable(model);
        class MyRenderer extends DefaultTableCellRenderer {

            private static final long serialVersionUID = 1L;

            @Override
            public void setValue(Object pValue) {
                if (pValue instanceof Double) {
                    setText(FormatterFactory.fmtCurrencyNoSymbol(((Double) pValue).floatValue()));
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
    private JPanel constructWestPanel(final LoanItem pItem) {
        JPanel lPanel = new JPanel();
        FrameUtils.buildPanelGroup(lPanel, new JComponent[][]{
            {new JLabel(translate("montant")), new JLabel(FormatterFactory.fmtCurrencyNoSymbol(pItem.getAmount())), new JLabel("\u20ac")},
            {new JLabel(translate("taux")), new JLabel(FormatterFactory.fmtCurrencyNoSymbol(pItem.getTaux())), new JLabel("%")},
            {new JLabel(translate("mensualite")), new JLabel(FormatterFactory.fmtCurrencyNoSymbol(pItem.getMensualite())), new JLabel("\u20ac")},
            {new JLabel(translate("duree")), new JLabel(FormatterFactory.fmtCurrencyNoSymbol(pItem.getDuree())), new JLabel(translate("ans"))},
            {new JLabel(translate("assurance")), new JLabel(FormatterFactory.fmtCurrencyNoSymbol(pItem.getInsurance())), new JLabel("%")}});
        lPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 50));
        return lPanel;
    }

    /**
     * Contruct the south panel with buttons
     *
     * @return the filled panel
     */
    private JPanel constructSouthPanel() {
        JButton lCsvBtn = JbiBtnFactory.PRINT.create(new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent pEvent) {
                try {
                    FrameUtils.printToCsv(SimulationDialog.this, model.toCsv());
                } catch (IOException lEx) {
                    JOptionPane.showMessageDialog(null, translate("UnableToCsv") + lEx.getLocalizedMessage(),
                            translate("erreur"), JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton lCloseBtn = JbiBtnFactory.CLOSE.create(new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent pEvent) {
                SimulationDialog.this.setVisible(false);
            }
        });
        return FrameUtils.createSouthPanel(new JComponent[]{lCsvBtn, lCloseBtn});
     }
}
