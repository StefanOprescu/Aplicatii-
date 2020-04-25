package me.repository;

import me.model.Entity;

public interface Repository <ID,E extends Entity<ID>> {

        /**
         *
         * @param elem
         * elem must be not null
         * @return null- if the given entity is saved
         * otherwise returns the entity (id already exists)
         * if the entity is not valid
         * @throws IllegalArgumentException
         * if the given entity is null. *
         */
        E save(E elem);


        /**
         *
         * @param elem
         * entity must not be null
         * @return null - if the entity is updated,
         * otherwise returns the entity - (e.g id does not
        exist).
         * @throws IllegalArgumentException
         * if the given entity is null.
         * if the entity is not valid.
         */
        E update(E elem);

        /**
         * removes the entity with the specified id
         * @param elem
         * id must be not null
         * @return the removed entity or null if there is no entity with the
        given id
         * @throws IllegalArgumentException
         * if the given id is null.
         */
        E delete(E elem) throws IllegalArgumentException;

        /**
         *
         * @param id -the id of the entity to be returned
         * id must not be null
         * @return the entity with the specified id
         * or null - if there is no entity with the given id
         * @throws IllegalArgumentException
         * if id is null.
         */
        E get(ID id);


        int size();


        /**
         *
         * @return all entities
         */
        Iterable<E> findAll();

}
