package presistance;

public interface EntityManager<T> {
    void presist(T t);
    T read(Class<?> clazz,long primaryKey);
}
