package com.bqp.frog.util;


/**
 * @author ash
 */
public class Objects {

    public static boolean equal(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    public static int hashCode(Object... objects) {
        return java.util.Arrays.hashCode(objects);
    }

}
