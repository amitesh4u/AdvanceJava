package com.amitesh.playbook;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class StreamLinesDemo {

  public static void main(String[] args) {

    String str = "To whom it may concern \n" +
        "I wish you a good day \r" +
        "Sincerely \n\r" + //\n\r creates an extra blank line
        "Me";

    Stream<String> lines = str.lines();

    // add line numbers
    final AtomicInteger i = new AtomicInteger(1);
    lines.forEach(line -> {
      if (!line.isBlank()) {
        System.out.println(i.getAndIncrement() + " " + line);
      }
    });
  }
}
