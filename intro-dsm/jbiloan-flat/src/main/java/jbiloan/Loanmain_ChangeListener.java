/*
 * Components that are interested in LoanItem changes should implement this interface.
 */
package jbiloan;

/**
 * Components that are interested in LoanItem changes should implement this interface.
 *
 * @author jean-blas imbert
 */
public interface Loanmain_ChangeListener {

    /**
     * Function should be fired when the item has changed
     *
     * @param pItem the changed item
     */
    void itemChanged(final Loanmain_LoanItem pItem);
}
