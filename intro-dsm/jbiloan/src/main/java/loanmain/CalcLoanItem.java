/*
 * Utility class that computes some Loanitem field values.
 * 
 * Note : all calculations are made from the following 3 equations :<BR>
 * Given A = amount to borrow (€), M = monthly fee (€), Mi = insurance monthly fee (€),
 * D = duration (year), Ti = insurance rate (%), Te = loan rate (%), we have : <BR>
 * <ol>
 * <li> Mt = M + Mi
 * <li> Mi = A * Ti / 12
 * <li> M = A * f(Te, D)
 * </ol>
 * where f(Te, D) = x / (1 - (1 / (1 + x)^(12 * D))) and x = Te / 12

 */
package loanmain;

import loansolver.OneParamFuncItf;
import loansolver.Solver;
import loansolver.SolverItf;

/**
 * Utility class that computes some Loanitem field values.
 *
 * @author jean-blas imbert
 */
public final class CalcLoanItem {

    /**
     * Utility class => private constructor
     */
    private CalcLoanItem() {
    }

    /**
     * Compute the approximative notary fee
     *
     * @param pItem the item loan
     * @return the notary fee
     */
    public static Double computeNotaryFee(final LoanItem pItem) {
        return pItem.getAmount() * 0.07D;
    }

    /**
     * Compute the f(Te, D). We need :
     * <ol>
     * <li>A duration (D)
     * <li>A loan rate (Te)
     * </ol>
     *
     * @param pItem the current loan item
     * @return the function f (see on top of this page)
     */
    private static Double calcF(final LoanItem pItem) {
        double lTaux = pItem.getTaux();
        double lDuration = pItem.getDuree();
        double lX = lTaux / 1200D;
        return lX / (1D - 1D / Math.pow(1D + lX, lDuration * 12D));
    }

    /**
     * Compute the loan rate. We need :
     * <ol>
     * <li>A duration (D)
     * <li>An amount (A)
     * <li>A monthly fee (Mt)
     * <li>An insurance rate (Ti)
     * </ol>
     *
     * @param pItem the current loan item
     * @return the loan rate in %
     */
    public static Double calcTauxBis(final LoanItem pItem) {
        return solveTaux(pItem.getMensualite() / pItem.getAmount() - pItem.getInsurance() / 1200D, pItem);
    }

    /**
     * Compute the loan effective rate. We need :
     * <ol>
     * <li>A duration (D)
     * <li>An amount (A)
     * <li>A monthly fee (Mt)
     * <li>An insurance rate (Ti)
     * </ol>
     *
     * @param pItem the current loan item
     * @return the loan effective rate in %
     */
    public static Double calcTauxEff(final LoanItem pItem) {
        Double lF = (pItem.getMensualite()  + pItem.getFrais() / 12D / pItem.getDuree()) / (pItem.getAmount());
        return solveTaux(lF, pItem);
    }

    /**
     * Solve the function rate for a given amount
     *
     * @param pC the amount to retrieve
     * @param pItem the current loan item
     * @return the root of the function for this amount
     */
    private static Double solveTaux(final Double pC, final LoanItem pItem) {
        final double lD = pItem.getDuree();
        final double lPuis = 1D + 1D / 12D / lD;
        class lFunc implements OneParamFuncItf<Double> {

            @Override
            public Double f(Double pX) {
                return Math.pow(pX, lPuis) - (pC + 1D) * pX + pC;
            }
        }
        double lZ0 = Math.pow((pC + 1D) / lPuis, 12D * lD);
        SolverItf<Double> lSolver = new Solver();
        try {
            Double lRoot = lSolver.solve(new lFunc(), (lZ0 + 1D) / 2D, lZ0 + 1D, 0.00001D);
            return 1200D * (Math.pow(lRoot, 1D / 12D / lD) - 1D);
        } catch (ArithmeticException lEx) {
            return 1000D;
        }
    }

