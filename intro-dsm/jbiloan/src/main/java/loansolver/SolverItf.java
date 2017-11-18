/*
 * Interface for all solver functions
 */
package loansolver;

/**
 * Interface for all solver functions
 *
 * @author jean-blas imbert
 */
public interface SolverItf<T> {

    /**
     * The function to solve
     *
     * @param pFunc the function to find the roots
     * @param pMin the lower bound
     * @param pMax the upper bound
     * @param pAcc the accuracy
     * @return the root
     */
    T solve(OneParamFuncItf<T> pFunc, final T pMin, final T pMax, final T pAcc);
}
