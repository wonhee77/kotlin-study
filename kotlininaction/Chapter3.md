# 3장 함수 정의화 호출
3장에서는 모든 프로그램에서 핵심이라 할 수 있는 함수 정의와 호출 기능을 코틀린이 어떻게 개선했는지 살펴본다. 추가로 확장 함수와 프로퍼티를 사용해 자바 라이브러리를  
코틀린 스타일로 적용하는 방법을 살펴본다. 

## 3.1 코틀린에서 컬렉션 만들기
숫자로 이뤄진 집합을 만들어보자.
```kotlin
val set = hashSetOf(1, 7, 53)
```

비슷한 방법으로 리스트와 맵도 만들 수 있다.
```kotlin
val list = arrayListOf(1, 7, 53)
val map = hashMapof(1 to "one", 7 to "seven", 53 to "fifty-three")
```

여기서 to가 언어가 제공하는 특별한 키워드가 아니라 일반 함수라는 점에 유의하라.
```kotlin
>>> println(set.javaClass) // javaClass는 자바 getClass()에 해당하는 코틀린 코드다.
class java.util.HashSet

>>> println(list.javaClass)
class java.util.ArrayList

>>> println(map.javaClass)
class java.util.HashMap
```

이는 코틀린이 자신만의 컬렉션 기능을 제공하지 않는다는 뜻이다. 코틀린이 자체 컬렉션을 제공하지 않는 이유는 표준 자바 컬렉션을 활용하면 자바 코드와 상호작용하기가  
훨씬 쉽다. 자바와 코틀린 컬렉셔능ㄹ 서로 변환할 필요도 없다.  
코틀린 컬렉션은 자바 컬렉션과 똑같은 클래스지만 리스트의 마지막 원소를 가져오거나 수로 이뤄진 컬렉션에서 최댓값을 찾는 등 더 많은 기능을 쓸 수 있다.
```kotlin
>>> val strings = listOf("first", "second", "fourteenth")
>>> println(strings.last())
frounteenth

>>> val numbers = setOf(1, 14, 2)
>>> println(numbers.max())
14
```


## 3.2 함수를 호출하기 쉽게 만들기
자바 컬렉션에는 디폴트 toString 구현이 들어있다. 하지만 그 디폴트 toString의 출력 형식은 고정돼 있고 우리에게 피룡한 형식이 아닐 수도 있다.
```kotlin
>>> val list = listOf(1, 2, 3)
>>> println(list)
[1, 2, 3]
```

디폴트 구현과 달리 (1; 2; 3) 처럼 원소 사이를 세미콜론으로 구분하고 괄호로 리스트를 둘러싸고 싶다면 어떻게 해야 할까? 코틀린에서는 이런 요구 사항을 처리할 수  
있는 함수가 표준 라이브러리에 이미 들어 있다.  
다음 리스트의 joinToString 함수는 컬렉션의 원소를 StringBuilder의 뒤에 덧붙인다. 이때 원소 사이에 구분자를 추가하고, StringBuilder의 맨 앞과 맨   
뒤에는 접두사와 접미사를 추가한다.
```kotlin
fun <T> joinToString(collection: Collection<T>, separator: String, prefix: String, postfix: String) : String {
    val result = StringBuilder(prefix)
    for ((index, element) in collection.withIndex()) {
        if (index > 0) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}
```

이 함수는 제네릭하다. 어떻게 하면 이 함수를 호출하는 문장을 덜 번잡하게 만들 수 있을까? 함수를 호출할 때마다 매번 네 인자를 모두 전달하지 않을 수는 없을까?

### 3.2.1 이름 붙인 인자
해결하고픈 첫 번째 문제는 함수 호출 부분의 가독성이다.
```kotlin
joinToString(collection, "", "", ".")
```

인자로 전달한 각 문자열이 어떤 역할을 하는지 구분하기 어렵다. 자바에서는 시그니처를 외우거나 IDE의 도움을 받는다.  
코틀린에서는 아래와 같은 방식으로 해결할 수 있다.
```kotlin
joinToString(collection, separator = " ", prefix = " ", postfix = ".")
```

코틀린으로 작성한 함수를 호출할 때는 함수에 전달하는 인자 중 일부의 이름을 명시할 수 있다. 호출 시 인자 중 어느 하나라도 이름을 명시하고 나면 혼동을 막기 위해 그  
뒤에 오는 모든 인자는 이름을 꼭 명시해야 한다. 

