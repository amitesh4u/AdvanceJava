package com.amitesh.switchexpressions;

enum DAYS {
  MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

public class SwitchExpressions {

  public static void main(String[] args) {
    DAYS dayEnum = DAYS.SUNDAY;
    testSwitchEnum(dayEnum); // Java 8
    String day = "Friday";
    testSwitchReturnByYieldKeyword(
        day); // Java 13 preview, Java 14. Should be used with Colon expressions
    testSwitchReturnByArrow(day); // Java 12 preview, Java 14
    testSwitchMultipleCaseLabels(day); // Java 12 preview, Java 14  PREFERRED STYLE

    testSwitchPatternMatching(5); // Java 17 preview
    testSwitchPatternMatching("A"); // Java 17 preview
    Employee empDev = new Employee("Tom", "DEV");
    testSwitchPatternMatching(empDev); // Java 17 preview
    testSwitchPatternMatching(new Test(20)); // Java 17 preview
    testSwitchPatternMatching(new ParentTest(new Test(40))); // Java 17 preview

    testSwitchPatternMatchingWithNestedValue(empDev); // Java 17 preview
    testSwitchPatternMatchingWithNestedValue(new Test(20)); // Java 17 preview
    testSwitchPatternMatchingWithNestedValue(new ParentTest(new Test(40))); // Java 17 preview

    Employee empIT = new Employee("Harry", "IT");
    testSwitchGuardedPattern(empDev); // Java 20 preview
    testSwitchGuardedPattern(empIT); // Java 20 preview
    testSwitchNullCases(null); // Java 17 preview
  }

  private static void testSwitchEnum(final DAYS day) {
    System.out.println("~~~~~~~~~~~ testSwitchEnum");
    System.out.print(day + " is a ");
    switch (day) {
      case MONDAY:
        System.out.println("Weekdays");
      case TUESDAY:
      case WEDNESDAY:
      case THURSDAY:
      case FRIDAY:
        System.out.println("Weekday");
        break;
      case SATURDAY:
      case SUNDAY:
        System.out.println("Weekend");
        break;
      default:
        System.out.println("Unknown");
    }
  }

  private static void testSwitchReturnByYieldKeyword(final String day) {
    System.out.println("~~~~~~~~~~~ testSwitchReturnByYieldKeyword");

    var dayType = switch (day) {
      case "Monday":
      case "Wednesday":
      case "Thursday":
      case "Friday":
        yield "Weekday";
      case "Saturday":
      case "Sunday":
        yield "Weekend";
      default:
        yield "Unknown";
    };
    System.out.println(day + " is a " + dayType);
  }

  private static void testSwitchReturnByArrow(final String day) {
    System.out.println("~~~~~~~~~~~ testSwitchReturnByArrow");

    var dayType = switch (day) {
      case "Monday" -> "Week day";
      case "Tuesday" -> "Week day";
      case "Wednesday" -> "Week day";
      case "Thursday" -> "Week day";
      case "Friday" -> "Week day";
      case "Saturday" -> "Weekend";
      case "Sunday" -> "Weekend";
      default -> "Unknown";
    };
    System.out.println(day + " is a " + dayType);
  }

  private static void testSwitchMultipleCaseLabels(final String day) {
    System.out.println("~~~~~~~~~~~ testSwitchMultipleCaseLabels");

    var dayType = switch (day) {
      case "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" -> "Week day";
      case "Saturday", "Sunday" -> "Weekend";
      default -> "Unknown";
    };
    System.out.println(day + " is a " + dayType);
  }

  private static void testSwitchPatternMatching(final Object obj) {
    System.out.println("~~~~~~~~~~~ testSwitchPatternMatching");

    var objType = switch (obj) {
      case null -> null + " is a NULL object";
      case Integer i -> i + " is an integer";
      case String s -> s + " is a string";
      case Employee e -> e + " is an Employee";
      case Test t -> t + " is a Test";
      case ParentTest p -> p + " is a ParentTest";
      default -> "Obj is unknown";
    };
    System.out.println(objType);
  }

  private static void testSwitchPatternMatchingWithNestedValue(final Object obj) {
    System.out.println("~~~~~~~~~~~ testSwitchPatternMatchingWithNestedValue");

    var objType = switch (obj) {
      case Employee(String name, String dept) ->
          " is an Employee " + name + " of Department " + dept;
      case Test(int value) -> {
        System.out.println("The value inside Test record: " + value);
        yield " is a Test";
      }
      case ParentTest(Test(int value)) -> {
        System.out.println("The value inside nested ParentTest record: " + value);
        yield " is a ParentTest";
      }
      default -> "Obj is unknown";
    };
    System.out.println(obj + objType);
  }

  private static void testSwitchGuardedPattern(final Object obj) {
    System.out.println("~~~~~~~~~~~ testSwitchGuardedPattern");

    var objType = switch (obj) {
      case Employee e when e.dept().equalsIgnoreCase("IT") -> e + " is an IT Employee";
      case Employee e when !e.dept().equalsIgnoreCase("IT") -> e + " is not an IT Employee";
      default -> "Object is unknown";
    };
    System.out.println(objType);
  }

  private static void testSwitchNullCases(final Object obj) {
    System.out.println("~~~~~~~~~~~ testSwitchNullCases");

    var objType = switch (obj) {
      case null -> null + " is a null object";
      case Integer i -> i + " is an integer";
      case String s -> s + " is a string";
      default -> "Obj is unknown";
    };
    System.out.println(objType);
  }
}

record Employee(String name, String dept) {

}

record Test(int value) {

}

record ParentTest(Test test) {

}
