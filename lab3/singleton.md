# Singleton pattern
A singleton is usefull when we only require one instance of the object and that instance is used by many
different parts of the software.
One bad solution to this is to pass the object around as reference to where it is needed.
  
The singleton Pattern suggests that this object should have global visibility.
This is achieved by defining a static method of a class that returns a singleton. In most cases eager initalization is prefered there are however cases such as when you need to read a configuration from file. In those cases lazy initalization is prefered [1]
  

code example:
```Java
public class main {
    public static void main( String []args ) {

        SingleTon mySingle = SingleTon.getInstance();
        System.out.println(mySingle.helloWorld());
    }
}
```
In file SingleTon.java
```Java
public class SingleTon {
    private static SingleTon instance = new SingleTon(); // Eager initalization.
    private SingleTon(){ } // Private constructor to prevent accidental reflection (creating a new singleton instance with new)
    public static SingleTon getInstance(){
        return instance;
    }
    public String helloWorld(){
        return "Hello World";
    }
}

```

Ref
[1] Craig Larman. Applying UML and patterns. Third edition. Page 442-446. place of publish: One Lake Street Upper Saddle River,NJ 07458 Publisher Pearson Education, Inc; Publish year 2006.


