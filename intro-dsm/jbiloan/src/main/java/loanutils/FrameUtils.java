/*
 * A utility package
 */
package loanutils;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.geom.Dimension2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.Writer;
import java.net.MalformedURLException;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import loanmain.CalcLoanItem;
import loanmain.LoanItem;
import static loanutils.MyBundle.translate;

/**
 * Utility class
 *
 * @author jimbert
 */
public abstract class FrameUtils {

    /**
     * Centers the frame on the screen.
     *
     * @param pWindow the window to be centered.
     */
    public static void screenCenter(Window pWindow) {
        Toolkit lTk = Toolkit.getDefaultToolkit();
        Dimension2D lDim = lTk.getScreenSize();
        Dimension2D lDimWindow = pWindow.getSize();
        int lTop = (int) (lDim.getHeight() - lDimWindow.getHeight()) / 2;
        int lLeft = (int) (lDim.getWidth() - lDimWindow.getWidth()) / 2;
        pWindow.setLocation(lLeft, lTop);
    }

    /**
     * Returns an ImageIcon, or null if the path was not valid.
     *
     * @param pPath the icon relative location
     * @param pDescription a description of the icon for accessibility context
     * @return the corresponding image
     */
    public static ImageIcon createImageIcon(String pPath, String pDescription) {
        String lPath = "loanimages/" + pPath;
        final String lErreur = "erreur";
        File lFile = new File(lPath);
        java.net.URL lImgURL = null;
        if (lFile.isAbsolute()) {
            try {
                lImgURL = lFile.toURI().toURL();
            } catch (MalformedURLException lEx) {
                JOptionPane.showMessageDialog(null, translate("badPathImage") + " : " + lPath + ". " + lEx.getLocalizedMessage(),
                        translate(lErreur), JOptionPane.ERROR_MESSAGE);
            }
        } else {
            lImgURL = Thread.currentThread().getContextClassLoader().getResource(lPath);
        }
        if (lImgURL != null) {
            return new ImageIcon(lImgURL, pDescription);
        } else {
            JOptionPane.showMessageDialog(null, translate("fileNotFound") + " : " + lPath,
                    translate(lErreur), JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Associates components and displays them in a pretty N columns using the Group layout
     *
     * @param pPanel the panel to fill
     * @param pComp the components [number of lines][number of columns]
     */
    public static void buildPanelGroup(JPanel pPanel, JComponent[][] pComp) {
        int lNbLin = pComp.length;
        int lNbCol = pComp[0].length;
        GroupLayout lLayout = new GroupLayout(pPanel);
        pPanel.setLayout(lLayout);
        // Turns on automatically adding gaps between components
        lLayout.setAutoCreateGaps(true);
        // Turns on automatically creating gaps between components that touch the edge of the container and the container.
        lLayout.setAutoCreateContainerGaps(true);
        // Creates a sequential group of lNbCol columns for the horizontal axis.
        GroupLayout.SequentialGroup lHGroup = lLayout.createSequentialGroup();
        lLayout.setHorizontalGroup(lHGroup);
        GroupLayout.ParallelGroup lColumns[] = new GroupLayout.ParallelGroup[lNbCol];
        for (int lCol = 0; lCol < lNbCol; lCol++) {
            lColumns[lCol] = lLayout.createParallelGroup();
            lHGroup.addGroup(lColumns[lCol]);
        }
        // Creates a sequential group for the vertical axis.
        GroupLayout.SequentialGroup lVGroup = lLayout.createSequentialGroup();
        lLayout.setVerticalGroup(lVGroup);
        //Associates the components
        for (int lLine = 0; lLine < lNbLin; lLine++) {
            GroupLayout.ParallelGroup lGrp = lLayout.createParallelGroup(GroupLayout.Alignment.BASELINE);
            for (int lCol = 0; lCol < lNbCol; lCol++) {
                lColumns[lCol].addComponent(pComp[lLine][lCol]);
                lGrp.addComponent(pComp[lLine][lCol]);
            }
            lVGroup.addGroup(lGrp);
        }
    }

    /**
     * Writer the values of a csv formatted string in a file
     *
     * @param pParent the component used as a parent for the file chooser (may be null)
     * @param pString the string to print
     * @throws IOException if the file can not be opened or written correctly
     */
    public static void printToCsv(final Component pParent, final String pString) throws IOException {
        final String lName = chooseFile(pParent, "csv", "Excel compatible file", false);
        if (lName != null) {
            //Write in the file
            Writer lWriter = null;
            try {
                lWriter = new FileWriter(lName);
                lWriter.write(pString);
                lWriter.flush();
            } finally {
                if (lWriter != null) {
                    lWriter.close();
                }
            }
        }
    }

    /**
     * Choose a file facility function
     *
     * @param pParent the FileChooser parent component, which may be null
     * @param pExt the FileChooser file extension
     * @param pExtMsg the FileFilter extension file message
     * @param pOpen true = opendialog, false = saveDialog
     * @return the chosen file name, or null if the operation has been cancelled
     */
    public static String chooseFile(final Component pParent, final String pExt, final String pExtMsg, boolean pOpen) {
        JFileChooser lChooser = new JFileChooser();
        FileNameExtensionFilter lFilter = new FileNameExtensionFilter(pExtMsg + " (." + pExt + ")", pExt);
        lChooser.setFileFilter(lFilter);
        int lReturnVal = pOpen ? lChooser.showOpenDialog(pParent) : lChooser.showSaveDialog(pParent);
        String lRes = null;
        if (lReturnVal == JFileChooser.APPROVE_OPTION) {
            lRes = lChooser.getSelectedFile().getAbsolutePath();
            if (!lRes.endsWith("." + pExt)) {
                lRes += "." + pExt;
            }
        }
        if (!pOpen && lRes != null) {
            //Ask for confirmation if the file already exists
            File lFile = new File(lRes);
            if (lFile.exists()
                    && JOptionPane.showConfirmDialog(pParent, translate("overloadSaveFile"), translate("warning"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.OK_OPTION) {
                lRes = null;
            }

        }
        return lRes;
    }

    /**
     * Serialize an object
     *
     * @param pParent the FileChooser parent component, which may be null
     * @param pObject the object to serialize
     */
    public static void serialize(final Component pParent, Serializable pObject) {
        ObjectOutputStream lOos = null;
        try {
            String lFilename = FrameUtils.chooseFile(pParent, "loan", "JBILoan compatible file", false);
            if (lFilename != null) {
                lOos = new ObjectOutputStream(new FileOutputStream(lFilename));
                lOos.writeObject(pObject);
            }
        } catch (Exception lEx) {
            JOptionPane.showMessageDialog(null, translate("UnableToSave") + " : " + lEx.getLocalizedMessage(),
                    translate("erreur"), JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (lOos != null) {
                    lOos.close();
                }
            } catch (IOException lEx) {
                ;
            }
        }
    }

    /**
     * De-serialize an object
     *
     * @param pParent the FileChooser parent component, which may be null
     */
    public static Object deserialize(final Component pParent) {
        ObjectInputStream lOis = null;
        try {
            String lFilename = FrameUtils.chooseFile(pParent, "loan", "JBILoan compatible file", true);
            if (lFilename != null) {
                lOis = new ObjectInputStream(new FileInputStream(lFilename));
                return lOis.readObject();
            }
        } catch (Exception lEx) {
            JOptionPane.showMessageDialog(null, translate("UnableToRead") + " : " + lEx.getLocalizedMessage(),
                    translate("erreur"), JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (lOis != null) {
                    lOis.close();
                }
            } catch (IOException lEx) {
                ;
            }
        }
        return null;
    }

    /**
     * Return the string value of this in .csv format
     *
     * @param pItem the item loan
     * @return the string value of this in .csv format
     */
    public static String toCsv(final LoanItem pItem) {
        final String lSep = ";";
        StringBuilder lBuilder = new StringBuilder();
        Double lMensHorsAss = CalcLoanItem.computeMensHorsAss(pItem);
        Double lMensAss = CalcLoanItem.computeMensAss(pItem);
        Double lNotFee = CalcLoanItem.computeNotaryFee(pItem);
        Double lTauxEff = CalcLoanItem.calcTauxEff(pItem);
        lBuilder.append(pItem.getName()).append(lSep).append(pItem.getAmount()).append(lSep).append(pItem.getTaux())
                .append(lSep).append(pItem.getMensualite()).append(lSep).append(lMensHorsAss.floatValue())
                .append(lSep).append(lMensAss.floatValue()).append(lSep).append(pItem.getDuree()).append(lSep)
                .append(pItem.getFrais()).append(lSep).append(pItem.getSalary()).append(lSep)
                .append(pItem.getInsurance()).append(lSep).append(lNotFee.floatValue()).append(lSep)
                .append(pItem.getLoanType()).append(lSep).append(lTauxEff.floatValue());
        return lBuilder.toString();
    }

    /**
     * Create a panel that lays the components from left to right, with right leading edge
     *
     * @param pComponents the components to add to the panel
     * @return the filled panel
     */
    public static JPanel createSouthPanel(final JComponent[] pComponents) {
        JPanel lPanel = new JPanel();
        lPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        lPanel.add(Box.createHorizontalGlue());
        for (JComponent lComp : pComponents) {
            lPanel.add(lComp);
            lPanel.add(Box.createHorizontalStrut(5));
        }
        return lPanel;
    }
}
