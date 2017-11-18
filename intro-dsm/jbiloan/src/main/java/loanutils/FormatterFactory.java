/*
 * Gets the formatters used in the application.
 */
package loanutils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Gets the formatters used in the application.
 *
 * @author Jean-Blas Imbert
 */
public final class FormatterFactory {

    /**
     * Default locale
     */
    private static Locale locale = Locale.FRENCH;
    /**
     * The decimal formatter which is associated to a locale and a currency (with no symbol!)
     */
    private static DecimalFormat lFormatter = null;

    static {
        resetFormatter();
    }

    /**
     * Setter. Sets the locale for all formatters
     *
     * @param pLocale the new locale value
     */
    public static void setLocale(Locale pLocale) {
        if (pLocale != null) {
            locale = pLocale;
            resetFormatter();
        }
    }

    /**
     * Reset the formatter according to an external change, like the currency for example
     */
    private static void resetFormatter() {
        lFormatter = (DecimalFormat) NumberFormat.getCurrencyInstance(locale);
        DecimalFormatSymbols lSymbols = lFormatter.getDecimalFormatSymbols();
        lSymbols.setCurrencySymbol("");
        lFormatter.setDecimalFormatSymbols(lSymbols);
    }

    /*
     * Private constructor because of singleton instance
     * Initialises the formatters
     */
    private FormatterFactory() {
    }

    /**
     * Format a float value as currency but without the currrency symbol.<br>
     *
     * @param pValue the value to format
     * @return the formatted value
     */
    public static String fmtCurrencyNoSymbol(final Float pValue) {
        String lStr = lFormatter.format(pValue);
        return lStr.trim();
    }

    /**
     * Format a float value as currency but without the currrency symbol.<br>
     * <ul>
     * <li>In FRENCH, we have 100 000,00 (with a white space)
     * <li>In US, we have 100,000.00 (with a ",")
     * </ul>
     * And we want finally : 100000.00 (no space nor ",")
     *
     * @param pValue the value to transform
     * @return the float compliant value
     */
    public static String currencyToFloat(final String pValue) {
        int lNb = 0;
        StringBuilder lBuilder = new StringBuilder();
        if (locale.equals(Locale.FRANCE)) {
            for (int lI = 0; lI < pValue.length(); lI++) {
                Character lC = pValue.charAt(lI);
                if (Character.isDigit(lC)) {
                    lBuilder.append(lC);
                } else if ((lC.equals(',') || lC.equals('.')) && lNb++ == 0) {
                    lBuilder.append(".");
                }
            }
        } else {
            for (int lI = 0; lI < pValue.length(); lI++) {
                Character lC = pValue.charAt(lI);
                if (Character.isDigit(lC) || (lC.equals('.') && lNb++ == 0)) {
                    lBuilder.append(lC);
                }
            }
        }
        return lBuilder.toString();
    }
}
