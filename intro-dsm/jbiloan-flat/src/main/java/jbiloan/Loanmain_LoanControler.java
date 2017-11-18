/*
 * Loan controler that makes the MVC model complete.<BR>
 * Link between the TabbedPane (result) and the Entry and Option panels (entries)
 * through the model (data).<BR>
 * When an Entry/Option panel component value changes
 * <ol>
 * <li>TabbedPane calls the controler (with LoanItem number and Entry/Option panel component changed value)
 * <li>Controler updates the model (the correct LoanItem number)
 * <li>Controler updates the Entry and Option panels (with the correct LoanItem number)
 * </ol>
 */
package jbiloan;

import static jbiloan.Loanmain_LoanItem.LoanType.DUREE;

/**
 * Loan controler that makes the MVC model complete.<BR>
 * Link between the TabbedPane (result) and the Entry and Option panels (entries) through the model (data).<BR>
 * When an Entry/Option panel component value changes
 * <ol>
 * <li>TabbedPane calls the controler (with LoanItem number and Entry/Option panel component changed value)
 * <li>Controler updates the model (the correct LoanItem number)
 * <li>Controler updates the Entry and Option panels (with the correct LoanItem number)
 * </ol>
 *
 * @author jean-blas imbert
 */
public class Loanmain_LoanControler {

    /**
     * The data model current item
     */
    private Loanmain_LoanItem item = null;
    /**
     * TRUE if the item is a diffed loan
     */
    private boolean mIsDiffed = false;

    /**
     * Set the loan type to the current item
     *
     * @param pType the loan type
     */
    public void setLoanType(Loanmain_LoanItem.LoanType pType) {
        if (item != null) {
            item.setLoanType(pType);
        }
    }

    /**
     * Setter. Set the isDiffed field
     *
     * @param pValue new value
     */
    public void setDiffed(final boolean pValue) {
        mIsDiffed = pValue;
    }

    /**
     * Getter.
     *
     * @return TRUE if the current item is a diffed one
     */
    public boolean isDiffed() {
        return mIsDiffed;
    }

    /**
     * Setter. Set the current item value according to the tabPane selected
     *
     * @param pItem the new item value
     */
    public void setCurrentItem(final Loanmain_LoanItem pItem) {
        item = pItem;
    }

    /**
     * get the current item
     *
     * @return the current item
     */
    public Loanmain_LoanItem getCurrentItem() {
        return item;
    }

    /**
     * Run the computation process depending on the loan type
     */
    private void compute() {
        Double lNewValue = null;
        switch (item.getLoanType()) {
            case DUREE:
                lNewValue = Loanmain_CalcLoanItem.computeDuration(item);
                if (lNewValue != null) {
                    item.setDuree(lNewValue.floatValue());
                }
                break;
            case MENSUALITE:
                lNewValue = Loanmain_CalcLoanItem.computeMensHorsAss(item);
                if (lNewValue != null) {
                    Double lMensAss = Loanmain_CalcLoanItem.computeMensAss(item);
                    Double lMens = lNewValue + (lMensAss == null ? 0D : lMensAss);
                    item.setMensualite(lMens.floatValue());
                }
                break;
            case MONTANT:
                lNewValue = Loanmain_CalcLoanItem.computeAmount(item);
                if (lNewValue != null) {
                    item.setAmount(lNewValue.floatValue());
                }
                break;
            case TAUX:
                lNewValue = Loanmain_CalcLoanItem.computeRate(item);
                if (lNewValue != null) {
                    item.setTaux(lNewValue.floatValue());
                }
                break;
        }
        if (lNewValue != null) {
            item.fireItemChanged();
        }
    }

    /**
     * Update the model with new values from the Entry panel
     *
     * @param pAmo new amount value
     * @param pTau new rate value
     * @param pMon new monthly fee value
     * @param pTim new duration value
     */
    public void updateEntry(final Float pAmo, final Float pTau, final Float pMon, final Float pTim) {
        item.setAmount(pAmo);
        item.setTaux(pTau);
        item.setDuree(pTim);
        item.setMensualite(pMon);
        compute();
    }

    /**
     * Update the model with new values from the Options panel
     *
     * @param pAfe agency fee new value
     * @param pAss insurance new value
     * @param pNot notary fee new value
     * @param pSal salary new value
     */
    public void updateOption(final Float pAfe, final Float pAss, final Float pNot, final Float pSal) {
        item.setFrais(pAfe);
        item.setInsurance(pAss);
        item.setSalary(pSal);
        compute();
    }
}
