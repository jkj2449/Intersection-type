package com.intersection;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Main {

    public static void main(String[] args) {
        /*
           hello(new UnaryOperator<String>() {
            @Override
            public String apply(String s) {
                return s;
            }
        });
        */

        //람다는 익명 클래스를 만들어 준다.
        //호출 하는 메서드를 보고 타입을 추론 한다.
        hello(str -> str);
    }

    public static void hello(UnaryOperator<String> operator) {
        System.out.println(operator.apply("hello"));
    }
}
