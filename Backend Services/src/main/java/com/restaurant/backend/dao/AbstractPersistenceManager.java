package com.restaurant.backend.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.lang.reflect.*;

public class AbstractPersistenceManager <F extends Serializable> {

    @PersistenceContext
    EntityManager entityManager;

    Class<F> persistentClass;
    public AbstractPersistenceManager() {

        Type t = getClass().getGenericSuperclass();
        Type arg;

        if (t instanceof ParameterizedType) {
            arg = ((ParameterizedType) t).getActualTypeArguments()[0];
        } else if (t instanceof Class) {
            arg = ((ParameterizedType) ((Class<?>) t).getGenericSuperclass()).getActualTypeArguments()[0];
        } else {
            throw new RuntimeException("Can not handle type construction for '" + getClass() + "'!");
        }

        if (arg instanceof Class) {
            this.persistentClass = (Class<F>) arg;
        } else if (arg instanceof ParameterizedType) {
            this.persistentClass = (Class<F>) ((ParameterizedType) arg).getRawType();
        } else {
            throw new RuntimeException("Problem dtermining generic class for '" + getClass() + "'! ");
        }

    }



}
