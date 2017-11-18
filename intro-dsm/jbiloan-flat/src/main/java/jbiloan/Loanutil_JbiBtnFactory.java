/*
 * Constructs all the buttons needed in the application.
 * The user must still code the action performed
 */
package jbiloan;

import static jbiloan.Loanutil_MyBundle.translate;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Constructs all the buttons needed in the application. The user must still code the action performed
 *
 * @author Jean-Blas Imbert
 */
public enum Loanutil_JbiBtnFactory {

    OPEN("open.png", "open", "openTooltip"),
    VALIDATE("open.png", "validate", "validateTooltip"),
    NEW("new.png", "new", "newTooltip"),
    CLOSE("retour.png", "close", "closeTooltip"),
    POUBELLE("poubelle.png", "poubelle", "poubelleTooltip"),
    PRINT("print.png", "print", "printTooltip"),
    SAUVER("sauver.png", "saveTo", "saveToTooltip"),
    COMPARE("comparer.png", "compare", "compareTooltip"),
    DUPLIQUER("dupliquer.png", "dupliquer", "dupliquerTooltip"),
    RENAME("rename.png", "rename", "renameTooltip"),
    SIMUL("simul.png", "simul", "simulTooltip"),
    CANCEL("retour.png", "cancel", "cancelTooltip");
    private String img;
    private String name;
    private String tooltip;

    /**
     * Constructor
     *
     * @param pImg the image name
     * @param pName the action name
     * @param pTooltip the component tooltip
     */
    Loanutil_JbiBtnFactory(String pImg, String pName, String pTooltip) {
        img = pImg;
        name = pName;
        tooltip = pTooltip;
    }

    /**
     * Creates an abstract button
     *
     * @param pAction the action underlying the button behaviour
     * @return the filled button
     */
    public JButton create(AbstractAction pAction) {
        pAction.putValue(Action.NAME, translate(name));
        pAction.putValue(Action.SHORT_DESCRIPTION, translate(tooltip));
        Object lObj = pAction.getValue(Action.SMALL_ICON);
        ImageIcon lIcon = lObj != null ? (ImageIcon) lObj : Loanutil_FrameUtils.createImageIcon(img, translate(tooltip));
        pAction.putValue(Action.SMALL_ICON, lIcon);
        pAction.putValue(Action.LARGE_ICON_KEY, lIcon);
        return new JButton(pAction);
    }
}