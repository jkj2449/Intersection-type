package com.intersection;

import java.util.function.Consumer;
import java.util.function.Function;

public class IntersectionEx3 {

  interface Hello {
    default void hello() {
      System.out.println("hello");
    }
  }

  interface Hi {
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
      consumer를 이용하여, Callback 방식 적용.
      호출 하는 곳에서 기능 호출.
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
