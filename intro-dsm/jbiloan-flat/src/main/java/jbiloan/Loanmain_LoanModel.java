/*
 * The underlying model of the application (collection of LoanItem).
 */
package jbiloan;

import static jbiloan.Loanutil_MyBundle.translate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The underlying model of the application (collection of LoanItem).
 *
 * @author jean-blas imbert
 */
public class Loanmain_LoanModel implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * The list of loan items
     */
    private List<Loanmain_LoanItem> data = null;
    /**
     * List of diffed items together with their links
     */
    private Map<Loanmain_LoanItem, ItemLinks> diffLinks = null;

    /**
     * Constructor
     */
    public Loanmain_LoanModel() {
        data = new ArrayList<Loanmain_LoanItem>();
        diffLinks = new HashMap<Loanmain_LoanItem, ItemLinks>();
    }

    /**
     * Get a LoanItem from its index
     *
     * @param pIndex the item index
     * @return the corresponding item or null if not found
     */
    public Loanmain_LoanItem get(final int pIndex) {
        return pIndex < data.size() ? data.get(pIndex) : null;
    }

    /**
     * Get the index of the passed item
     *
     * @param pItem the item to find
     * @return the item index if found
     */
    public int indexOf(final Loanmain_LoanItem pItem) {
        return data.indexOf(pItem);
    }

    /**
     * Add a LoanItem to the collection
     *
     * @param pItem the Loan item to add
     */
    public void add(final Loanmain_LoanItem pItem) {
        if (pItem.getName() == null || pItem.getName().trim().isEmpty()) {
            pItem.setName(translate("newloan") + data.size());
        }
        data.add(pItem);
    }

    /**
     * Add a diffed LoanItem to the collection
     *
     * @param pItem the Loan item to add
     * @param pItem1 the first Loan item to add
     * @param pItem2 the second Loan item to add
     */
    public void add(final Loanmain_LoanItem pItem, final Loanmain_LoanItem pItem1, final Loanmain_LoanItem pItem2) {
        add(pItem);
        ItemLinks lLink = new ItemLinks(pItem1, pItem2);
        diffLinks.put(pItem, lLink);
    }

    /**
     * Remove a LoanItem from the collection
     *
     * @param pItem the item to remove
     * @return true if the item is removed successfully, false otherwise
     */
    public boolean remove(final Loanmain_LoanItem pItem) {
        ItemLinks lLink = diffLinks.get(pItem);
        if (lLink != null) {
            diffLinks.remove(pItem);
        }
        return data.remove(pItem);
    }

    /**
     * Get the first diffed associated item (item = item1 - item2)
     *
     * @param pItem the item which is diffed
     * @return the first associated item (item1)
     */
    public Loanmain_LoanItem getFirst(final Loanmain_LoanItem pItem) {
        ItemLinks lLink = diffLinks.get(pItem);
        if (lLink != null) {
            return lLink.item1;
        }
        return null;
    }

    /**
     * Get the second diffed associated item (item = item1 - item2)
     *
     * @param pItem the item which is diffed
     * @return the second associated item (item2)
     */
    public Loanmain_LoanItem getSecond(final Loanmain_LoanItem pItem) {
        ItemLinks lLink = diffLinks.get(pItem);
        if (lLink != null) {
            return lLink.item2;
        }
        return null;
    }

    /**
     * Check if the item is a diffed item
     *
     * @return true if the associated item1 and item2 are found, false otherwise
     */
    public boolean isDiffed(final Loanmain_LoanItem pItem) {
        return diffLinks.get(pItem) != null;
    }

    /**
     * Save the model full content into csv format
     *
     * @return the csv formatted string
     */
    public String toCsv() {
        final String lNewLine = "\r\n";
        StringBuilder lBuilder = new StringBuilder();
        for (Loanmain_LoanItem lItem : data) {
            if (!isDiffed(lItem)) {
                lBuilder.append(Loanutil_FrameUtils.toCsv(lItem)).append(lNewLine);
            }
        }
        return lBuilder.toString();
    }

    /**
     * Get the size of the data
     *
     * @return the number of items
     */
    public int size() {
        return data.size();
    }

    /**
     * Class that stores the link between a diffed item and its targets (item = item1 - item2)
     */
    private static class ItemLinks implements Serializable {

        private static final long serialVersionUID = 1L;
        /**
         * First loan item
         */
        Loanmain_LoanItem item1;
        /**
         * Second loan item
         */
        Loanmain_LoanItem item2;

        /**
         * Constructor
         *
         * @param pItem1 first loan item
         * @param pItem2 second loan item
         */
        ItemLinks(final Loanmain_LoanItem pItem1, final Loanmain_LoanItem pItem2) {
            item1 = pItem1;
            item2 = pItem2;
        }
    }
}
