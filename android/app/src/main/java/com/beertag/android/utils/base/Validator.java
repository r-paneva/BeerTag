package com.beertag.android.utils.base;

import com.beertag.android.utils.ValidationException;

public interface Validator<T> {

    void validateEntity(T entity) throws ValidationException;
}
