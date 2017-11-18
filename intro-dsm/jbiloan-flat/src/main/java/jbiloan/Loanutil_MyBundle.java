/*
 * Singleton that deals with a single resource bundle
 */
package jbiloan;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Singleton that deals with a single resource bundle
 *
 * @author Jean-Blas Imbert
 */
public abstract class Loanutil_MyBundle {

    /**
     * The resource bundle
     */
    private static ResourceBundle bundle;

    /**
     * Initialises the bundle : loads the corresponding properties file
     *
     * @exception MissingResourceException if no resource bundle for the specified base name can be found
     */
    public static void init() {
        init(Locale.UK);
    }

    /**
     * Initialises the bundle : loads the corresponding properties file
     *
     * @param pLocale the locale
     * @exception MissingResourceException if no resource bundle for the specified base name can be found
     */
    public static void init(Locale pLocale) {
        ResourceBundle.clearCache();
        String lName = "loanresources/mainBundle";
        bundle = ResourceBundle.getBundle(lName, pLocale);
        if (bundle == null) {
            throw new MissingResourceException(lName, "MyBundle.init", null);
        }
    }

    /**
     * Getter. Gets the value corresponding to a given key
     *
     * @param pKey the key whom value is wanted
     * @return the value corresponding to the key
     * @exception MissingResourceException if no object for the given key can be found
     */
    public static String translate(String pKey) {
        if (bundle == null) {
            init();
        }
        return bundle.getString(pKey);
    }
}
