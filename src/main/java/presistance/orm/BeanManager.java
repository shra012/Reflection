package presistance.orm;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import presistance.annotations.Inject;
import presistance.annotations.Provider;
import presistance.providers.H2ConnectionProvider;

public class BeanManager {

	private final static BeanManager BEAN_MANAGER_INSTANCE = new BeanManager();
	private Map<Class<?>,Supplier<?>> registry = new HashMap<Class<?>, Supplier<?>>();
	private BeanManager() {
		List<Class<?>> classes = List.of(H2ConnectionProvider.class);
		for(Class<?> clss : classes) {
			Method[] methods = clss.getMethods();
			for(Method method : methods) {
				Provider provider = method.getAnnotation(Provider.class);
				if(provider!=null) {
					Class<?> returnType = method.getReturnType();
					Supplier<?> supplier = () -> {
						try {
							if(Modifier.isStatic(method.getModifiers())) {
								return method.invoke(null);
							}else {
								Object o = clss.getConstructor().newInstance();
								return method.invoke(o);
							}
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException | NoSuchMethodException | SecurityException e) {
							throw new RuntimeException(e);
						}
					};
					registry.put(returnType, supplier);
				}
			}
		}
	}

	public static BeanManager getInstance() {
		return BEAN_MANAGER_INSTANCE;
	}

	public <T> T getInstance(Class<T> clss) {
		try {
			T t = clss.getConstructor().newInstance();
			Field[] fields = clss.getDeclaredFields();
			for (Field field : fields) {
				Inject inject = field.getAnnotation(Inject.class);
				if(inject!=null) {
					Class<?> injectFieldType = field.getType();
					Supplier<?> supplier = registry.get(injectFieldType);
					Object objectToBeInjected = supplier.get();
					field.setAccessible(true);
					field.set(t, objectToBeInjected);
				}
			}
			return t;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}

}
