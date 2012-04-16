/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.cismet.projecttracker.report;

import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 *
 * @author therter
 */
public class ArrayResourceBundle {
    ResourceBundle bundle;

    public ArrayResourceBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public String[] getStringArray(String key) {
        StringTokenizer st = new StringTokenizer(bundle.getString(key), "',");
        ArrayList<String> tmpList = new ArrayList<String>();

        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            token = token.trim();

            if (!token.equals("")) {
                tmpList.add(token);
            }
        }

        System.out.println("cat: " + key);
        for ( String tmp : tmpList ) {
            System.out.println("val: " + tmp);
        }

        return tmpList.toArray(new String[tmpList.size()]);
    }


    public String getString(String key) {
        return bundle.getString(key);
    }
}
