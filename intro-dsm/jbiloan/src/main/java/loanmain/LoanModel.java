/*
 * The underlying model of the application (collection of LoanItem).
 */
package loanmain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import loanutils.FrameUtils;
import static loanutils.MyBundle.translate;

/**
 * The underlying model of the application (collection of LoanItem).
 *
 * @author jean-blas imbert
 */
public class LoanModel implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * The list of loan items
     */
    private List<LoanItem> data = null;
    /**
     * List of diffed items together with their links
     */
    private Map<LoanItem, ItemLinks> diffLinks = null;

    /**
     * Constructor
     */
    public LoanModel() {
        data = new ArrayList<LoanItem>();
        diffLinks = new HashMap<LoanItem, ItemLinks>();
    }

    /**
     * Get a LoanItem from its index
     *
     * @param pIndex the item index
     * @return the corresponding item or null if not found
     */
    public LoanItem get(final int pIndex) {
        return pIndex < data.size() ? data.get(pIndex) : null;
    }

    /**
     * Get the index of the passed item
     *
     * @param pItem the item to find
     * @return the item index if found
     */
    public int indexOf(final LoanItem pItem) {
        return data.indexOf(pItem);
    }

    /**
     * Add a LoanItem to the collection
     *
     * @param pItem the Loan item to add
     */
    public void add(final LoanItem pItem) {
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
    public void add(final LoanItem pItem, final LoanItem pItem1, final LoanItem pItem2) {
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
    public boolean remove(final LoanItem pItem) {
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
    public LoanItem getFirst(final LoanItem pItem) {
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
    public LoanItem getSecond(final LoanItem pItem) {
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
    public boolean isDiffed(final LoanItem pItem) {
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
        for (LoanItem lItem : data) {
            if (!isDiffed(lItem)) {
                lBuilder.append(FrameUtils.toCsv(lItem)).append(lNewLine);
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
        LoanItem item1;
        /**
         * Second loan item
         */
        LoanItem item2;

        /**
         * Constructor
         *
         * @param pItem1 first loan item
         * @param pItem2 second loan item
         */
        ItemLinks(final LoanItem pItem1, final LoanItem pItem2) {
            item1 = pItem1;
            item2 = pItem2;
        }
    }
}
