/*
 * Main entry point
 */
package jbiloan;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import javax.swing.JOptionPane;

/**
 * The main class
 *
 * @author jean-blas Imbert
 */
public class Loanmain_JbiLoan extends WindowAdapter {

    /**
     * The main function
     *
     * @param pArgs the command line arguments
     */
    public static void main(String... pArgs) {
        try {
            new Loanmain_JbiLoan();
        } catch (Exception lEx) {
            JOptionPane.showMessageDialog(null, "Unable to initialise the application!\n" + lEx.getLocalizedMessage(),
                    "FATAL", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Constructor
     */
    public Loanmain_JbiLoan() {
        Loangui_ChooseLanguageDialog lDialog = new Loangui_ChooseLanguageDialog(null);
        lDialog.addWindowListener(Loanmain_JbiLoan.this);
        lDialog.setVisible(true);
        Locale.setDefault(lDialog.getUserLocale());
        Loanutil_MyBundle.init(lDialog.getUserLocale());
        Loanutil_FormatterFactory.setLocale(lDialog.getUserLocale());
        //Launches the main frame
        Loangui_LoanFrame lFrame = new Loangui_LoanFrame();
        lFrame.addWindowListener(Loanmain_JbiLoan.this);
        lFrame.setVisible(true);
    }

    /**
     * When this event is received, the application finishes
     *
     * @param pEvent the received window event
     */
    @Override
    public void windowClosed(WindowEvent pEvent) {
        if (pEvent.getWindow() instanceof Loangui_LoanFrame || pEvent.getWindow() instanceof Loangui_ChooseLanguageDialog) {
            System.exit(0);
        }
    }
}
