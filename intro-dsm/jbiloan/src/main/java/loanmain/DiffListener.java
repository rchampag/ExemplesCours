/*
 * Components that are interested in LoanItem differenciations should implement this interface.
 */
package loanmain;

/**
 * Components that are interested in LoanItem differenciations should implement this interface.
 *
 * @author jean-blas imbert
 */
public interface DiffListener {

    /**
     * Compute the real difference between two loan items
     *
     * @param pItem1 the first loan item
     * @param pItem2 the second loan item
     */
    void itemDiffed(final LoanItem pItem1, final LoanItem pItem2);
}
