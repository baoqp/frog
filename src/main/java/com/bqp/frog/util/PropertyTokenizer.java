package com.bqp.frog.util;

/**
 * 工具类
 *
 * @author Clinton Begin
 * @author ash
 */
public class PropertyTokenizer {
    private String name;
    private String children;

    public PropertyTokenizer(String fullname) {
        if (fullname != null) {
            int delim = fullname.indexOf('.');
            if (delim > -1) {
                name = fullname.substring(0, delim);
                children = fullname.substring(delim + 1);
            } else {
                name = Strings.emptyToNull(fullname);
                children = null;
            }
        }
    }


    public String getName() {
        return name;
    }


    public String getChildren() {
        return children;
    }

    public boolean hasCurrent() {
        return name != null;
    }

    public boolean hasNext() {
        return children != null;
    }

    public PropertyTokenizer next() {
        return new PropertyTokenizer(children);
    }

}