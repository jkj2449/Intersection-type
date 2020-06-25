# Type Intersection

### 1. Lambda
람다는 익명 클래스를 만들어준다.
때문에 형변환이 가능하다.
    
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
    
            hello((UnaryOperator<String>)  str -> str);
        }
    
        public static void hello(UnaryOperator<String> operator) {
            System.out.println(operator.apply("hello"));
        }
    }

호출 하는 Method를 보고 타입을 추론 하기 때문에 명시적 형변환은 제거해도 된다.
    
    hello(str -> str);
    
    public static void hello(UnaryOperator<String> operator) {
        System.out.println(operator.apply("hello"));
    }

### 2. Type Intersection
람다식은 Mehtod가 하나 밖에 존재 하지 않는 Interface를 쓸 수 있다.
    
    //Compile error
    hello4((Function<String, String> & Callable) s -> s);

    public static void hello4(Callable<String> o) {
        System.out.println("hello4");
    }
    
타입들의 총 Method 갯수가 하나이면 Intersection이 가능하다.
아래 Interface는 Marker Interface이기 때문에 Intersection이 가능.
Marker Interface란 추상 Method가 없는 Interface를 뜻한다.
     
    hello1((Function<String, String> & Serializable & Cloneable) s -> s);
    
    public static void hello1(Function<String, String> o) {
        System.out.println("hello1");
    }
    
    hello2((Function<String, String> & Serializable & Cloneable) s -> s);
    
    public static void hello2(Serializable o) {
        System.out.println("hello2");
    }
    
    hello3((Function<String, String> & Serializable & Cloneable) s -> s);
    
    public static void hello3(Cloneable o) {
        System.out.println("hello3");
    }

### 3. Default Method
default method를 제외하고 일반적인 메서드의 갯수의 총합이 1개면이면 Intersection이 가능하다.]
하지만 아래 방법은 Intersection Type이 추가 될때마다 Method 선언부가 수정되어야 하기 때문에 좋지 않은 방식이다.

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
        run((Function<String, String> & Hello & Hi) s -> s);
    }
    
    public static <T extends Function<String, String> & Hello & Hi > void run(T t) {
        t.hello();
        t.hi();
    }

### 4. Callback
타입 추론을 위해 operator와 추론된 타입을 이용하는 consumer를 파라미터로 선언 해준다. consumer 해당 구현체는 메서드를 실행하는 쪽에서 람다 표현식으로 전달한다.

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
         */
        run((Function<String, String> & Hello & Hi & Printer) s -> s, o -> {
          o.hi();
          o.hello();
          o.print("Lambda");
        });
      }
    
      public static <T extends Function<String, String> > void run(T operator, Consumer<T> consumer) {
        consumer.accept(operator);
      }
      
### 5-1. 응용

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
        run((DelegateTo<String> & Hello & UpperCase)() -> "Daniel", o -> {
          System.out.println(o.hello());
          System.out.println(o.upperCase());
        });
      }
    
      private static <T extends DelegateTo<S>, S> void run(T t, Consumer<T> consumer) {
        consumer.accept(t);
      }

### 5-2. 응용 
      interface Pair<T> {
        T getFirst();
        T getSecond();
        void setFirst(T first);
        void setSecond(T second);
      }
    
      static class Name implements Pair<String> {
        String firstName;
        String lastName;
    
        public Name(String firstName, String lastName) {
          this.firstName = firstName;
          this.lastName = lastName;
        }
    
        @Override
        public String getFirst() {
          return this.firstName;
        }
    
        @Override
        public String getSecond() {
          return this.lastName;
        }
    
        @Override
        public void setFirst(String first) {
          this.firstName = first;
        }
    
        @Override
        public void setSecond(String second) {
          this.lastName = second;
        }
      }
#     
      /*
        default 메서드로 오버라이딩
      */
      interface ForwardingPair<T> extends DelegateTo<Pair<T>>, Pair<T> {
        default T getFirst() {
          return delegate().getFirst();
        };
    
        default T getSecond() {
          return delegate().getSecond();
        };
    
        default void setFirst(T first) {
          delegate().setFirst(first);
        }
    
        default void setSecond(T second) {
          delegate().setSecond(second);
        }
      }
#
    interface Convertible<T> extends DelegateTo<Pair<T>> {
        default void convert(Function<T, T> mapper) {
          Pair<T> pair = delegate();
          pair.setFirst(mapper.apply(pair.getFirst()));
          pair.setSecond(mapper.apply(pair.getSecond()));
        }
      }
    
      interface DelegateTo<T> {
        T delegate();
      }
    
      private static <T extends DelegateTo<S>, S> void run(T t, Consumer<T> consumer) {
        consumer.accept(t);
      }
    
      static <T> void print(Pair<T> pair) {
        System.out.println(pair.getFirst() + " " + pair.getSecond());
      }
    
      public static void main(String[] args) {
        /*
          동적으로 기능 추가
         */
        Pair<String> name = new Name("Keun Jung", "Jeong");
        run((ForwardingPair<String> & Convertible<String>) () -> name, o -> {
          print(o);
          o.convert(s -> s.toUpperCase());
          print(o);
        });
    
      }
