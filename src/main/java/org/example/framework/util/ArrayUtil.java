package org.example.framework.util;

import org.apache.commons.lang3.ArrayUtils;

public final class ArrayUtil {
    public static boolean isEmpty(Object[] arrays){
        return ArrayUtils.isEmpty(arrays);
    }

    public static boolean isNotEmpty(Object[] arrays){
        return  ArrayUtils.isNotEmpty(arrays);
    }

}
