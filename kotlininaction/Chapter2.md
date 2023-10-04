# 2장 코틀린 기초
2장에서는 변수, 함수, 클래스 등을 코틀린에서 어떻게 선언하는지 살펴보고 프로퍼티라는 개념을 배운다. 그 후 코틀린의 여러 제어 구조를 배운다.  
그런 다음 스마트 캐스트에 대해 설명한다. 스마트 캐스트는 타입 검사와 타입 캐스트, 타입 강제 변환을 하나로 엮은 기능이다. 마지막으로 예외처를 살펴본다.

## 2.1 기본 요소: 함수와 변수

### 2.1.1 Hello, World!
```kotlin
fun main(args: Array<String>) {
    println("Hello, world!")
}
```
- 함수를 선언할 때 fun 키워드를 사용한다.  
- 파라미터 이름 뒤에 그 파라미터 타입을 쓴다.  
- 함수를 최상위 수준에 정의할 수 있다. 꼭 클래스 안에 함수를 넣어야 할 필요가 없다.  
- 배열도 일반적인 클래스와 마찬가지다. 코틀린에는 자바와 달리 배열 처리를 위한 문법이 따로 존재하지 않는다.  
- System.out.println 대신 println이라고 쓴다. 코틀린 표준 라이브러리는 여러 가지 표준 자바 라이브러리 함수를 간결하게 사용할 수 있게 감싼 래퍼를 제공한다.  
- 세미콜론을 붙이지 않아도 된다.

### 2.1.2 함수
함수 선언은 fun 키워드로 시작한다. fun 다음에는 함수가 온다. 함수 이름 뒤에는 괄호 안에 파라미터 목록이 온다. 함수의 반환 타입은 파라미터 목록의 닫는 괄호  
다음에 오는데, 괄호와 반환 타입 사이를 콜론(:)으로 구분해야 한다. 코틀린 If는 값을 만들어내지 못하는 문장이 아니고 결과를 만드는 식이다. 자바 3항 연산자와  
비슷하다.
```kotlin
fun max(a: Int, b: Int) : Int {
    return if (a > b) a else b
}
```

자바에서는 모든 제어 구조가 statement인 반면 코틀린에서는 루프를 제외한 대부분의 제어 구조가 expression이다. 반면 대입문은 자바에서는 expression이었으나  
코틀린에서는 statement가 됐다.

식이 본문인 함수  
앞의 함수 본문은 if 식 하나로만 이뤄져 있다. 이런 경우 다음과 같이 중괄호를 없애고 return을 제거하면서 등호를 식 앞에 붙이면 더 간결하게 함수를 표현할 수 있다.  
```kotlin
fun max(a: Int, b: Int) : Int = if (a > b) a else b
```

본문이 중괄호로 둘러싸인 함수를 블록이 본문인 함수라 부르고, 등호와 식으로 이뤄진 함수를 식이 본문인 함수라고 부른다. 코틀린에서는 식이 본문인 함수가 자주 쓰인다.  
반환 타입을 생략하면 max 함수를 더 간략하게 만들 수 있다.  
```kotlin
fun max(a: Int, b: Int) = if (a > b) a else b
```

식이 본문인 함수의 경우 굳이 사용자가 반환 타입을 적지 않아도 컴파일러가 함수 본문 식을 분석해서 식의 결과 타입을 함수 반환 타입으로 정해준다. 이렇게 컴파일러가  
타입을 분석해 프로그래머 대신 프로그램 구성 요소의 타입을 정해주는 기능을 타입 추론이라고 부른다. 식이 본문인 함수의 반환 타입만 생략 가능하다는 점에 유의하라.  
블록이 본문인 함수가 값을 반환한다면 반드시 반환 타입을 지정하고 return문을 사용해 반환 값을 명시해야 한다.

### 2.1.3 변수
자바에서는 변수 선언을 할 때 타입이 맨 앞에 온다. 코틀린에서는 타입 지정을 생략하는 경우가 흔하다. 타입으로 변수 선언을 시작하면 타입을 생략할 경우 식과 변수  
선언을 구별할 수 없다. 그런 이유로 코틀린에서는 키워드로 변수 선언을 시작하는 대신 변수 이름 뒤에 타입을 명시하거나 생략하게 허용한다.  
```kotlin
val question = "삶, 우주, 그리고 모든 것에 대한 궁극적인 질문"
val answer = 42
```

