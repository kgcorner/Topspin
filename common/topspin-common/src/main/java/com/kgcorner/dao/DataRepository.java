package com.kgcorner.dao;


import java.io.Serializable;
import java.util.List;

/**
 * Contract for data repository
 * @param <T> @{@link Serializable}
 */

public interface DataRepository<T extends Serializable> {

    class Order {
        private String name;
        private boolean isAscending;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isAscending() {
            return isAscending;
        }

        public void setAscending(boolean ascending) {
            isAscending = ascending;
        }
    }

    /***
     * Persists given entity into database
     * @param model
     * @return the created entity
     */
    T create(T model);

    /***
     * Creates an entity if it doesn't exist updates otherwise
     * @param model
     * @return the updated entity
     */
    T update(T model);

    /***
     * removes the entity from database
     * @param modelId id (primary key) of the entity to be removed
     * @param model
     */
    void remove(String modelId, Class<T> model);

    /***
     * removes the entity from database
     * @param model Object to remove
     */
    void remove(T model);

    /**
     * removes a models by its primary key
     * @param key
     * @param modelName
     * @param keyName
     */
    void remove(Object key, String modelName, String keyName);


    /**
     * Get models which is identified by modelId
     * @param modelId
     * @param model
     * @return
     */
    T get(String modelId, Class<T> model);

    /**
     * Performs IN query
     * @param args list of comma separated values
     * @param argumentUnderCheck argument on which in clause will be appllied
     * @param model entity
     * @return
     */
    List<T> getIn(List<?> args, String argumentUnderCheck, Class<T> model);

    /**
     * Performs IN query
     * @param args list of comma separated values
     * @param conditions list of other conditions
     * @param argumentUnderCheck argument on which in clause will be appllied
     * @param model entity
     * @return
     */
    List<T> getIn(List<?> args, List<Operation> conditions, String argumentUnderCheck, Class<T> model);

    /***
     * returns list of models
     * @return
     */
    List<T> getAll(Class<T> model);

    /***
     * returns list of models
     * @param page page number
     * @param model
     * @param itemsPerPage number of items per page
     *
     * @return
     */
    List<T> getAll(int page, int itemsPerPage, Class<T> model);

    /***
     * Fetches entity using given conditions
     * @param conditions
     * @param model
     * @return return extracted entities
     */
    List<T> getAll(List<Operation> conditions, Class<T> model);

    /***
     * Fetches entity using given conditions
     * @param conditions
     * @param orders order list
     * @param model
     * @return return extracted entities
     */
    List<T> getAll(List<Operation> conditions, List<Order> orders, Class<T> model);

    /***
     * Fetches entity using given conditions
     * @param conditions
     * @param model
     * @return return extracted entities
     */
    T get(List<Operation> conditions, Class<T> model);

    /***
     * executes a procedure
     * @param procedure
     * @return output of the procedure
     */
    List<Object[]> getAll(Procedure procedure);

    /**
     * Returns paged List
     * @param conditions
     * @param page
     * @param itemPerPage
     * @param model
     * @return
     */
    CroppedCollection<List<T>> getCroppedList(List<Operation> conditions, int page, int itemPerPage, Class<T> model);

    /**
     * Returns paged List
     * @param model
     * @param conditions
     * @param page
     * @param itemPerPage
     * @param orders
     * @return
     */
    CroppedCollection<List<T>> getCroppedList(List<Operation> conditions, int page, int itemPerPage,
                                              List<Order> orders, Class<T> model);

    /**
     * Fetches entity using given conditions in given order
     * @param conditions
     * @param page
     * @param itemPerPage
     * @param orders
     * @param model
     * @return
     */
    List<T> getAll(List<Operation> conditions, int page, int itemPerPage, List<Order> orders, Class<T> model);

    /**
     * runs a select native query
     * @param query
     * @param params
     * @return
     */
    Object runSelectNativeQuery(String query, Object... params);

    /**
     * runs a select native query
     * @param query
     * @param params
     * @return
     */
    int runUpdateNativeQuery(String query, Object... params);

    /**
     * returns count of the items for given conditions
     * @param operationList
     * @param entity
     */
    long getCount(List<Operation> operationList, Class<T> entity);

    /**
     * Runs given stroed procedure
     * @param procedureName
     * @param operation params
     * @return result
     */
    Object[] callProc(String procedureName, List<Operation> operation);

    /**
     * Returns result of group by operation on a table with give condition and group by params
     * @param groupBy
     * @param conditions
     * @param model
     * @return
     */
    List<Object[]> get(List<String> groupBy, List<Operation> conditions, Class<T> model);
}
