package com.intersection;

import java.util.function.Consumer;
import java.util.function.Function;

public class IntersectionEx4 {

  interface Hello extends Function<String, String> {
    default void hello() {
      System.out.println("hello");
    }
  }

  interface Hi extends Function<String, String> {
    default void hi() {
      System.out.println("hi");
    }
  }

  interface Printer {
    default void print(String str) {
      System.out.println(str);
    }
  }

  public static void main(String[] args) {
    /*
      Interface 들 Function 상속
      최종적으로 메서드가 하나이면 된다.
     */
    run((Function<String, String> & Hello & Hi & Printer) s -> s, o -> {
      o.hi();
      o.hello();
      o.print("Lambda");
    });
  }

  public static <T extends Function<String, String> > void run(T t, Consumer<T> consumer) {
    consumer.accept(t);
  }

}