원한다면 타입을 명시해도 된다.
```kotlin
val answer: Int = 42
```

초기화 식을 사용하지 않고 변수를 선언하려면 변수 타입을 반드시 명시해야 한다.
```kotlin
val answer: Int
answer = 42
```

변경 가능한 변수와 변경 불가능한 변수  
- val(값을 뜻하는 value에서 따옴) - 변경 불가능한 참조를 저장하는 변수다. 초기화하고 나면 재대입이 불가능하다.  
- var(변수를 뜻하는 variable에서 따옴) - 변경 가능한 참조다. 

기본적으로는 모든 변수를 val 키워드를 사용해 불변 변수로 선언하고, 나중에 꼭 필요할 때에만 var로 변경하라.  
val 변수는 블록을 실행할 때 정확히 한 번만 초기화돼야 한다. 하지만 어떤 블록이 실행될 때 오직 한 초기화 문장만 실행됨을 컴파일러가 확인할 수 있다면 조건에 따라  
val 값을 다른 여러 값으로 초기화할 수도 있다.
```kotlin
val message: String
if (canPerformOperation()) {
    message = "Success"
} else {
    message = "Failed"
}
```

val 참조 자체는 불변일지라도 그 참조가 가리키는 객체의 내부 값은 변경될 수 있다는 사실을 기억하라.
```kotlin
val languages = arrayListOf("Java")
languages.add("Kotlin") // 참조가 가리키는 객체 내부를 변경한다.
```

var 키워드를 사용하면 변수의 값을 변경할 수 있지만 변수의 타입은 고정돼 바뀌지 않는다.
```kotlin
var answer = 42
answer = "no answer" // type mismatch 컴파일 오류 발생
```

### 2.1.4 더 쉽게 문자열 형식 지정: 문자열 템플릿
```kotlin
fun main(args: Array<String>) {
    val name = if (args.size > 0) args[0] else "Kotlin"
    println("Hello, $name!")
}
```

이 예제는 `문자열 템플릿`이라는 기능을 보여준다. 여러 스크립트 언어와 비슷하게 코틀린에서도 변수를 문자열 안에 사용할 수 있다. 문자열 리터럴의 필요한 곳에 변수를  
넣되 변수 앞에 $를 추가해야 한다. $ 문자를 문자열에 넣고 싶으면 \$ 를 사용하면 된다.  
문자열 템플릿 안에 사용할 수 있는 대상은 간단한 변수 이름만으로 한정되지 않는다. 복잡한 식도 중괄호로 둘러싸서 문자열 템플릿 안에 넣을 수 있다.
```kotlin
fun main(args: Array<String>) {
    if (args.size > 0) {
        println("Hello, ${args[0]}!")
    }
}
```

가급적 문자열 리터럴을 사용할 때는 중괄호를 붙여 가독성을 높이고 한글로 인한 오류를 방지하자.


## 2.2 클래스와 프로퍼티
```java
public class Person {
    private final String name;

    public Person(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
```

필드가 늘어날 수록 생성자의 개수도 늘어난다. 
```kotlin
class Person(val name: String)
```

이런 유형의 코드가 없이 데이터만 저장하는 클래스를 `값 객체`라고 부른다. 코틀린의 기본 가시성은 public이므로 이런 경우 변경자를 생략해도 된다.

### 2.2.1 프로퍼티
클래스라는 개념의 목적은 데이터를 캡슐화하고 캡슐화한 데이터를 다루는 코드를 한 주체 아래 가두는 것이다. 자바에서는 데이터를 필드에 저장하며, 멤버 필드의 가시성은  
보통 비공개다. 클래스는 자신을 사용하는 클라이언트가 그 데이터에 접근하는 통로로 쓸 수 있는 `접근자 메소드` 를 제공한다(getter, setter).  
자바에서는 필드와 접근자를 한데 묶어 `프로퍼티`라고 부르며 프로퍼티라는 개념을 활용하는 프레임워크가 많다. 코틀린은 프로퍼티를 언어 기본 기능으로 제공하며, 코틀린  
프로퍼티는 자바의 필드와 접근 메소드를 완전히 대신한다.
```kotlin
class Person {
    val name: String, // 읽기 전용 프로퍼티로, 코틀린은 비공개 필드와 필드를 읽는 단순한 공개 게터를 만들어낸다.
    var isMarried: Boolean // 쓸 수 있는 프로퍼티로, 코틀린은 비공개 필드, 공개 게터, 공개 세터를 만들어낸다.
}
```

