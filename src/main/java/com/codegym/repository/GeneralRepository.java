package com.codegym.repository;

import java.util.List;

public interface GeneralRepository<T> {
    List<T>findAll();

    void  save(T receptionist);

    void update(int id,T receptionist);

    void remove(int id);

    T findById(int id);

    T findByName(String name);
}
