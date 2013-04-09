/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.projecttracker.report;

import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * DOCUMENT ME!
 *
 * @author   therter
 * @version  $Revision$, $Date$
 */
public class ArrayResourceBundle {

    //~ Instance fields --------------------------------------------------------

    ResourceBundle bundle;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ArrayResourceBundle object.
     *
     * @param  bundle  DOCUMENT ME!
     */
    public ArrayResourceBundle(final ResourceBundle bundle) {
        this.bundle = bundle;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param   key  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String[] getStringArray(final String key) {
        final StringTokenizer st = new StringTokenizer(bundle.getString(key), "',");
        final ArrayList<String> tmpList = new ArrayList<String>();

        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            token = token.trim();

            if (!token.equals("")) {
                tmpList.add(token);
            }
        }

        System.out.println("cat: " + key);
        for (final String tmp : tmpList) {
            System.out.println("val: " + tmp);
        }

        return tmpList.toArray(new String[tmpList.size()]);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   key  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getString(final String key) {
        return bundle.getString(key);
    }
}
