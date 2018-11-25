package com.beertag.android.repositories.base;


import java.io.IOException;
import java.util.List;

public interface Repository<T> {
    List<T> getAll() throws IOException;

    T add(T item) throws IOException;

    T delete(T item) throws IOException;

    T update(T item) throws IOException;

    T getById(int id) throws IOException;

    T getByUsername(String username) throws IOException;

}
