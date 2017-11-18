/*
 * Components that are interested in LoanItem differenciations should implement this interface.
 */
package jbiloan;

/**
 * Components that are interested in LoanItem differenciations should implement this interface.
 *
 * @author jean-blas imbert
 */
public interface Loanmain_DiffListener {

    /**
     * Compute the real difference between two loan items
     *
     * @param pItem1 the first loan item
     * @param pItem2 the second loan item
     */
    void itemDiffed(final Loanmain_LoanItem pItem1, final Loanmain_LoanItem pItem2);
}
