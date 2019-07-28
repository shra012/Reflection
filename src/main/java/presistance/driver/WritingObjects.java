package presistance.driver;

import presistance.orm.EntityManager;
import presistance.pojos.Person;

public class WritingObjects {
    public static void main(String[] args) throws Exception {
        EntityManager<Person> entityManager = EntityManager.of(Person.class);
        
		/*
		 * Person shravan = new Person("Shravan",25);
		 * Person malavika = new
		 * Person("Malavika",24);
		 * entityManager.persist(shravan);
		 * entityManager.persist(malavika);
		 * 
		 * System.out.println(shravan);
		 * System.out.println(malavika);
		 */
        Person gokul = entityManager.read(Person.class, 3);
        
        System.out.println(gokul);
        
    }
}
