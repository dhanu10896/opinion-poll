package com.fullstackwebapp.opinionpoll.utils;

import java.util.Collection;
import java.util.Map;

public class ObjectUtils {

    public static boolean isUsable(Object obj) {
        if (obj != null) {
            if (obj instanceof String) {
                return ((String)obj).trim().length() > 0;
            } else if (obj instanceof Collection) {
                return ((Collection)obj).size() > 0;
            } else if (obj instanceof Map) {
                return ((Map)obj).size() > 0;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}
