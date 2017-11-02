package com.zhtx.goodsapi.common;

public class TransactionalRuntimeException extends RuntimeException{
public TransactionalRuntimeException(String msg) {
	super(msg);
}
public TransactionalRuntimeException() {
	super();
}
}
