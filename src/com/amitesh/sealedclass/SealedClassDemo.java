package com.amitesh.sealedclass;

import java.util.Arrays;
import java.util.List;

public class SealedClassDemo {

  public static void main(String[] args) {
    System.out.println("SealedClass isSealed: " + SealedClass.class.isSealed());
    System.out.println("Parent isSealed: " + Parent.class.isSealed());
    System.out.println("Child isSealed: " + Child.class.isSealed());
    System.out.println("Grandson isSealed: " + Grandson.class.isSealed());
    System.out.println("Sibling isSealed: " + Sibling.class.isSealed());
    System.out.println("Cousin isSealed: " + Cousin.class.isSealed());
    System.out.println("Root.A isSealed: " + Root.A.class.isSealed());
    System.out.println("Root.B isSealed: " + Root.B.class.isSealed());
    System.out.println("Root.C isSealed: " + Root.C.class.isSealed());
    System.out.println("D isSealed: " + D.class.isSealed());
    System.out.println("E isSealed: " + E.class.isSealed());

    System.out.println("\nPermitted classes of ");
    permittedClasses(SealedClass.class);
  }

  private static void permittedClasses(Class<?> clazz) {
    System.out.print(clazz.getName() + ": ");
    Class<?>[] permittedSubclasses = clazz.getPermittedSubclasses();
    if (null != permittedSubclasses && permittedSubclasses.length > 0) {
      List<Class<?>> classList = Arrays.asList(clazz.getPermittedSubclasses());
      System.out.println(classList);
      classList.forEach(SealedClassDemo::permittedClasses);
    } else {
      System.out.println();
    }
  }
}

@SuppressWarnings("unused")
sealed class SealedClass permits Parent, Child, Sibling {

  D d = new D();

  Root.A a = new Root.A();

  /* Compilation error. Root is not an enclosing class. Make B static
      i.e. Since B is not static it requires an instance of the outer
      class which is abstract so need to make B as Static
   */
  //Root.B b = new Root().new B();
  Root.C c = new Root.C();


}

non-sealed class Child extends SealedClass {

}

// Non Sealed class break the chain and can be extended by any class
class Grandson extends Child {

}

// Final class can't extend anymore
final class Parent extends SealedClass {

}

// Child Sealed class can be extended by permitted classes only
sealed class Sibling extends SealedClass permits Cousin {

}

sealed class Cousin extends Sibling permits Nephew {

}

final class Nephew extends Cousin {

}


// Another style using Inheritors and not Sealed keyword
abstract class Root {

  static final class A extends Root {

  }

  static sealed class C extends Root permits E {

  }

  final class B extends Root {

  }
}

final class D extends Root {

}

final class E extends Root.C {

}