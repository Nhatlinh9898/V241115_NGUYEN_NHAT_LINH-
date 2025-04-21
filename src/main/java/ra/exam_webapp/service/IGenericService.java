package ra.exam_webapp.service;

import java.util.List;

public interface IGenericService<T, E, U, V> {
    List<T> findAll();
    T findById(E id);
    void create(U u);
    void update(V v, E id);
    void delete(E id);
}


