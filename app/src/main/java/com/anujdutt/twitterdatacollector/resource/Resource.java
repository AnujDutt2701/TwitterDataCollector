package com.anujdutt.twitterdatacollector.resource;

import androidx.annotation.Nullable;

public class Resource<T> {

    @Nullable
    public final Status status;
    @Nullable
    public final T data;
    @Nullable
    public final String errorMessage;

    private Resource(@Nullable Status status, @Nullable T data, @Nullable String errorMessage) {
        this.status = status;
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public static <T> Resource<T> error(String errorMessage, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, errorMessage);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, null);
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public enum Status {
        LOADING,
        SUCCESS,
        ERROR
    }
}
