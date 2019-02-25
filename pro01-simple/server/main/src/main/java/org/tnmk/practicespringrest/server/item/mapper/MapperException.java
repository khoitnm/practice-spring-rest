package org.tnmk.practicespringrest.server.item.mapper;

public class MapperException extends RuntimeException {
    public MapperException(String message, Throwable e) {
        super(message, e);
    }
}
