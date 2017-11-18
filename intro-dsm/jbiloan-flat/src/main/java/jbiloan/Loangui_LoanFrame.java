/*
 * The main application frame.
 */
package jbiloan;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import static jbiloan.Loanutil_MyBundle.translate;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The main application frame.
 *
 * @author jean-blas imbert
 */
public class Loangui_LoanFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    /**
     * Close abstract button
     */
    private AbstractButton closeBtn = null;
    /**
     * Dupliquer abstract button
     */
    private AbstractButton cloneBtn = null;
    /**
     * Delete abstract button
     */
    private AbstractButton deleteBtn = null;
    /**
     * Save abstract button
     */
    private AbstractButton cvsBtn = null;
    /**
     * Compare abstract button
     */
    private AbstractButton compareBtn = null;
    /**
     * New abstract button
     */
    private AbstractButton newBtn = null;
    /**
     * Open abstract button
     */
    private AbstractButton openBtn = null;
    /**
     * Save abstract button
     */
    private AbstractButton saveBtn = null;
    /**
     * Simulation abstract button
     */
    private AbstractButton simulBtn = null;
    /**
     * The tabbed pane that displays the results
     */
    private JTabbedPane tabPane = new JTabbedPane();
    /**
     * The entry components panel
     */
    private Loangui_EntryPanel entryPanel = null;
    /**
     * The option components panel
     */
    private Loangui_OptionPanel optionPanel = null;
    /**
     * The data model
     */
    private Loanmain_LoanModel model = new Loanmain_LoanModel();
    /**
     * The loan controler
     */
    private Loanmain_LoanControler controler = new Loanmain_LoanControler();

    /**
     * Constructor
     */
    public Loangui_LoanFrame() {
        entryPanel = new Loangui_EntryPanel(controler);
        optionPanel = new Loangui_OptionPanel(controler);
        init();
    }

    /**
     * Initialise the frame
     */
    private void init() {
        setTitle(translate("applicationTitle"));
        ImageIcon lIcon = Loanutil_FrameUtils.createImageIcon("emprunt.png", null);
        setIconImage(lIcon.getImage());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent pEvent) {
                close();
            }
        });
        createAbstractBtn();
        //
        tabPane.addMouseListener(createPopupListener());
        tabPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent pEvent) {
                JTabbedPane lPane = (JTabbedPane) pEvent.getSource();
                int lIndex = lPane.getSelectedIndex();
                if (lIndex > -1) {
                    Loanmain_LoanItem lItem = model.get(lIndex);
                    controler.setCurrentItem(lItem);
                    boolean lIsDiffed = model.isDiffed(lItem);
                    cloneBtn.getAction().setEnabled(!lIsDiffed);
                    simulBtn.getAction().setEnabled(!lIsDiffed);
                    controler.setDiffed(lIsDiffed);
                    entryPanel.itemChanged(lItem);
                    optionPanel.itemChanged(lItem);
                    if (lIsDiffed) {
                        ((Loangui_TabbedPanel) tabPane.getSelectedComponent()).itemDiffed(model.getFirst(lItem), model.getSecond(lItem));
                    } else {
                        ((Loangui_TabbedPanel) tabPane.getSelectedComponent()).itemChanged(lItem);
                    }
                }
            }
        });
        //Creates the "File" menu
        JMenu lMenuFile = new JMenu(translate("file"));
        lMenuFile.add(new JMenuItem(newBtn.getAction()));
        lMenuFile.add(new JMenuItem(cloneBtn.getAction()));
        lMenuFile.add(new JSeparator());
        lMenuFile.add(new JMenuItem(openBtn.getAction()));
        lMenuFile.add(new JSeparator());
        lMenuFile.add(new JMenuItem(cvsBtn.getAction()));
        lMenuFile.add(new JMenuItem(saveBtn.getAction()));
        lMenuFile.add(new JMenuItem(deleteBtn.getAction()));
        lMenuFile.add(new JSeparator());
        lMenuFile.add(new JMenuItem(closeBtn.getAction()));
        //Creates the "Edit" menu
        JMenu lMenuEdit = new JMenu(translate("edit"));
        lMenuEdit.add(new JMenuItem(compareBtn.getAction()));
        lMenuEdit.add(new JSeparator());
        lMenuEdit.add(new JMenuItem(simulBtn.getAction()));
        //Creates the menubar
        JMenuBar lMenuBar = new JMenuBar();
        lMenuBar.add(lMenuFile);
        lMenuBar.add(lMenuEdit);
        setJMenuBar(lMenuBar);
        //Fill the tabbed pane with the first component
        newBtn.doClick();
        entryPanel.synchronizeCBandTF();
        //Add the components
        getContentPane().add(buildCenterPanel(), BorderLayout.CENTER);
        getContentPane().add(buildButtonPanel(), BorderLayout.SOUTH);
        //Initialises the frame
        pack();
        setSize(650, 450);
        setResizable(false);
        Loanutil_FrameUtils.screenCenter(this);
    }

    /**
     * This function is called when the user closes the main window.
     */
    public final void close() {
        if (JOptionPane.showConfirmDialog(null, translate("terminerAppli") + "?", translate("terminate"),
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            this.dispose();
        }
    }

    /**
     * Construct the center panel
     *
     * @return the filled panel
     */
    private JPanel buildCenterPanel() {
        //Build the entries and options panel
        JPanel lEOPanel = new JPanel();
        lEOPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        lEOPanel.setLayout(new GridLayout(1, 2, 20, 5));
        lEOPanel.add(entryPanel);
        lEOPanel.add(optionPanel);
        //Build the center panel
        JPanel lPanel = new JPanel();
        lPanel.setLayout(new BoxLayout(lPanel, BoxLayout.Y_AXIS));
        lPanel.add(lEOPanel);
        lPanel.add(Box.createVerticalStrut(20));
        lPanel.add(tabPane);
        lPanel.add(Box.createVerticalGlue());
        return lPanel;
    }

    /**
     * Constructs the south panel with usual buttons
     *
     * @return the filled panel
     */
    private JPanel buildButtonPanel() {
        return Loanutil_FrameUtils.createSouthPanel(new JComponent[]{simulBtn, compareBtn, closeBtn});
    }

    /**
     * Constructs and implements the actions for all buttons
     */
    private void createAbstractBtn() {
        closeBtn = Loanutil_JbiBtnFactory.CLOSE.create(new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent pEvent) {
                close();
            }
        });
        cloneBtn = Loanutil_JbiBtnFactory.DUPLIQUER.create(new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent pEvent) {
                addItem(controler.getCurrentItem().clone());
            }
        });
        deleteBtn = Loanutil_JbiBtnFactory.POUBELLE.create(new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent pEvent) {
                if (tabPane.getTabCount() > 1 && JOptionPane.showConfirmDialog(null, translate("deleteCurrentItem") + "?", translate("Delete"),
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    removeCurrentItem();
                }
            }
        });
        newBtn = Loanutil_JbiBtnFactory.NEW.create(new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent pEvent) {
                addItem(new Loanmain_LoanItem());
            }
        });
        compareBtn = Loanutil_JbiBtnFactory.COMPARE.create(new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent pEvent) {
                Loangui_CompareDialog lDlg = new Loangui_CompareDialog(Loangui_LoanFrame.this, model);
                lDlg.setVisible(true);
                if (lDlg.getModalResult() == Loangui_CompareDialog.ModalResult.VALID) {
                    Loanmain_LoanItem lItem = Loanmain_CalcLoanItem.diff(lDlg.getFirst(), lDlg.getSecond());
                    addItem(lItem, lDlg.getFirst(), lDlg.getSecond());
                }
            }
        });
        cvsBtn = Loanutil_JbiBtnFactory.PRINT.create(new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent pEvent) {
                try {
                    Loanutil_FrameUtils.printToCsv(Loangui_LoanFrame.this, model.toCsv());
                } catch (IOException lEx) {
                    JOptionPane.showMessageDialog(null, translate("UnableToCsv") + lEx.getLocalizedMessage(),
                            translate("erreur"), JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        saveBtn = Loanutil_JbiBtnFactory.SAUVER.create(new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent pEvent) {
                Loanutil_FrameUtils.serialize(Loangui_LoanFrame.this, model);
            }
        });
        openBtn = Loanutil_JbiBtnFactory.OPEN.create(new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent pEvent) {
                Object lObj = Loanutil_FrameUtils.deserialize(Loangui_LoanFrame.this);
                if (lObj != null) {
                    for (int lI = 0; lI < model.size(); lI++) {
                        removeCurrentItem();
                    }
                    Loanmain_LoanModel lModel = (Loanmain_LoanModel) lObj;
                    for (int lI = 0; lI < lModel.size(); lI++) {
                        Loanmain_LoanItem lItem = lModel.get(lI);
                        if (lModel.isDiffed(lItem)) {
                            addItem(lItem, lModel.getFirst(lItem), lModel.getSecond(lItem));
                        } else {
                            addItem(lItem);
                        }

                    }
                }
            }
        });
        simulBtn = Loanutil_JbiBtnFactory.SIMUL.create(new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent pEvent) {
                Loangui_SimulationDialog lDlg = new Loangui_SimulationDialog(Loangui_LoanFrame.this, controler.getCurrentItem());
                lDlg.setVisible(true);
            }
        });
    }

    /**
     * Remove the current item
     */
    private void removeCurrentItem() {
        Loanmain_LoanItem lItem = controler.getCurrentItem();
        tabPane.remove(tabPane.getSelectedIndex());
        model.remove(lItem);
    }

    /**
     * Add an item to the tabbed pane
     *
     * @param pItem the item to add
     */
    private void addItem(final Loanmain_LoanItem pItem) {
        int lNb = tabPane.getTabCount();
        pItem.addChangeListener(entryPanel);
        pItem.addChangeListener(optionPanel);
        Loangui_TabbedPanel lTabbedPanel = new Loangui_TabbedPanel();
        pItem.addChangeListener(lTabbedPanel);
        if (pItem.getName() == null) {
            pItem.setName(String.valueOf(lNb + 1));
        }
        model.add(pItem);
        Icon lIcon = Loanutil_FrameUtils.createImageIcon("emprunt.png", "");
        tabPane.addTab(pItem.getName(), lIcon, lTabbedPanel, translate("tabTooltip"));
    }

    /**
     * Add a diffed item to the tabbed pane
     *
     * @param pItem the diffed item to add (pItem = pItem1 - pItem2)
     * @param pItem1 the first item
     * @param pItem2 the second item
     */
    private void addItem(final Loanmain_LoanItem pItem, final Loanmain_LoanItem pItem1, final Loanmain_LoanItem pItem2) {
        pItem.addChangeListener(entryPanel);
        pItem.addChangeListener(optionPanel);
        Loangui_TabbedPanel lTabbedPanel = new Loangui_TabbedPanel();
        pItem.addDiffListener(lTabbedPanel);
        model.add(pItem, pItem1, pItem2);
        Icon lIcon = Loanutil_FrameUtils.createImageIcon("emprunt.png", "");
        tabPane.addTab(pItem.getName(), lIcon, lTabbedPanel, translate("tabTooltip"));
    }

    /**
     * Create a popup listener to add to a component (here we add it to the JTabbedPane)
     */
    private PopupListener createPopupListener() {
        JPopupMenu lPopup = new JPopupMenu();
        AbstractButton lRenameItem = Loanutil_JbiBtnFactory.RENAME.create(new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent pEvent) {
                String lNewName = JOptionPane.showInputDialog(translate("InputValue"));
                if (lNewName != null) {
                    int lIndex = tabPane.getSelectedIndex();
                    model.get(lIndex).setName(lNewName);
                    tabPane.setTitleAt(lIndex, lNewName);
                }
            }
        });
        lPopup.add(new JMenuItem(lRenameItem.getAction()));
        lPopup.add(new JSeparator());
        lPopup.add(new JMenuItem(cloneBtn.getAction()));
        lPopup.add(new JSeparator());
        lPopup.add(new JMenuItem(deleteBtn.getAction()));
        return new PopupListener(lPopup);
    }
    //*************************************************************************************************

    /**
     * Simple popup listener class
     */
    private static class PopupListener extends MouseAdapter {

        /**
         * The popup menu to trigger
         */
        JPopupMenu popup = null;

        /**
         * Constructor
         *
         * @param pPopup the popup to trigger
         */
        PopupListener(final JPopupMenu pPopup) {
            popup = pPopup;
        }

        @Override
        public void mousePressed(MouseEvent pEvent) {
            showPopup(pEvent);
        }

        @Override
        public void mouseReleased(MouseEvent pEvent) {
            showPopup(pEvent);
        }

        /**
         * Display the popup menu
         *
         * @param pEvent the trigger event
         */
        private void showPopup(MouseEvent pEvent) {
            if (pEvent.isPopupTrigger()) {
                popup.show(pEvent.getComponent(), pEvent.getX(), pEvent.getY());
            }
        }
    }
}
