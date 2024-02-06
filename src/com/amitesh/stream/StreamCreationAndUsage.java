package com.amitesh.stream;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamCreationAndUsage {

  public static void main(String[] args) throws IOException {
    System.out.println("Test buildStream");

    // Stream from values
    Stream<String> streamFromValues = Stream.of("a1", "a2", "a3");
    System.out.println(
        "streamFromValues = " + streamFromValues.toList());

    // Stream from array
    String[] array = {"a1", "a2", "a3"};
    Stream<String> streamFromArrays = Arrays.stream(array);
    System.out.println(
        "streamFromArrays = " + streamFromArrays.toList());

    Stream<String> streamFromArrays1 = Stream.of(array);
    System.out.println(
        "streamFromArrays1 = " + streamFromArrays1.toList());

    // Stream from file
    File file = new File("1.tmp");
    file.deleteOnExit();
    PrintWriter out = new PrintWriter(file);
    out.println("a1");
    out.println("a2");
    out.println("a3");
    out.close();

    Stream<String> streamFromFiles = Files.lines(Paths.get(file.getAbsolutePath()));
    System.out.println("streamFromFiles = " + streamFromFiles.toList());

    // Stream from Collection
    Collection<String> collection = Arrays.asList("a1", "a2", "a3");
    Stream<String> streamFromCollection = collection.stream();
    System.out.println("streamFromCollection = " + streamFromCollection.toList());

    // Numeric stream from String
    IntStream streamFromString = "123".chars();
    System.out.print("streamFromString = ");
    streamFromString.forEach((e) -> System.out.print(e + " , "));

    // With the help Stream.builder
    Stream.Builder<String> builder = Stream.builder();
    Stream<String> streamFromBuilder = builder.add("a1").add("a2").add("a3").build();
    System.out.println("streamFromBuilder = "
        + streamFromBuilder.collect((Collectors.toList())));

    // Infinite Streams
    // Stream.iterate
    Stream<Integer> streamFromIterate = Stream.iterate(1, n -> n + 2);
    System.out.println("streamFromIterate, first 3 = "
        + streamFromIterate.limit(3).toList());

    // Stream.iterate 3 Args like normal For loop
    Stream<Integer> streamFromIterate3Args = Stream.iterate(1, i -> i < 256, i -> i * 2);
    System.out.println(
        "streamFromIterate3Args Square of Numbers < 256 = " + streamFromIterate3Args.toList());

    // Stream.generate
    Stream<String> streamFromGenerate = Stream.generate(() -> "a1");
    System.out.println("streamFromGenerate, first 3 = "
        + streamFromGenerate.limit(3).toList());

    // Empty Stream
    Stream<String> streamEmpty = Stream.empty();
    System.out.println("streamEmpty = " + streamEmpty.toList());

    // Parallel Stream
    Stream<String> parallelStream = collection.parallelStream();
    System.out.println("parallelStream = " + parallelStream.toList());

    testFilterAndCount();
    testFindFirstSkipCount();
    testLimit();
    testDistinct();
    testMatch();
    testMap();
    testMapToInt();
    testFlatMap();
    testFlatMapToInt();
    testFlatMapListOfList();
    testFlatMapObject();
    testSorted();
    testMinMax();
    testForEach();
    testPeek();
    testReduce();
    testReduceAdvance();
    testCollect();
    testShortCircuitingOperations();
    testRandomNumberGenerator();
    testDuplicateFinder();
    testNthLargestNumber();
    testParallelSequentialConversion();
    testStatistics();
    testCustomCollector();
    testTakeWhile();
    testDropWhile();
    testOfNullable();
  }

  private static void testOfNullable() {
    System.out.println("\nTest OfNullable");

    // Returns Empty Stream for null values
    Integer number = null;
    List<Integer> emptyList = Stream.ofNullable(number).map(x -> x * x).toList();
    System.out.println("Empty output List for Null value : " + emptyList);

    number = 5;
    List<Integer> resultList = Stream.ofNullable(number).map(x -> x * x).toList();
    System.out.println("Some output List for non-null value : " + resultList);

  }

  private static void testDropWhile() {
    System.out.println("\nTest Drop While");

    // It takes (elements from a stream) while a given condition is false.
    // The moment the condition becomes True, it quits and returns a new stream
    // with rest of the elements.
    // Here, condition is True at 6 (index 5) so all the other elements are returned even though
    // we have items less than equal to 5
    List<Integer> dropWhileIntegerList = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 9, 8, 7, 6, 5, 4,
            3, 2, 1, 0)
        .dropWhile(x -> x <= 5)
        .toList();
    System.out.println("Drop While integer List : " + dropWhileIntegerList);

  }

  private static void testTakeWhile() {
    System.out.println("\nTest Take While");

    // It takes (elements from a stream) while a given condition is true.
    // The moment the condition becomes false, it quits and returns a new stream
    // with just the elements that matched the predicate.
    // In other words, it’s like a filter with a condition.
    List<Integer> takeWhileIntegerSquareList = Stream.iterate(1, i -> i + 1)
        .takeWhile(n -> n <= 10)
        .map(x -> x * x)
        .toList();
    System.out.println("Take While integer Square List : " + takeWhileIntegerSquareList);

    // Optimized with 3 args Iterate
    List<Integer> iterateIntegerList = Stream.iterate(1, n -> n <= 10, i -> i + 1)
        .map(x -> x * x)
        .toList();
    System.out.println("3 Args Iterate integer Square List : " + iterateIntegerList);

    System.out.println(
        "Testing TakeWhile and Filter difference for values 1,2,3,4,5,6,7,8,9,0,9,8,7,6,5,4,3,2,1,0");
    Integer[] integers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};

    // takeWhile stops evaluating as soon as it finds the first occurrence where the condition is false.
    List<Integer> takeWhileRangeList = Stream.of(integers)
        .takeWhile(x -> x <= 5)
        .toList();
    System.out.println("Take While integer List : " + takeWhileRangeList);

    // filter() applies the predicate throughout the whole sequence
    List<Integer> filterRangeList = Stream.of(integers)
        .filter(x -> x <= 5)
        .toList();
    System.out.println("Filter integer List : " + filterRangeList);
  }

  private static void testCustomCollector() {
    System.out.println("\nTest Custom Collector");

    Collector<DummyItem, ?, LinkedList<DummyItem>> toLinkedList =
        Collector.of(LinkedList::new, LinkedList::add,
            (first, second) -> {
              first.addAll(second);
              return first;
            });

    List<DummyItem> itemList = Arrays.asList(new DummyItem(23, "potatoes"),
        new DummyItem(14, "orange"), new DummyItem(13, "lemon"),
        new DummyItem(23, "bread"), new DummyItem(13, "sugar"));
    LinkedList<DummyItem> linkedListOfItems =
        itemList.stream().collect(toLinkedList);
    System.out.println("Custom collector list : " + linkedListOfItems);
  }

  private static void testStatistics() {
    System.out.println("\nTest Statistics");

    IntSummaryStatistics statistics = Stream.of(1, 2, 3)
        .collect(Collectors.summarizingInt((p) -> p + 3));
    System.out.println("Int statistics = " + statistics);

    DoubleSummaryStatistics doubleStatistics = Stream.of(1.0, 2.2, 3.5)
        .collect(Collectors.summarizingDouble((p) -> p + 3.0));
    System.out.println("Double statistics = " + doubleStatistics);
  }

  private static void testParallelSequentialConversion() {
    System.out.println("\nTest Parallel-Sequential conversion");

    System.out.println("Creating Parallel Stream");
    IntStream intStreamParallel = IntStream.range(1, 150).parallel();
    System.out.println("isParallel : " + intStreamParallel.isParallel());

    System.out.println("Converting to Sequential Stream");
    IntStream intStreamSequential = intStreamParallel.sequential();
    System.out.println("isParallel : " + intStreamSequential.isParallel());

  }

  private static void testNthLargestNumber() {
    int n = 2;
    List<Integer> ints = Arrays.asList(1, 3, 6, 9, 2, 9, 4);
    System.out.println("\nTest " + n + " largest from " + ints);
    Integer nthLargest = ints.stream().distinct().sorted((a, b) -> b - a).skip(n - 1).findFirst()
        .orElse(null);
    System.out.println(n + " largest: " + nthLargest);
  }

  private static void testDuplicateFinder() {
    List<String> fruits = Arrays.asList("Apple", "banana", "orange", "Apple", "kiwi", "orange");
    System.out.println("\nTest Duplicate finder from " + fruits);

    Map<String, Long> collect = fruits.stream()
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
        .entrySet().stream().filter(entry -> entry.getValue() > 1)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    System.out.println("Duplicates: " + collect);

  }

  private static void testRandomNumberGenerator() {
    System.out.println("\nTest Random number generator");
    Random random = new Random();
    String collect = random.ints(10, 5, 50).boxed().map(String::valueOf)
        .collect(Collectors.joining(","));
    System.out.println("Random numbers: " + collect);
  }

  private static void testShortCircuitingOperations() {
    System.out.println("\nTest Short Circuiting operations");
    Stream<Integer> infiniteStream = Stream.iterate(2, i -> i * 2);

    // Here, we use short-circuiting operations skip() to skip first 3 elements,
    // and limit() to limit to 5 elements from the infinite stream
    List<Integer> collect = infiniteStream
        .skip(3)
        .limit(5)
        .toList();
    System.out.println("List: " + collect);

  }

  private static void testFilterAndCount() {
    System.out.println("\nTest filter and count");
    Collection<String> collection = Arrays.asList("a1", "a2", "a3", "a1");
    Collection<People> peoples = Arrays.asList(
        new People("William", 16, Sex.MAN),
        new People("John", 23, Sex.MAN),
        new People("Helen", 42, Sex.WOMEN),
        new People("Peter", 69, Sex.MAN)
    );

    long count = collection.stream().filter("a1"::equals).count();
    System.out.println("count = " + count);

    List<String> select = collection.stream()
        .filter((s) -> s.contains("1"))
        .toList();
    System.out.println("select = " + select);

    List<People> militaryService = peoples.stream()
        .filter((p) -> p.age() >= 18 && p.age() < 27
            && p.sex() == Sex.MAN).toList();
    System.out.println("militaryService = " + militaryService);

    double manAverageAge = peoples.stream().filter((p) -> p.sex() == Sex.MAN).
        mapToInt(People::age).average().orElse(0.0);
    System.out.println("manAverageAge = " + manAverageAge);

    long peopleHowCanWork = peoples.stream()
        .filter((p) -> p.age() >= 18)
        .filter(
            (p) -> (p.sex() == Sex.WOMEN && p.age() < 55) ||
                (p.sex() == Sex.MAN && p.age() < 60)).count();
    System.out.println("peopleHowCanWork = " + peopleHowCanWork);

  }

  private static void testFindFirstSkipCount() {
    Collection<String> collection = Arrays.asList("a1", "a2", "a3", "a1");

    System.out.println("\nTest findFirst, skip and Count");
    String first = collection.stream().findFirst().orElse("1");
    System.out.println("first = " + first);

    String last = collection.stream().skip(collection.size() - 1)
        .findAny().orElse("1");
    System.out.println("last = " + last);

    String find = collection.stream().filter("a3"::equals).findFirst().orElse(null);
    System.out.println("find = " + find);

    String third = collection.stream().skip(2).findFirst().orElse(null);
    System.out.println("third = " + third);

    System.out.println("Test collect");
    List<String> select = collection.stream()
        .filter((s) -> s.contains("1"))
        .toList();
    System.out.println("select = " + select);
  }

  private static void testLimit() {
    System.out.println("\nTest limit");
    Collection<String> collection = Arrays.asList("a1", "a2", "a3", "a1");

    List<String> limit = collection.stream().limit(2).toList();
    System.out.println("limit = " + limit);

    List<String> fromTo = collection.stream().skip(1).limit(2).toList();
    System.out.println("fromTo = " + fromTo);

    String last = collection.stream().skip(collection.size() - 1).findAny().orElse("1");
    System.out.println("last = " + last);
  }

  private static void testDistinct() {
    System.out.println("\nTest distinct");
    Collection<String> ordered = Arrays.asList("a1", "a2", "a2", "a3", "a1", "a2", "a2");
    Collection<String> nonOrdered = new HashSet<>(ordered);

    List<String> distinct = nonOrdered.stream()
        .distinct()
        .toList();
    System.out.println("distinct = " + distinct);

    List<String> distinctOrdered = ordered.stream()
        .distinct()
        .toList();
    System.out.println("distinctOrdered = " + distinctOrdered);
  }

  private static void testMatch() {
    System.out.println("\nTest anyMatch, allMatch, noneMatch");
    Collection<String> collection = Arrays.asList("a1", "a2", "a3", "a1");

    boolean isAnyOneTrue = collection.stream().anyMatch("a1"::equals);
    System.out.println("anyOneTrue " + isAnyOneTrue);
    boolean isAnyOneFalse = collection.stream().anyMatch("a8"::equals);
    System.out.println("anyOneFlase " + isAnyOneFalse);

    boolean isAll = collection.stream().allMatch((s) -> s.contains("1"));
    System.out.println("isAll " + isAll);

    boolean isNotEquals = collection.stream().noneMatch("a7"::equals);
    System.out.println("isNotEquals " + isNotEquals);
  }

  private static void testMap() {
    System.out.println("\nTest map");
    Collection<String> collection = Arrays.asList("a1", "a2", "a3", "a1");
    List<String> transform = collection.stream()
        .map((s) -> s + "_1")
        .toList();
    System.out.println("transform = " + transform);

    List<Integer> number = collection.stream()
        .map((s) -> Integer.parseInt(s.substring(1)))
        .toList();
    System.out.println("number = " + number);

  }

  private static void testMapToInt() {
    System.out.println("\nTest mapToInt");
    Collection<String> collection = Arrays.asList("a1", "a2", "a3", "a1");
    int[] number = collection.stream()
        .mapToInt((s) -> Integer.parseInt(s.substring(1)))
        .toArray();
    System.out.println("number = " + Arrays.toString(number));

  }

  private static void testFlatMap() {
    System.out.println("\nTest flat map");
    Collection<String> collection = Arrays.asList("1,2,0", "4,5");
    String[] number = collection.stream()
        .flatMap((p) -> Arrays.stream(p.split(",")))
        .toArray(String[]::new);
    System.out.println("number = " + Arrays.toString(number));
  }

  private static void testFlatMapToInt() {
    System.out.println("\nTest flat map");
    Collection<String> collection = Arrays.asList("1,2,0", "4,5");
    int sum = collection.stream()
        .flatMapToInt((p) ->
            Arrays.stream(p.split(","))
                .mapToInt(Integer::parseInt))
        .sum();
    System.out.println("sum = " + sum);
  }

  private static void testFlatMapListOfList() {
    System.out.println("\nTest flat map List of List");
    List<List<Integer>> numberLists = Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6));

    int sum = numberLists.stream().flatMap(List::stream).reduce(Integer::sum).orElse(0);
    System.out.println("sum = " + sum);

  }

  private static void testFlatMapObject() {
    System.out.println("\nTest flat map object");
    List<Employee> employeeList = Arrays.asList(new Employee(1, Arrays.asList("Role1", "Role3"))
        , new Employee(1, Arrays.asList("Role4", "Role2")));

    List<String> roles = employeeList.stream()
        .flatMap(e -> e.getRoles().stream())
        .toList();
    System.out.println("Roles = " + roles);
  }

  private static void testSorted() {

    System.out.println("\nTest sorted");

    Collection<String> collection = Arrays
        .asList("a1", "a4", "a3", "a2", "a1", "a4");

    List<String> sorted = collection.stream().sorted().toList();
    System.out.println("sorted = " + sorted);

    List<String> sortedDistinct = collection.stream()
        .sorted()
        .distinct()
        .toList();
    System.out.println("sortedDistinct = " + sortedDistinct);

    List<String> sortedReverse = collection
        .stream()
        .sorted((o1, o2) -> -o1.compareTo(o2))
        .toList();
    System.out.println("sortedReverse = " + sortedReverse);

    List<String> distinctReverse = collection.stream()
        .sorted((o1, o2) -> -o1.compareTo(o2))
        .distinct()
        .toList();
    System.out.println("distinctReverse = " + distinctReverse);

    Collection<People> peoples = Arrays.asList(
        new People("William", 16, Sex.MAN),
        new People("John", 23, Sex.MAN),
        new People("Helen", 42, Sex.WOMEN),
        new People("Peter", 69, Sex.MAN)
    );

    Collection<People> byName = peoples.stream()
        .sorted((o1, o2) -> -o1.name().compareTo(o2.name()))
        .toList();
    System.out.println("byName = " + byName);

    Collection<People> bySexAndAge = peoples.stream()
        .sorted((o1, o2) -> o1.sex() != o2.sex() ? o1.sex().
            compareTo(o2.sex()) : o1.age().compareTo(o2.age()))
        .toList();
    System.out.println("bySexAndAge = " + bySexAndAge);
  }

  private static void testMinMax() {

    System.out.println("\nTest min and max");
    Collection<String> collection = Arrays.asList("a1", "a2", "a3", "a1");

    String max = collection.stream().max(String::compareTo).get();
    System.out.println("max " + max);

    String min = collection.stream().min(String::compareTo).get();
    System.out.println("min " + min);

    Collection<People> peoples = Arrays.asList(
        new People("William", 16, Sex.MAN),
        new People("John", 23, Sex.MAN),
        new People("Helen", 42, Sex.WOMEN),
        new People("Peter", 69, Sex.MAN)
    );

    People older = peoples.stream()
        .max(Comparator.comparing(People::age)).get();
    System.out.println("older " + older);

    People younger = peoples.stream()
        .min(Comparator.comparing(People::age)).get();
    System.out.println("younger " + younger);
  }

  private static void testForEach() {

    System.out.println("\nFor each");
    Collection<String> collection = Arrays.asList("a1", "a2", "a3", "a1");
    System.out.print("forEach = ");
    collection.stream().map(String::toUpperCase)
        .forEach((e) -> System.out.print(e + ","));

    Collection<StringBuilder> list =
        Arrays.asList(new StringBuilder("a1"), new StringBuilder("a2"), new StringBuilder("a3"));
    list.stream().forEachOrdered((p) -> p.append("_new"));
    System.out.println("forEachOrdered = " + list);
  }

  private static void testPeek() {

    System.out.println("\nTest peek");
    Collection<String> collection = Arrays.asList("a1", "a2", "a3", "a1");
    System.out.print("peak1 = ");
    List<String> peek = collection.stream().map(String::toUpperCase)
        .peek((e) -> System.out.print(e + ",")).
        toList();

    System.out.println("peek2 = " + peek);

    Collection<StringBuilder> list = Arrays.asList(new StringBuilder("a1"), new StringBuilder("a2"),
        new StringBuilder("a3"));
    List<StringBuilder> newList = list
        .stream()
        .peek((p) -> p.append("_new")).toList();
    System.out.println("newList = " + newList);
  }

  private static void testReduce() {

    System.out.println("\nTest reduce");

    Collection<Integer> collection = Arrays.asList(1, 2, 3, 4, 2);

    Integer sum = collection.stream()
        .reduce(Integer::sum).orElse(0);
    Integer sumOld = 0;
    for (Integer i : collection) {
      sumOld += i;
    }
    System.out.println("sum = " + sum + " : " + sumOld);

    Integer max1 = collection.stream()
        .reduce((s1, s2) -> s1 > s2 ? s1 : s2).orElse(0);
    Integer max2 = collection.stream()
        .reduce(Integer::max).orElse(0);
    Integer maxOld = null;
    for (Integer i : collection) {
      maxOld = maxOld != null && maxOld > i ? maxOld : i;
    }
    maxOld = maxOld == null ? 0 : maxOld;
    System.out.println("max = " + max1 + " : " + max2 + " : " + maxOld);

    Integer min = collection.stream()
        .reduce((s1, s2) -> s1 < s2 ? s1 : s2).orElse(0);
    Integer minOld = null;
    for (Integer i : collection) {
      minOld = minOld != null && minOld < i ? minOld : i;
    }
    minOld = minOld == null ? 0 : minOld;
    System.out.println("min = " + min + " : " + minOld);

    Integer last = collection.stream().reduce((s1, s2) -> s2).orElse(0);
    Integer lastOld = null;
    for (Integer i : collection) {
      lastOld = i;
    }
    lastOld = lastOld == null ? 0 : lastOld;
    System.out.println("last = " + last + " : " + lastOld);

    Integer sumMore2 = collection.stream()
        .filter(o -> o > 2).reduce(Integer::sum).orElse(0);
    int sumMore2Old = 0;
    for (Integer i : collection) {
      if (i > 2) {
        sumMore2Old += i;
      }
    }
    System.out.println("sumMore2 = " + sumMore2 + " : " + sumMore2Old);

    Integer sumOdd = collection.stream().filter(o -> o % 2 != 0)
        .reduce(Integer::sum).orElse(0);
    int sumOddOld = 0;
    for (Integer i : collection) {
      if (i % 2 != 0) {
        sumOddOld += i;
      }
    }
    System.out.println("sumOdd = " + sumOdd + " : " + sumOddOld);

    Collection<People> peoples = Arrays.asList(
        new People("William", 16, Sex.MAN),
        new People("John", 23, Sex.MAN),
        new People("Helen", 42, Sex.WOMEN),
        new People("Peter", 69, Sex.MAN)
    );

    int oldMan = peoples.stream().filter((p) -> p.sex() == Sex.MAN)
        .map(People::age).reduce((s1, s2) -> s1 > s2 ? s1 : s2).orElse(0);
    System.out.println("oldMan = " + oldMan);

    int younger = peoples.stream()
        .filter((p) -> p.name()
            .contains("е")).mapToInt(People::age)
        .reduce(Math::min).orElse(0);
    System.out.println("younger = " + younger);
  }

  private static void testReduceAdvance() {

    System.out.println("\nTest advance reduce");

    OptionalInt reduced =
        IntStream.rangeClosed(1, 4).reduce((a, b) -> a + b);
    System.out.println("Basic Reduce with values 1-4: " + reduced.orElse(-1));

    int reducedTwoParams =
        IntStream.rangeClosed(1, 4).reduce(10, (a, b) -> a + b);
    System.out.println("Reduced with Identity 10 and values 1-4: " + reducedTwoParams);

    // Combiner won't be called for Sequential Stream. Identity will be used only once
    // i.e. 10 + 1 = 11, 11 + 2 = 13, 13 + 3 = 16
    System.out.println(
        "Calling Sequential Stream Reduced with Combiner with Identity 10 and values 1-3");
    int reducedParams = Stream.of(1, 2, 3)
        .reduce(10,
            (a, b) -> {
              System.out.println("accumulator was called: " + a + "," + b);
              return a + b;
            },
            (a, b) -> {
              System.out.println("combiner was called: " + a + "," + b);
              return a + b;
            });
    System.out.println("Sum: " + reducedParams);

    // The result here is different (36), and the combiner was called twice.
    // Here the reduction works by the following algorithm: the accumulator ran three times
    // by adding every element of the stream to identity. These actions are being done in parallel.
    // As a result, they have (10 + 1 = 11; 10 + 2 = 12; 10 + 3 = 13;).
    // Now combiner can merge these three results. It needs two iterations for that (12 + 13 = 25; 25 + 11 = 36).
    System.out.println(
        "Calling Parallel Stream Reduced with Combiner with Identity 10 and values 1-3");
    int reducedParallel = Arrays.asList(1, 2, 3).parallelStream()
        .reduce(10,
            (a, b) -> {
              System.out.println("accumulator was called: " + a + "," + b);
              return a + b;
            },
            (a, b) -> {
              System.out.println("combiner was called: " + a + "," + b);
              return a + b;
            });
    System.out.println("Sum: " + reducedParallel);

  }

  private static void testCollect() {

    System.out.println("\nTest distinct");

    Collection<String> strings = Arrays.asList("a1", "b2", "c3", "a1");

    List<String> distinct = strings.stream()
        .distinct().toList();
    System.out.println("distinct = " + distinct);

    String[] array = strings.stream().distinct().map(String::toUpperCase).toArray(String[]::new);
    System.out.println("array = " + Arrays.asList(array));

    String join = strings.stream().collect(Collectors.joining(" : ", "<b> ", " </b>"));
    System.out.println("join = " + join);

    Map<String, String> map = strings.stream()
        .distinct()
        .collect(Collectors.toMap((p) ->
            p.substring(0, 1), (p) -> p.substring(1, 2)));
    System.out.println("map = " + map);

    Map<String, List<String>> groups = strings.stream()
        .collect(Collectors.groupingBy((p) -> p.substring(0, 1)));
    System.out.println("groups = " + groups);

    Map<String, String> groupJoin = strings.stream().collect(
        Collectors.groupingBy((p) -> p.substring(0, 1),
            Collectors.mapping((p) -> p.substring(1, 2), Collectors.joining(":"))));
    System.out.println("groupJoin = " + groupJoin);

    Collection<Integer> numbers = Arrays.asList(1, 2, 3, 4);

    long sumOdd = numbers.stream().mapToInt(((p) -> p % 2 == 1 ? p : 0)).sum();
    System.out.println("sumOdd = " + sumOdd);

    double average = numbers.stream().collect(Collectors.averagingInt((p) -> p - 1));
    System.out.println("average = " + average);

    long sumEven = numbers.stream().collect(Collectors.summarizingInt((p) -> p % 2 == 0 ? p : 0))
        .getSum();
    System.out.println("sumEven = " + sumEven);

    Map<Boolean, List<Integer>> parts = numbers.stream()
        .collect(Collectors.partitioningBy((p) -> p % 2 == 0));
    System.out.println("Even parts = " + parts.get(true));
    System.out.println("Odd parts = " + parts.get(false));

  }

  private enum Sex {
    MAN,
    WOMEN
  }

  private record People(String name, Integer age, Sex sex) {

    @Override
    public String toString() {
      return "{" +
          "name='" + name + '\'' +
          ", age=" + age +
          ", sex=" + sex +
          '}';
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof People people)) {
        return false;
      }
      return Objects.equals(name, people.name) &&
          Objects.equals(age, people.age) &&
          Objects.equals(sex, people.sex);
    }

  }
}

class Employee {

  final int id;
  final List<String> roles;

  public Employee(int id, List<String> roles) {
    this.id = id;
    this.roles = roles;
  }

  public int getId() {
    return id;
  }

  public List<String> getRoles() {
    return roles;
  }
}

class DummyItem {

  private String name;
  private double price;

  public DummyItem(double price, String name) {
    this.name = name;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "Item [name=" + name + ", price=" + price + "]";
  }

}