게터와 세터의 이름을 정하는 규칙에는 예외가 있다. 이름이 is로 시작하는 프로퍼티의 게터에는 get이 붙지 않고 원래 이름 그대로 사용하며, 세터에는 is를 set으로 바꾼  
이름을 사용한다.
```kotlin
val person = Person("Bob", true) // new 키워드를 사용하지 않고 생성자를 호출한다.
println(person.name) // 프로퍼티 이름을 직접 사용해도 코틀린이 자동으로 게터를 호출해준다.
println(person.isMarried)
```

자바에서는 person.setMarried(false) 를 쓰지만 코틀린에서는 person.isMarried = false를 쓴다.  
대부분의 프로퍼티에는 그 프로퍼티의 값을 저장하기 위한 필드가 있다. 이를 프로퍼티를 `뒷받침하는 필드`라고 부른다. 하지만 원한다면 프로퍼티 값을 그때그때 계산할 수도  
있다. 커스텀 게터를 작성하면 그런프로퍼티를 만들 수 있다.

### 2.2.2 커스텀 접근자
이번 절에서는 프로퍼티의 접근자를 직접 작성하는 방법을 보여준다. 직사각현 클래스인 Rectangle을 정의하면서 자신이 정사각형인지 알려주는 기능을 만들어보자.  
직사각형이 정사각형인지를 별도의 필드에 저장할 필요가 없다. 사각형의 너비와 높이가 같은지 검사하면 정사각형의 여부를 그때그때 알 수 있다.
```kotlin
class Rectangle(val height: Int, val width: Int) {
    val isSquare: Boolean
        get() {
            return height == width // get() = height = width
        }
}
```

### 2.2.3 코틀린 소스코드 구조: 디렉터리와 패키지
모든 코틀린 파일의 맨 앞에 package문을 넣을 수 있다. 그러면 그 파일 안에 있는 모든 선언(클래스, 함수, 프로퍼티 등)이 해당 패키지에 들어간다. 같은 패키지에  
속해 있다면 다른 파일에서 정의한 선언일지라도 직접 사용할 수 있다. 반면 다른 패키지에 정의한 선언을 사용하려면 임포트를 통해 선언을 불러와야 한다. 자바와 마찬가지로  
임포트문은 파일의 맨 앞에 와야 하며 import 키워드를 사용한다.
```kotlin
package geometry.shapes

import java.util.Random

class Rectangle(val height: Int, val width: Int) {
    val isSquare: Boolean
        get() = height == width
}

fun createRandomRectangle() : Rectangle { 
    val random = Random()
    return Rectangle(random.nextInt(), random.nextInt())
}
```

코틀린에서는 클래스 임포트와 함수 임포트 차이가 없으며, 모든 선언을 import 키워드로 가져올 수 있다. 최상위 함수는 그 이름을 써서 임포트할 수 있다.  

자바에서는 패키지 구조와 일치하는 디렉터리 계층 구조를 만들고 클래스의 소스코드를 그 클래스가 속한 패키지와 같은 디렉터리에 위치시켜야 한다.  
코틀린에서는 여러 클래스를 한 파일에 넣을 수 있고, 파일의 이름도 마음대로 정할 수 있다. 디스크상의 어느 디렉터리에 소스코드 파일을 위치시키든 관계없다. 하지만  
대부분의 경우 자바와 같이 패키지별로 디렉터리를 구성하는 편이 낫다. 


## 2.3 선택 표현과 처리: enum과 when
when은 자바의 swith를 대치하되 훨씬 더 강력하며, 앞으로 더 자주 사용할 프로그래밍 요소라고 생각할 수 있다. when에 대해 설명하는 과정에서 코틀린에서 enum을  
선언하는 방법과 스마트 캐스트에 대해서도 살펴본다.

#### 2.3.1 enum 클래스 정의
```kotlin
enum calss Color {
    RED, ORANGE, YELLOW
}
```

