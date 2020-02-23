package fr.satysko.cernun.interfaces;

import java.util.List;

public interface GenericServices<T> {

    List<T> findAll();

    T find(int id);

    T create(T t);

    T update(T t);

    void delete(int id);

    void close();
}
