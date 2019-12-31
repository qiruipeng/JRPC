package com.study.rpc.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: qirp
 * @since: 2019/12/31 11:01
 **/
@Data
public class MsgResponse implements Serializable {
    private String messageId;
    private String error;
    private Object resultDesc;
}