### 3.2.2 디폴트 파라미터 값
자바에서는 일부 클래스에서 오버로딩한 메소드가 너무 많아진다는 문제가 있다. 코틀린에서는 함수 선언에서 파라미터 디폴트 값을 지정할 수 있으므로 이런 오버로드 중  
상당수를 피할 수 있다. 디폴트 값을 사용해 joinToString 함수를 개선해보자. 대부분의 경우 아무 접두사나 접미사 없이 콤마로 원소를 구분한다.

```kotlin
fun <T> joinToString(collection: Collectoin<T>, separator: String = ", ", prefix: String = "". postfix: String= "")
    : String 
```

이제 함수를 호출할 때 모든 인자를 쓸 수도 있고, 일부를 생략할 수도 있다.
```kotlin
>>> joinToString(list, ", ", "", "")
>>> joinToString(list)
>>> joinToString(lsit, "; ")

```

일반 호출 문법을 사용하려면 함수를 선언할 때와 같은 순서로 인자를 지정해야 한다. 이름이 붙은 인자를 사용하는 경우에는 인자 목록의 중간에 있는 인자를 생략하고,  
지정하고 싶은 인자를 이름을 붙여서 순서와 관계없이 지정할 수 있다.
```kotlin
>>> joinToString(list, postfix = ";". prefix = "# ")
```

함수의 디폴트 파라미터 값은 함수를 호출하는 쪽이 아니라 함수 선언 쪽에서 지정된다는 사실을 기억하라.

### 3.2.3 정적인 유틸리티 클래스 없애기: 최상위 함수와 프로퍼티
자바에서 어느 한 클래스에 포함시키기 어려운 코드가 많이 생긴다. 일부 연산에는 비슷하게 중요한 역할을 한느 클래스가 둘 이상 있을 수도 있다. 중요한 객체는  
하나뿐이지만 그 연산을 객체의 인스턴스 API에 추가해서 API를 너무 크게 만들고 싶지는 않은 경우도 있다.  
그 결과 다양한 정적 메소드를 모아두는 역할만 담당하며, 특별한 상태아 인스턴스 메소드는 없는 클래스가 생겨난다.  
코틀린에서는 함수를 직접 소스 파일의 최상위 수준, 모든 다른 클래스 밖에 위치시키면 된다. joinToString 함수를 strings 패키지에 직접 넣어보자. join.kt라는  
파일을 다음과 같이 작성하라.
```kotlin
package strings
fun joinToString(...): String {...}
```

JVM이 클래스 안에 들어있는 코드만을 실행할 수 있기 때문에 컴파일러는 이 파일을 컴파일할 때 새로운 클래스를 정의해준다. 코틀린만 사용하는 경우엔느 그냥 그런 클래스가  
생긴다는 사실만 기억하면 된다. 하지만 이 함수를 자바 등의 다른 JVM 언어에서 호출하고 싶다면 코드가 어떻게 컴파일되는지 알아야 joinToString과 같은 최상위 함수를  
사용할 수 있다.  
join.kt를 컴파일한 결과와 같은 클래스를 자바 코드로 써보면 다음과 같다.
```java
pacakge strings;

public class JoinKt {
    public static String joinToString(...) {...}
}
```

코틀린 컴파일러가 생성하는 클래스의 이름은 최상위 함수가 들어있던 코틀린 소스파일 이름과 대응한다. 코틀린 팔의 모든 최상위 함수는 이 클래스의 정적인 메소드가 된다.

최상위 프로퍼티  
함수와 마찬가지로 프로퍼티도 파일의 최상위 수준에 놓을 수 있다. 예를 들어 어떤 연산을 수행한 횟수를 저장하는 var 프로퍼티를 만들 수 있다.
```kotlin
var opCount = 0
fun performOperation() {
    opCount++
    }
```

이런 프로퍼티 값은 정적 필드에 저장된다. 최상위 프로퍼티를 활용해 코드에 상수를 추가할 수 있다.
```kotlin
val UNIX_LINE_SEPARATOR = "\n"
```

기본적으로 최상위 프로퍼티도 다른 모든 프로퍼티처럼 접근자 메소드를 통해 자바 코드에 노출된다. const 변경자를 추가하면 프로퍼티를 public static final 필드로  
컴파일하게 만들 수 있다(단, 원시 타입과 String 타입의 프로퍼티만 const로 지정할 수 있다).
```kotlin
const val UNIX_LINE_SEPARATOR = "\n"
```

