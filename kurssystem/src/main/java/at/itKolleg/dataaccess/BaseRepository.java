package at.itKolleg.dataaccess;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T, I> {
    //T = generischer Typ -> man kann mit allen beliebigen Referenztypen mit java arbeiten können
    Optional<T> insert(T entity);
    //I = ähnlich wie T -> weil vielleicht wollen wir nicht nur ein Long eingeben können
    Optional<T> getById (I id);
    List<T> getAll();
    Optional<T> update(T entity);
    void deleteById(I id);
}
