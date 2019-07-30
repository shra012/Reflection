package presistance.driver;

import presistance.orm.BeanManager;
import presistance.orm.EntityManager;
import presistance.orm.ManagedEntityManager;
import presistance.pojos.Person;

public class ReadingObjects {
	
	public static void main(String[] args) {
		BeanManager manager = BeanManager.getInstance();
		EntityManager<Person> entityManager = manager.getInstance(ManagedEntityManager.class);
		
		try {
			Person shravan = entityManager.read(Person.class, 1L);
			System.out.println(shravan);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
