package presistance.orm;

import java.sql.SQLException;

public interface EntityManager<T> {
    void persist(T t) throws SQLException, IllegalAccessException;
    T read(Class<?> clazz,long primaryKey);

    static <T> EntityManager<T> of(Class<T> clss){
        return new EntityManagerImpl<T>(clss);
    }

}
