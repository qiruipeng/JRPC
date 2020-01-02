package com.study.rpc.core.ThreadLocal;

import java.util.Map;

/**
 * 暂时用threadlocal
 * @author: qirp
 * @since: 2019/12/31 17:32
 **/
public class CommonThreadLocal {
    public static ThreadLocal<Map<String,Class<?>>> registryThreadLocal = new ThreadLocal<>();
}
