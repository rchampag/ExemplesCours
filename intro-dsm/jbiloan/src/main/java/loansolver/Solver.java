/*
 * A general solver root finding of functions of type f(x) = 0.
 */
package loansolver;

/**
 * A general solver root finding of functions of type f(x) = 0.
 *
 * @author jean-blas imbert
 */
public class Solver implements SolverItf<Double> {

    /**
     * using bisection, find the root of a function f known to lie between x1 and x2. The root will be refined until its
     * accuracy is +/-xacc.
     *
     * @param pFunc the function to find the root of
     * @param pMin the lower bound
     * @param pMax the upper bound
     * @param pAcc the wanted accuracy
     * @return the root value
     */
    private Double bissect(final OneParamFuncItf<Double> pFunc, final Double pMin, final Double pMax, final Double pAcc) {
        final int lJMax = 40;
        int lJ;
        double lDx, lFunc, lFMid, lXMid, lRtb;
        lFunc = pFunc.f(pMin);
        lFMid = pFunc.f(pMax);
        if (lFunc * lFMid >= 0D) {
            throw new ArithmeticException("Root must be bracketed for bisection in bissect");
        }
        if (lFunc < 0D) {
            lRtb = pMin;
            lDx = pMax - pMin;
        } else {
            lRtb = pMax;
            lDx = pMin - pMax;
        }
        for (lJ = 1; lJ < lJMax; lJ++) {
            lDx *= 0.5;
            lXMid = lRtb + lDx;
            lFMid = pFunc.f(lXMid);
            if (lFMid <= 0D) {
                lRtb = lXMid;
            }
            if (Math.abs(lDx) < pAcc || lFMid == 0D) {
                return lRtb;
            }
        }
        throw new ArithmeticException("Too many bisections in bissect");
    }

    @Override
    public Double solve(OneParamFuncItf<Double> pF, Double pXMin, Double pXMax, Double pAcc) {
        return bissect(pF, pXMin, pXMax, pAcc);
    }
}
