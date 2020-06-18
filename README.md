#Type Intersection

###1. Lambda
람다는 익명 클래스를 만들어준다.
    
    public class Main {
    
        public static void main(String[] args) {
    	      method1(str -> str);
        }
    
        public static void method1(UnaryOperator<String> operator) {
            System.out.println(operator.apply("hello"));
        }
    } 

  
