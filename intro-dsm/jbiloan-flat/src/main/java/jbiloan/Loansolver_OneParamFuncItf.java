/*
 * General interface for unidimensional functions
 */
package jbiloan;

/**
 * General interface for unidimensional functions
 */
public interface Loansolver_OneParamFuncItf<T> {

    /**
     * The unidimensional function prototype f(x)
     *
     * @param pX the function variable
     * @return the image of x through f
     */
    T f(T pX);
}
