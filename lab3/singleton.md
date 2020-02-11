# Singleton pattern
A singleton is usefull when we only require one instance of the object and that instance is used by many
different parts of the software.
One bad solution to this is to pass the object around as reference to where it is needed.
  
The singleton Pattern suggests that this object should have global visibility.
This is achieved by defining a static method of a class that returns a singleton.[1]
  

code example:
```Java
var s = "JavaScript syntax highlighting";
alert(s);
```

Ref
[1] Craig Larman. Applying UML and patterns. Third edition. Page 442-446. place of publish:One Lake Street Upper Saddle River,NJ 07458
Publisher Pearson Education, Inc; Publish year 2006.


