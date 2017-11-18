/*
 * Components that are interested in LoanItem changes should implement this interface.
 */
package loanmain;

/**
 * Components that are interested in LoanItem changes should implement this interface.
 *
 * @author jean-blas imbert
 */
public interface ChangeListener {

    /**
     * Function should be fired when the item has changed
     *
     * @param pItem the changed item
     */
    void itemChanged(final LoanItem pItem);
}
