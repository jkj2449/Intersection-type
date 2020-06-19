package com.intersection;

import java.io.Serializable;
import java.util.function.Function;

public class IntersectionEx2 {

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

  public static void main(String[] args) {
    /*
      defalut 메서드는 제외 하고 일반적인 메서드의 갯수의 총합이 1개이면 Intersection 가능.

      Intersection 타입을 추가 해주면 기능을 계속적으로 추가할 수가 있다.
      하지만 이런 방식은 좋지 않은 방식이다. 메서드 선언부를 수정해 주어야 하기 때문이다.
     */
    run((Function<String, String> & Hello & Hi) s -> s);
  }

  public static <T extends Function<String, String> & Hello & Hi > void run(T t) {
    t.hello();
    t.hi();
  }

}
