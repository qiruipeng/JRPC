package com.study.rpc.example.sever.impl;

import com.study.rpc.example.sever.SayIntface;

/**
 * @author: qirp
 * @since: 2019/12/31 14:02
 **/
public class SayService implements SayIntface {
    @Override
    public String says(String para) {
        System.out.println(String.format("传递的参数是:%s",para));
        return para;
    }
}
