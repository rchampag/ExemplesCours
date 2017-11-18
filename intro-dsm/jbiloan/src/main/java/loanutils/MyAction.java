/*
 * A simple action that bears an accelerator key, a tooltip and an icon.
 */

package loanutils;

import java.awt.event.InputEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

/**
 * A simple action that bears an accelerator key, a tooltip and an icon
 * @author Jean-Blas Imbert
 */
public abstract class MyAction extends AbstractAction {

    /**
     * Constructor
     * @param pCaption caption of the action, and then of the menu item, button, etc.
     * @param pKeyStroke an keystroke accelerator of type "CTRL + keystroke".
     * @param pIconName base name of the icon (path is added automatically)
     * @param pToolTipText a user help tool tip (short description).
     */
    public MyAction(final String pCaption, int pKeyStroke, String pIconName, String pToolTipText) {
        this(pCaption, pIconName, pToolTipText);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(pKeyStroke, InputEvent.CTRL_DOWN_MASK));
    }

    /**
     * Constructor
     * @param pCaption caption of the action, and then of the menu item, button, etc.
     * @param pIconName base name of the icon (path is added automatically)
     * @param pToolTipText a user help tool tip (short description).
     */
    public MyAction(final String pCaption, String pIconName, String pToolTipText) {
        this(pCaption, pIconName);
        putValue(SHORT_DESCRIPTION, pToolTipText);
    }

    /**
     * Constructor
     * @param pCaption caption of the action, and then of the menu item, button, etc.
     * @param pIconName base name of the icon (path is added automatically)
     */
    public MyAction(final String pCaption, String pIconName) {
        super(pCaption, FrameUtils.createImageIcon(pIconName, null));
    }

}
