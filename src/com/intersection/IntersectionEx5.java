package com.intersection;

import java.util.function.Consumer;
import java.util.function.Function;

public class IntersectionEx5 {

  interface DelegateTo<T> {
    T delegate();
  }

  interface Hello extends DelegateTo<String> {
    default String hello() {
      return "Hello " + delegate();
    }
  }

  interface UpperCase extends DelegateTo<String> {
    default String upperCase() {
      return delegate().toUpperCase();
    }
  }

  public static void main(String[] args) {
    run((DelegateTo<String> & Hello & UpperCase) ()  -> "Daniel" , o -> {
      System.out.println(o.hello());
      System.out.println(o.upperCase());
    });
  }

  public static <T extends DelegateTo<String> > void run(T t, Consumer<T> consumer) {
    consumer.accept(t);
  }

}
