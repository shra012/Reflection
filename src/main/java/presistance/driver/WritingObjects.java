package presistance.driver;

import presistance.orm.BeanManager;
import presistance.orm.EntityManager;
import presistance.orm.ManagedEntityManager;
import presistance.pojos.Person;

public class WritingObjects {
	public static void main(String[] args) throws Exception {
		// Removed as we have advanced to dependency Injection
		// We have to use BeanManager
		//EntityManager<Person> entityManager = EntityManager.of(Person.class);
		BeanManager manager = BeanManager.getInstance();
		EntityManager<Person> entityManager = manager.getInstance(ManagedEntityManager.class);
		Person venkat = new Person("Venkat",27);
		entityManager.persist(venkat);
		System.out.println(venkat);
	}
}
