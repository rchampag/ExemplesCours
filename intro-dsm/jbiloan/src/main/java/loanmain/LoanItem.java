/*
 * This class represents the item of a loan, with its entries, options and results.
 */
package loanmain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the item of a loan, with its entries, options and results.
 *
 * @author jean-blas imbert
 */
public final class LoanItem implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Suivant le type de loan, le calcul n'est pas le même.
     */
    public static enum LoanType implements Serializable {

        /**
         * Détermine la mensualité connaissant les 3 autres
         */
        MENSUALITE,
        /**
         * Détermine le taux connaissant les 3 autres
         */
        TAUX,
        /**
         * Détermine le montant connaissant les 3 autres
         */
        MONTANT,
        /**
         * Détermine la durée connaissant les 3 autres
         */
        DUREE
    }
    /**
     * The item loan type
     */
    private LoanType type = LoanType.MENSUALITE;
    /**
     * List of components that are interested in item change events
     */
    private transient List<ChangeListener> changeListeners = null;
    /**
     * List of components that are interested in item diff events
     */
    private transient List<DiffListener> diffListeners = null;
    /**
     * Name of this loan item
     */
    private String name = null;
    //Entries
    /**
     * The amount of the loan, in euros
     */
    private Float amount = 0F;
    /**
     * The rate of the loan, in percents
     */
    private Float taux = 0F;
    /**
     * The amount of one monthly payment, in euros
     */
    private Float mensualite = 0F;
    /**
     * The duration of the loan, in months
     */
    private Float duree = 0F;
    //Options
    /**
     * The bank fees, in euros
     */
    private Float frais = 0F;
    /**
     * The user monthly income, in euros
     */
    private Float salary = 0F;
    /**
     * The insurance of the loan, in percents
     */
    private Float insurance = 0F;

//Methods
    /**
     * Default constructor
     */
    public LoanItem() {
        changeListeners = new ArrayList<ChangeListener>();
    }

    /**
     * Clone this
     *
     * @return the clone of this
     */
    @Override
    public LoanItem clone() {
        LoanItem lClone = new LoanItem();
        lClone.setAmount(getAmount());
        lClone.setDuree(getDuree());
        lClone.setFrais(getFrais());
        lClone.setInsurance(getInsurance());
        lClone.setMensualite(getMensualite());
        lClone.setName(getName());
        lClone.setTaux(getTaux());
        lClone.setSalary(getSalary());
        lClone.setLoanType(getLoanType());
        return lClone;
    }

//Listeners
    /**
     * Add a new listener
     *
     * @param pListener the new listener
     */
    public void addChangeListener(final ChangeListener pListener) {
        if (changeListeners == null) {
            //This happens after a deserialization
            changeListeners = new ArrayList<ChangeListener>();
        }
        if (!changeListeners.contains(pListener)) {
            changeListeners.add(pListener);
        }
    }

    /**
     * Remove a listener
     *
     * @param pListener the listener to remove from the list
     */
    public void removeChangeListener(final ChangeListener pListener) {
        if (changeListeners.contains(pListener)) {
            changeListeners.remove(pListener);
        }
    }

    /**
     * Aware the listeners that this item has changed
     */
    public void fireItemChanged() {
        for (ChangeListener lListener : changeListeners) {
            lListener.itemChanged(this);
        }
    }

    /**
     * Add a new listener
     *
     * @param pListener the new listener
     */
    public void addDiffListener(final DiffListener pListener) {
        if (diffListeners == null) {
            //This happens after a deserialization
            diffListeners = new ArrayList<DiffListener>();
        }
        if (!diffListeners.contains(pListener)) {
            diffListeners.add(pListener);
        }
    }

    /**
     * Remove a listener
     *
     * @param pListener the listener to remove from the list
     */
    public void removeDiffListener(final DiffListener pListener) {
        if (diffListeners.contains(pListener)) {
            diffListeners.remove(pListener);
        }
    }

    /**
     * Aware the listeners that this item is diffed
     *
     * @param pItem1 the first loan item
     * @param pItem2 the second loan item
     */
    public void fireItemDiffed(final LoanItem pItem1, final LoanItem pItem2) {
        for (DiffListener lListener : diffListeners) {
            lListener.itemDiffed(pItem1, pItem2);
        }
    }

//getters and setters
    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float pAmount) {
        this.amount = pAmount;
    }

    public Float getTaux() {
        return taux;
    }

    public void setTaux(Float pTaux) {
        this.taux = pTaux;
    }

    public Float getMensualite() {
        return mensualite;
    }

    public void setMensualite(Float pMensualite) {
        this.mensualite = pMensualite;
    }

    public Float getDuree() {
        return duree;
    }

    public void setDuree(Float pDuree) {
        this.duree = pDuree;
    }

    public Float getFrais() {
        return frais;
    }

    public void setFrais(Float pFrais) {
        this.frais = pFrais;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float pSalary) {
        this.salary = pSalary;
    }

    public Float getInsurance() {
        return insurance;
    }

    public void setInsurance(Float pInsurance) {
        this.insurance = pInsurance;
    }

    public void setName(final String pName) {
        name = pName;
    }

    public String getName() {
        return name;
    }

    public LoanType getLoanType() {
        return type;
    }

    public void setLoanType(LoanType pType) {
        this.type = pType;
    }
}
