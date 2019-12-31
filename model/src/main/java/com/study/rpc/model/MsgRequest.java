package com.study.rpc.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 请求消息体封装
 * @author: qirp
 * @since: 2019/12/31 10:59
 **/
@Data
public class MsgRequest implements Serializable{
    private String messageId;
    private String className;
    private String methodName;
    private Class<?>[] typeParameters;
    private Object[] parametersVal;
}
