package com.intersection;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class IntersectionEx {

    public static void main(String[] args) {
        /*
           hello(new UnaryOperator<String>() {
            @Override
            public String apply(String s) {
                return s;
            }
        });
        */

        //람다는 익명 클래스를 만들어 준다. 때문에 형변환도 가능하다. hello((UnaryOperator<String>)str -> str);
        //호출 하는 메서드를 보고 타입을 추론 한다.
        hello(str -> str);
    }

    public static void hello(UnaryOperator<String> operator) {
        System.out.println(operator.apply("hello"));
    }
}
