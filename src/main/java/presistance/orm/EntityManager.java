package presistance.orm;

import java.sql.SQLException;

public interface EntityManager<T> {
    void persist(T t) throws SQLException, IllegalAccessException;
    T read(Class<T> clazz,long primaryKey) throws Exception;

    static <T> EntityManager<T> of(Class<T> clss){
        return new H2EntityManager<T>(clss);
    }

}
