package com.intersection;

import java.io.Serializable;
import java.util.function.Function;

public class IntersectionEx1 {

  public static void main(String[] args) {

    /*
      Intersection
      람다식은 메서드가 하나 밖에 존재 하지 않는 인터페이스를 쓸 수 있다.
      Marker Interface는 Intersection 가능. (추상 메서드가 없는 인터페이스)
      ex) Serializable, Cloneable

      타입들의 총 메서드 갯수가 1개이면 Intersection 가능.
     */

    hello1((Function<String, String> & Serializable & Cloneable) s -> s);

    hello2((Function<String, String> & Serializable & Cloneable) s -> s);

    hello3((Function<String, String> & Serializable & Cloneable) s -> s);
  }

  public static void hello1(Function<String, String> o) {
    System.out.println("hello1");
  }

  public static void hello2(Serializable o) {
    System.out.println("hello2");
  }

  public static void hello3(Cloneable o) {
    System.out.println("hello3");
  }
}
