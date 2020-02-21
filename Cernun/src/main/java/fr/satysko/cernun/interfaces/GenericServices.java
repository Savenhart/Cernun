package fr.satysko.cernun.interfaces;

import java.util.List;

public interface GenericServices<T> {
    List<T> findAll();

    T find(int id);

    T create(T v);

    T update(T voiture);

    void delete(int id);
}
