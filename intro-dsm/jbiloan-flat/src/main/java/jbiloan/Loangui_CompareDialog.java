/*
 * Comparison frame.
 */
package jbiloan;

import static jbiloan.Loanutil_MyBundle.translate;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 * Set the two loans to compare
 *
 * @author jean-blas imbert
 */
public class Loangui_CompareDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    /**
     * Modal result of this dialog, depending on user action
     */
    public static enum ModalResult {

        CANCEL, VALID
    }
    /**
     * First loan selector
     */
    private JComboBox firstCB = new JComboBox();
    /**
     * Second loan selector
     */
    private JComboBox secondCB = new JComboBox();
    /**
     * This doalog modal result depending on the user action
     */
    private ModalResult modal = ModalResult.CANCEL;

    /**
     * Constructor
     *
     * @param pOwner the frame owner (should be the main frame)
     * @param pModel the loan model
     */
    public Loangui_CompareDialog(final Window pOwner, final Loanmain_LoanModel pModel) {
        super(pOwner);
        setModalityType(ModalityType.APPLICATION_MODAL);
        init(pModel);
    }

    /**
     * Initialise the frame
     *
     * @param pModel the loan model
     */
    private void init(final Loanmain_LoanModel pModel) {
        //Initialize the combo boxes
        ListCellRenderer lRenderer = new ListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList pJlist, Object pObj, int pI, boolean pBool, boolean pBln) {
                if (pObj instanceof Loanmain_LoanItem) {
                    return new JLabel(((Loanmain_LoanItem) pObj).getName());
                }
                return new JLabel("");
            }
        };
        firstCB.setRenderer(lRenderer);
        secondCB.setRenderer(lRenderer);
        for (int lI = 0; lI < pModel.size(); lI++) {
            Loanmain_LoanItem lItem = pModel.get(lI);
            if (!pModel.isDiffed(lItem)) {
                firstCB.addItem(lItem);
                secondCB.addItem(lItem);
            }
        }
        //Initialises the frame
        setTitle(translate("compareFrame"));
        add(constructCenterPanel(), BorderLayout.CENTER);
        add(constructSouthPanel(), BorderLayout.SOUTH);
        pack();
        setSize(300, 150);
        setResizable(false);
        Loanutil_FrameUtils.screenCenter(this);
    }

    /**
     * Construct the center panel, which contains the combo boxes
     *
     * @return the filled panel
     */
    private JPanel constructCenterPanel() {
        JPanel lPanel = new JPanel();
        Loanutil_FrameUtils.buildPanelGroup(lPanel, new JComponent[][]{{new JLabel(translate("firstLoan")), firstCB},
            {new JLabel(translate("secondLoan")), secondCB}});
        return lPanel;
    }

    /**
     * Contruct the south panel with buttons
     *
     * @return the filled panel
     */
    private JPanel constructSouthPanel() {
        JButton lQuitBtn = Loanutil_JbiBtnFactory.CANCEL.create(new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent pEvent) {
                modal = ModalResult.CANCEL;
                Loangui_CompareDialog.this.setVisible(false);
            }
        });
        JButton lValidBtn = Loanutil_JbiBtnFactory.VALIDATE.create(new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent pEvent) {
                if (getFirst().equals(getSecond())) {
                    JOptionPane.showMessageDialog(null, translate("mustChooseDiffLoan"),
                            translate("warning"), JOptionPane.WARNING_MESSAGE);
                    return;
                }
                modal = ModalResult.VALID;
                Loangui_CompareDialog.this.setVisible(false);
            }
        });
        return Loanutil_FrameUtils.createSouthPanel(new JComponent[]{lQuitBtn, lValidBtn});
    }

    /**
     * Getter. Get the modal result field
     *
     * @return the modal result
     */
    public ModalResult getModalResult() {
        return modal;
    }

    /**
     * Get the first loan selected
     *
     * @return the first loan item
     */
    public Loanmain_LoanItem getFirst() {
        return (Loanmain_LoanItem) firstCB.getSelectedItem();
    }

    /**
     * Get the second loan selected
     *
     * @return the second loan item
     */
    public Loanmain_LoanItem getSecond() {
        return (Loanmain_LoanItem) secondCB.getSelectedItem();
    }
}