코틀린에서 enum은 `소프트 키워드`라 부르는 존재다. enum은 class 앞에 있을 때는 특별한 의미를 지니지만 다른 곳에서는 이름에 사용할 수 있다. 반면 class는  
키워드다. 따라서 class라는 이름을 사용할 수 없으므로 클래스를 표현하는 변수 등을 정의할 때는 clazz나 aClass와 같은 이름을 사용해야 한다.  
enum 클래스 안에도 프로퍼티나 메소드를 정의할 수 있다. 
```kotlin
enum class Color (val r: Int, val g: Int, val b: Int) {
    RED(255, 0, 0), ORANGE(255, 165, 0); // 여기 반드시 세미콜론을 사용해야 한다.
    
    fun rgb() = (r * 256 + g) * 256 + b
}
```

### 2.3.2 when으로 enum 클래스 다루기
자바의 swith에 해당하는 코틀린 구성요소는 when이다. if와 마찬가지로 when도 값을 만들어내는 식이다.
```kotlin
fun getMnemonic(color: Color) = 
    when (color) {
        Color.RED -> "Richard"
        Color.Orange -> "Of"
    }
```

자바와 달리 각 분기의 끝에 break를 넣지 않아도 된다. 성공적으로 매치되는 분기를 찾으면 switch는 그 분기를 실행한다. 한 분기 안에 여러 값을 매치 패턴으로  
사용할 수도 있다. 그럴 경우 값 사이를 콤마로 분리한다. 
```kotlin
fun getWarmth(color: Color) = when(color) {
    Color.RED, Color.ORANGE -> "warm"
    Color.BLUE -> "cold"
}
```

### 2.3.3 when과 임의의 객체를 함께 사용
자바 switch와 달리 when의 분기 조건은 임의의 객체를 허용한다. 두 색을 혼합했을 때 미리 정해진 팔레트에 들어있는 색이 될 수 있는지 알려주는 함수를 작성하자. 
```kotlin
fun mix(c1: Color, c2: Color) =
    when (setOf(c1, c2)) {
        setOf(RED, YELLOW) -> ORANGE
        else -> throw Exception("Dirty color")
    }
```

when의 분기 조건에 식을 넣을 수 있기 때문에 많은 경우 코드를 더 간결하고 아름답게 작성할 수 있다.

### 2.3.4 인자 없는 when 사용
이전의 함수는 호출될 때마다 함수 인자로 주어진 두 색이 when의 분기 조건에 있는 다른 두 색과 같은지 비교하기 위해 여러 Set 인스턴스를 생성하는데 이는 불필요한  
가비지 객체를 늘어나게 할 수 있다. 인자가 없는 when식을 사용하면 불필요한 객체 생성을 막을 수 있다. 코드는 약간 읽기 어려워지지만 성능을 더 향상시키지 위해  
그 정도 비용을 감수해야 하는 경우도 자주 있다.

```kotlin
fun mixOptimized(c1: Color, c2: Color) = 
    when {
        (c1 == RED && c2 == YELLOW) ||
                (c1 == YELLOW && c2 == RED) -> ORANGE
        else -> throw Exception("Dirty color")
    }
```

when에 아무 인자도 없으려면 각 분기의 조건이 불리언 결과를 계산하는 식이어야 한다.

### 2.3.5 스마트 캐스트: 타입 검사와 타입 캐스트를 조합
(1 + 2) + 4 와 같은 간단한 산술식을 계산하는 함수를 만들어보자. 우선 식을 인코딩하는 방법을 생각해야 한다. 식을 트리 구조로 저장하자. 노드는 합계나 수 중  
하나다. Num은 항상 말단 노드지만, Sum은 자식이 둘 있는 중간 노드다. Sum 노드의 두 자식은 덧셈의 두 인자다.  
클래스가 구현하는 인터페이스를 지정하기 위해서 콜론 뒤에 인터페이스 이름을 사용한다. Expr 인터페이스는 아무 메소드도 선언하지 않으며, 단지 여러 타입의 식 객체를  
아우르는 공통 타입 역할만 수행한다.
```kotlin
interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr
```

