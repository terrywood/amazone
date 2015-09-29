package com.gt.bmf.util;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 15-4-1.
 */
public class NumberUtils {

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
}
