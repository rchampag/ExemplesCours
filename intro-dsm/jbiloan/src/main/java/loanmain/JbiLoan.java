/*
 * Main entry point
 */
package loanmain;

import loangui.ChooseLanguageDialog;
import loangui.LoanFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import javax.swing.JOptionPane;
import loanutils.FormatterFactory;
import loanutils.MyBundle;

/**
 * The main class
 *
 * @author jean-blas Imbert
 */
public class JbiLoan extends WindowAdapter {

    /**
     * The main function
     *
     * @param pArgs the command line arguments
     */
    public static void main(String... pArgs) {
        try {
            new JbiLoan();
        } catch (Exception lEx) {
            JOptionPane.showMessageDialog(null, "Unable to initialise the application!\n" + lEx.getLocalizedMessage(),
                    "FATAL", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Constructor
     */
    public JbiLoan() {
        ChooseLanguageDialog lDialog = new ChooseLanguageDialog(null);
        lDialog.addWindowListener(JbiLoan.this);
        lDialog.setVisible(true);
        Locale.setDefault(lDialog.getUserLocale());
        MyBundle.init(lDialog.getUserLocale());
        FormatterFactory.setLocale(lDialog.getUserLocale());
        //Launches the main frame
        LoanFrame lFrame = new LoanFrame();
        lFrame.addWindowListener(JbiLoan.this);
        lFrame.setVisible(true);
    }

    /**
     * When this event is received, the application finishes
     *
     * @param pEvent the received window event
     */
    @Override
    public void windowClosed(WindowEvent pEvent) {
        if (pEvent.getWindow() instanceof LoanFrame || pEvent.getWindow() instanceof ChooseLanguageDialog) {
            System.exit(0);
        }
    }
}