(1 + 2) + 4라는 식을 저장하면 Sum(Sum(Num(1), Num(2)), Num(4)) 라는 구조의 객체가 생긴다.  
Expr 인터페이스에는 두 가지 구현 클래스가 존재한다. 따라서 식을 평가하려면 두 가지 경우를 고려해야 한다.  
- 어떤 식이 수라면 그 값을 반환한다.  
- 어떤 식이 합계라면 좌항과 우항의 값을 계산한 다음에 그 두 값을 합한 값을 반환한다.  
자바 스타일로 작성한 함수를 먼저 살펴본 다음 코틀린 스타일로 만든 함수를 살펴보자.
  
```kotlin

 import java.lang.IllegalArgumentExceptionfun eval (e: Expr) : Int {
    if (e is Num) {
        val n = e as Num // 여기서 Num 타입을 변환하는데, 이는 불필요한 중복이다.
        return n.value
    }
    if (e is Sum) {
        return eval(e.right) + eval(e.left) // 변수 e에 대해 스마트 캐스트를 사용한다.
    }
    throw IllegalArgumentException("Unknown expression")
}
```

코틀린에서는 is를 사용해 변수 타입을 검사한다. 자바에서는 명시적으로 변수 타입을 캐스팅해야 하지만 코틀린에서는 프로그래머 대신 컴파일러가 캐스팅을 해준다. 어떤  
변수가 원하는 타입인지 일단 is로 검사하고 나면 굳이 변수를 원하는 타입으로 캐스팅하지 않아도 마치 처음부터 그 변수가 원하는 타입으로 선언된 것처럼 사용할 수 있다.  
하지만 실제로는 컴파일러가 캐스팅을 수행해준다. 이를 `스마트 캐스트`라고 부른다.  
원하는 타입으로 명시적으로 타입 캐스팅하려면 as 키워드를 사용한다. 
```kotlin
val n = e as Num
```

이제 eval 함수를 리팩토링해서 더 코틀린 다운 코드로 만드는 방법을 살펴보자.

### 2.3.6 리팩토링: if를 when으로 변경
코틀린에서는 if가 값을 만들어 내기 때문에 3항 연산자가 따로 없다. 이런 특성을 사용하면 eval 함수에서 return문과 중괄호를 없애고 if 식을 본문으로 사용해 더  
간단하게 만들 수 있다. 
```kotlin

 import java.lang.IllegalArgumentExceptionfun eval(e: Expr) : Int = 
    if (e is Num) {
        e.value
    } else if (e is Sum) {
        eval(e.right) + eval(e.left)
    } else {
        throw IllegalArgumentException("Unknown expression")
    }
```

if 분기에 식이 하나 밖에 없다면 중괄호를 생략해도 된다. if 분기에 블록을 사용하는 경우 그 블록의 마지막 식이 그 분기의 결과 값이다.

```kotlin

 import java.lang.IllegalArgumentExceptionfun eval(e: Expr) : Int = 
    when (e) {
        is Num -> e.value
        is Sum -> eval(e.right) + eval(e.left)
        else -> throw IllegalArgumentException("Unknown expression)
    }
```

### 2.3.7 if와 when의 분기에서 블록 사용
if나 when 모두 분기에 블록을 사용할 수 있다. 그런 경우 블록의 마지막 문장이 블록 전체의 결과가 된다. '블록의 마지막 식이 블록의 결과'라는 규칙은 블록이 값을  
만들어내야 하는 경우 항상 성립한다. 하지만 함수에서는 식이 본문이 함수는 블록을 본문으로 가질 수 없고 블록이 본문인 함수는 내부에 return 문이 반드시 있어야 한다.


## 2.4 대상을 이터레이션: while과 for 루프
코틀린 while 루프는 자바와 동일하다. for는 자바의 for-each 루프에 해당하는 형태만 존재한다. 코틀린의 for는 C#과 마찬가지로 for <아이템> in <원소들>  
형태를 취한다. 

### 2.4.1 while 루프
코틀린에는 while과 do-while 루프가 있다. 
```kotlin
while (조건) {
    
}

do {
    
} while (조건) // 맨 처음에 무조건 본문을 한 번 실행한 다음, 조건이 참인 동안 본문을 반복한다.
```

