# Reflection
This project has my basic learnings about reflection.

- I have created a simple example of a bean manager, which searches for `@Provider` custom annotation and creates a supplier to initialize the bean.
- The store beans are lazy initialize and injected to the classes that require them with the help of `@Inject` custom annotation
- This project also has examples of how ORM works, It has a `@PrimaryKey` and `@Column` annotations to automatically map values queried from 
the database table to the matching column in the `columnname` field.
- It also has example of the MethodHandle classes introduced in java 8
