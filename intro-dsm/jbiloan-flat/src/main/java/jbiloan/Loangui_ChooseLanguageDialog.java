/*
 * Frame that displays an option buttons group to choose the language at the beginning of the application.
 */
package jbiloan;

import static jbiloan.Loanutil_MyBundle.translate;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.Locale;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Frame that displays an option buttons group to choose the language at the beginning of the application.
 *
 * @author jean-blas imbert
 */
public class Loangui_ChooseLanguageDialog extends JDialog {
    private static final long serialVersionUID = 1L;

    /**
     * English language button
     */
    private JRadioButton lEnBtn = null, lFrBtn = null;

    /**
     * Constructor
     *
     * @param pOwner the frame owner (should be the main frame)
     */
    public Loangui_ChooseLanguageDialog(Window pOwner) {
        super(pOwner);
        setModalityType(ModalityType.APPLICATION_MODAL);
        init();
    }

    /**
     * Initialise the frame
     */
    private void init() {
        setTitle(translate("applicationTitle"));
        add(constructCenterPanel(), BorderLayout.CENTER);
        add(constructSouthPanel(), BorderLayout.SOUTH);
        pack();
        setSize(220, 120);
        setResizable(false);
        Loanutil_FrameUtils.screenCenter(this);
    }

    /**
     * Get the locale according to the selected language radio button
     *
     * @return the chosen locale
     */
    public Locale getUserLocale() {
        Locale lLocale = Locale.FRANCE;
        if (lEnBtn.isSelected()) {
            lLocale = Locale.UK;
        }
        return lLocale;
    }

    /**
     * Construct the center panel, which contains the radio buttons
     *
     * @return the filled panel
     */
    private JPanel constructCenterPanel() {
        lEnBtn = new JRadioButton("English", true);
        lFrBtn = new JRadioButton("Fran" + "\u00E7" +"ais");
        ButtonGroup lGroup = new ButtonGroup();
        lGroup.add(lEnBtn);
        lGroup.add(lFrBtn);
        JPanel lPanel = new JPanel();
        lPanel.setLayout(new BoxLayout(lPanel, BoxLayout.Y_AXIS));
        lPanel.add(lEnBtn);
        lPanel.add(lFrBtn);
        return lPanel;
    }

    /**
     * Contruct the south panel with frame usual buttons (here cancel and validate buttons)
     *
     * @return the filled panel
     */
    private JPanel constructSouthPanel() {
        JButton lQuitBtn = Loanutil_JbiBtnFactory.CANCEL.create(new AbstractAction() {
            private static final long serialVersionUID = 1L;
            @Override
            public void actionPerformed(ActionEvent pEvent) {
                Loangui_ChooseLanguageDialog.this.dispose();
            }
        });
        JButton lValidBtn = Loanutil_JbiBtnFactory.VALIDATE.create(new AbstractAction() {
            private static final long serialVersionUID = 1L;
            @Override
            public void actionPerformed(ActionEvent pEvent) {
                Loangui_ChooseLanguageDialog.this.setVisible(false);
            }
        });
        return Loanutil_FrameUtils.createSouthPanel(new JComponent[]{lQuitBtn, lValidBtn});
    }
}