### 2.4.2 수에 대한 이터레이션: 범위와 수열
코틀린에는 자바의 for 루프(어떤 변수를 초기화하고 그 변수를 루프를 한 번 실행할 때마다 갱신하고 루프 조건이 거짓이 될 때 반복을 마치는 형태의 루프)에 해당하는  
요소가 없다. 이런 루프의 가장 흔한 용례인 초깃값, 증가 값, 최종 값을 사용한 루프를 대신하기 위해 코틀린에서는 범위를 사용한다.  
범위는 기본적으로 두 값으로 이뤄진 구간이다. 보통은 그 두 값은 정수 등의 숫자 타입의 값이며, .. 연산자로 시작과 끝 값을 연결해서 범위를 만든다.
```kotlin
val oneToTen = 1..10
```

코틀린의 범위는 폐구간 또는 양끝을 포함하는 구간이다. 어떤 범위에 속한 값을 일정한 순서로 이터레이션하는 경우를 `수열`이라고 부른다.

```kotlin
fun fizzBuzz(i: Int) = when {
    i % 15 == 0 -> "FizzBuzz "
    i % 3 == 0 -> "Fizz "
    i % 5 == 0 -> "Buzz "
    else -> "$i" // 다른 경우에는 그 수 자체를 반환한다.
}

>>> for (i in 1..100) {
    ...print(fizzBuzz(i))
}
}
```

```kotlin
>>> for (i in 100 downTo 1 step 2) // 100부터 거꾸로 세되 짝수로만 조회
```

증가 값을 사용하면 수를 건너 뛸 수 있다. 증가 값을 음수로 만들면 정방향 수열이 아닌 역방향 수열을 만들 수 있다. 100 downTo 1은 역방향 수열을 만든다(역방향  
수열의 기본 증가 값은 -1이다). 그 뒤에 step 2를 붙이면 증가 값의 절대값이 2로 바뀐다.  
앞에서 언급한 대로 ..는 항상 범위의 끝값을 포함한다. 하지만 끝 값을 포함하지 않는 반만 닫힌 범위에 대해 이터레이션하면 편할 때가 자주 있다. 그런 범위를 만들고  
싶다면 until 함수를 사용하라. for (x in 0 until size)라는 루프는 for (x in 0..size -1)과 같지만 좀 더 명확하게 개념을 표현한다.

### 2.4.3 맵에 대한 이터레이션
```kotlin
val binaryReps = TreeMap<Char, String>() // 키에 대해 정렬하기 위해 TreeMap을 사용한다.
for (c in 'A'..'F') {
    val binary = Integer.toBinaryString(c.toInt())
    binaryReps[c] = binary
}

for((letter, binary) in binaryReps) { // 맵의 키와 값을 두 변수에 각각 대입한다.
    println("$letter = $binary")
}
```

.. 연산자를 숫자 타입의 값뿐 아니라 문자 타입의 값에도 적용할 수 있다.  
맵에서 get과 put대신 map[key]나 map[key] = value를 사용해 값을 가져오고 설정할 수 있다.  
맵에 사용했던 구조 분해 구문을 맵이 아닌 컬렉션에도 활용할 수 있다. 그런 구조 분해 구문을 사용하면 원소의 현재 인덱스를 유지하면서 컬렉션을 이터레이션할 수 있다. 
```kotlin
val list = arrayListOf("10", "11", "1001")
for ((index, element) in list.withindex()) {
    println("$index: $element")
}
```

### 2.4.4 in으로 컬렉션이나 범위의 원소 검사
in 연산자를 사용해 어떤 값이 범위에 속하는지 검사할 수 있다. 반대로 !in을 사용하면 어떤 값이 범위에 속하지 않는지 검사할 수 있다.
```kotlin
fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'
fun isNotDigit(c: Char) = c !in '0'..'9'
```

이 비교 로직은 표준 라이브러리의 범위 클래스 구현 안에 깔끔하게 감춰져 있다.  
```kotlin
c in 'a'..'z' // 'a' <= c && c <= 'z'
```

in과 !in 연산자를 when 식에서 사용해도 된다.
```kotlin
fun recognize(c: Char) = when (c) {
    in '0'..'9' -> "It's a digit!"
    in 'a'..'z', in 'A'..'Z' -> "It's a letter!"
    else -> "I don't know"
}
```
