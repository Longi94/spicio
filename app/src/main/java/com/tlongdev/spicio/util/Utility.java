package com.tlongdev.spicio.util;

/**
 * @author Long
 * @since 2016. 03. 05.
 */
public class Utility {

    public static String join(String[] strings, String delimiter) {
        StringBuilder sb = new StringBuilder();
        String sep = "";
        for(String s: strings) {
            sb.append(sep).append(s);
            sep = delimiter;
        }
        return sb.toString();
    }
}