    /**
     * Compute the monthly fee without insurance<BR>
     * We need :
     * <ol>
     * <li>An amount (A)
     * <li>A duration (D)
     * <li>A loan rate (Te)
     * </ol>
     *
     * @param pItem the current loan item
     * @return the monthly fee without insurance (M)
     */
    public static Double computeMensHorsAss(final LoanItem pItem) {
        if (pItem.getAmount().equals(0F) || pItem.getTaux().equals(0F) || pItem.getDuree().equals(0F)) {
            return null;
        }
        return pItem.getAmount() * calcF(pItem);
    }

    /**
     * Compute the monthly fee of the insurance only<BR>
     * We need :
     * <ol>
     * <li>An amount (A)
     * <li>A insurance rate (Ti)
     * </ol>
     *
     * @param pItem the current loan item
     * @return the monthly fee of the insurance only (Mi)
     */
    public static Double computeMensAss(final LoanItem pItem) {
        if (pItem.getAmount().equals(0F) || pItem.getInsurance().equals(0F)) {
            return null;
        }
        return pItem.getAmount() * pItem.getInsurance() / 1200D;
    }

    /**
     * Compute the amount<BR>
     * We need :
     * <ol>
     * <li>A monthly fee (Mt)
     * <li>A duration (D)
     * <li>A loan rate (Te)
     * <li>An insurance rate (Ti)
     * </ol>
     *
     * @param pItem the current loan item
     * @return the amount (A)
     */
    public static Double computeAmount(final LoanItem pItem) {
        if (pItem.getMensualite().equals(0F) || pItem.getTaux().equals(0F) || pItem.getDuree().equals(0F)) {
            return null;
        }
        return pItem.getMensualite() / (calcF(pItem) + pItem.getInsurance() / 1200D);
    }

    /**
     * Compute the duration<BR>
     * We need :
     * <ol>
     * <li>An amount (A)
     * <li>A monthly fee (Mt)
     * <li>A loan rate (Te)
     * <li>An insurance rate (Ti)
     * </ol>
     *
     * @param pItem the current loan item
     * @return the duration (D)
     */
    public static Double computeDuration(final LoanItem pItem) {
        if (pItem.getMensualite().equals(0F) || pItem.getTaux().equals(0F) || pItem.getAmount().equals(0F)) {
            return null;
        }
        double lMens = pItem.getMensualite() - (pItem.getAmount() * pItem.getInsurance() / 1200D);
        double lTaux = pItem.getTaux() / 1200D;
        return -Math.log(1D - pItem.getAmount() * lTaux / lMens) / Math.log(1D + lTaux) / 12D;
    }

    /**
     * Compute the loan rate knowing the monthly fee, amount and duration.
     *
     * @param pItem the current loan item
     * @return true if the item has changed, false otherwise
     */
    public static Double computeRate(final LoanItem pItem) {
        if (pItem.getMensualite().equals(0F) || pItem.getAmount().equals(0F) || pItem.getDuree().equals(0F)) {
            return null;
        }
        return calcTauxBis(pItem);
    }

    /**
     * Compute the difference between the first and the second loan item
     *
     * @param pItem1 the first loan item
     * @param pItem2 the second loan item
     * @return pItem1 - pItem2
     */
    public static LoanItem diff(final LoanItem pItem1, final LoanItem pItem2) {
        LoanItem lItem = new LoanItem();
        lItem.setName(pItem1.getName() + " - " + pItem2.getName());
        lItem.setAmount(pItem1.getAmount() - pItem2.getAmount());
        lItem.setDuree(pItem1.getDuree() - pItem2.getDuree());
        lItem.setFrais(pItem1.getFrais() - pItem2.getFrais());
        lItem.setInsurance(pItem1.getInsurance() - pItem2.getInsurance());
        lItem.setMensualite(pItem1.getMensualite() - pItem2.getMensualite());
        lItem.setSalary(pItem1.getSalary() - pItem2.getSalary());
        lItem.setTaux(pItem1.getTaux() - pItem2.getTaux());
        return lItem;
    }
}
