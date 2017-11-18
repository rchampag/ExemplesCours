/*
 * Comparison frame.
 */
package loangui;

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
import loanmain.LoanItem;
import loanmain.LoanModel;
import loanutils.FrameUtils;
import loanutils.JbiBtnFactory;
import static loanutils.MyBundle.translate;

/**
 * Set the two loans to compare
 *
 * @author jean-blas imbert
 */
public class CompareDialog extends JDialog {

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
    public CompareDialog(final Window pOwner, final LoanModel pModel) {
        super(pOwner);
        setModalityType(ModalityType.APPLICATION_MODAL);
        init(pModel);
    }

    /**
     * Initialise the frame
     *
     * @param pModel the loan model
     */
    private void init(final LoanModel pModel) {
        //Initialize the combo boxes
        ListCellRenderer lRenderer = new ListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList pJlist, Object pObj, int pI, boolean pBool, boolean pBln) {
                if (pObj instanceof LoanItem) {
                    return new JLabel(((LoanItem) pObj).getName());
                }
                return new JLabel("");
            }
        };
        firstCB.setRenderer(lRenderer);
        secondCB.setRenderer(lRenderer);
        for (int lI = 0; lI < pModel.size(); lI++) {
            LoanItem lItem = pModel.get(lI);
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
        FrameUtils.screenCenter(this);
    }

    /**
     * Construct the center panel, which contains the combo boxes
     *
     * @return the filled panel
     */
    private JPanel constructCenterPanel() {
        JPanel lPanel = new JPanel();
        FrameUtils.buildPanelGroup(lPanel, new JComponent[][]{{new JLabel(translate("firstLoan")), firstCB},
            {new JLabel(translate("secondLoan")), secondCB}});
        return lPanel;
    }

    /**
     * Contruct the south panel with buttons
     *
     * @return the filled panel
     */
    private JPanel constructSouthPanel() {
        JButton lQuitBtn = JbiBtnFactory.CANCEL.create(new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent pEvent) {
                modal = ModalResult.CANCEL;
                CompareDialog.this.setVisible(false);
            }
        });
        JButton lValidBtn = JbiBtnFactory.VALIDATE.create(new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent pEvent) {
                if (getFirst().equals(getSecond())) {
                    JOptionPane.showMessageDialog(null, translate("mustChooseDiffLoan"),
                            translate("warning"), JOptionPane.WARNING_MESSAGE);
                    return;
                }
                modal = ModalResult.VALID;
                CompareDialog.this.setVisible(false);
            }
        });
        return FrameUtils.createSouthPanel(new JComponent[]{lQuitBtn, lValidBtn});
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
    public LoanItem getFirst() {
        return (LoanItem) firstCB.getSelectedItem();
    }

    /**
     * Get the second loan selected
     *
     * @return the second loan item
     */
    public LoanItem getSecond() {
        return (LoanItem) secondCB.getSelectedItem();
    }
}
