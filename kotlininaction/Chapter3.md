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


## 3.3 메소드를 다른 클래스에 추가: 확장 함수와 확장 프로퍼티
기존 자바 API를 재작성하지 않고도 코틀린이 제공하는 여러 편리한 기능을 사용할 수 있다면 정말 좋을 일 아닐까? 바로 `확장 함수`가 그런 역할을 해줄 수 있다.  
확장 함수는 어떤 클래스의 멤버 메소드인 것처럼 호출할 수 있지만 그 클래스의 밖에 선언된 함수다. 확장 함수를 보여주기 위해 어떤 문자열의 마지막 문자를 돌려주는  
메소드를 추가해보자.
```kotlin
package strings
fun String.lastChat() : Char = this.get(this.length - 1)
```

확장 함수를 만들려면 추가하려는 함수 이름 앞에 그 함수가 확장할 클래스의 이름을 덧붙이기만 하면 된다. 클래스 이름을 `수신 객체 타입`이라 부르며, 확장 함수가  
호출되는 대상이 되는 값을 `수신 객체`라고 부른다.
```kotlin
>>> println("Kotlin".lastChar())
```

이 예제에서는 String이 수신 객체 타입이고 "kotlin"이 수신 객체다.  
어떤 면에서 이는 String 클래스에 새로운 메소드를 추가하는 것과 같다. String 클래스가 직접 작성한 코드가 아니고 심지어 String 클래스의 소스코드를 소유한 것도  
아니지만, 여전히 원하는 메소드를 String 클래스에 추가할 수 있다. 자바 클래스로 컴파일한 클래스 파일이 있는 한 그 클래스에 원하는 대로 확장을 추가할 수 있다.  
하지만 확장 함수가 캡슐화를 깨지는 않는다. 확장 함수 안에서는 클래스 내부에서만 사용할 수 있는 비공개 멤버나 보호된 멤버를 사용할 수 없다.  
이제부터는 클래스의 멤버 메소드와 확장 함수를 모두 메소드라고 부를 것이다.

### 3.3.1 임포트와 확장 함수
확장 함수를 정의했다고 해도 자동으로 프로젝트 안의 모든 소스코드에서 그 함수를 사용할 수 있지는 않다. 확장함수를 사용하기 위해서는 임포트를 해야만 한다. 확장 함수를  
정의하자마자 어디서든 그 함수를 쓸 수 있다면 한 클래스에 같은 이름의 확장 함수가 둘 이상 있어서 이름이 충돌하는 경우가 자주 생길 수 있다.
```kotlin
import strings.lastChar

val c = "Kotlin".lastChar()
```

as 키워드를 사용하면 임포트한 클래스나 함수를 다른 이름으로 부를 수 있다.
```kotlin
import strings.lastChat as last
val c = "Kotlin".last()
```

한 파일 안에서 다른 여러 패키지에 속해있는 이름이 같은 함수를 가져와 사용해야 하는 경우 이름을 바꿔서 임포트하면 이름 충돌을 막을 수 있다. 

### 3.3.2 자바에서 확장 함수 호출
내부적으로 확장 함수는 수신 객체를 첫 번째 인자로 받는 정적 메소드다. 확장 함수를 StringUtil.kt 파일에 정의했다면 다음과 같이 호출할 수 있다.
```java
char c = StringUtilKt.lastChar("Java");
```

### 3.3.3 확장 함수로 유틸리티 함수 정의
```java
fun <T> Collection<T>.joinToString( // Collection<T>에 대한 확장 함수를 선언한다.
    separator: String = ", ",
    prefix: String = "",
    postfix: String = ""
    ) : String {
        val rsult = StringBuilder(prefix)
    for ((index, element) in this.withIndex())
        if (index > 0 ) result.append(separator)
        result.append(element)
    }
    result.append(postfix)
    return reusult.toString()
```

원소로 이뤄진 컬렉션에 대한 확장을 만든다. 그리고 모든 인자에 대한 디폴트 값을 지정한다. 이제 joinToString을 마치 클래스의 멤버인 것처럼 호출할 수 있다.
```java
>>> val list = arrayListOf(1, 2, 3)
>>> println(list.joinToString(" "))
```

확장 함수는 단지 정적 메소드 호출에 대한 문법적인 편의일 뿐이다. 그래서 클래스가 아닌 더 구체적인 타입을 수신 객체 타입으로 지정할 수도 있다. 그래서 문자열의  
컬렉션에 대해서만 호출할 수 있는 join 함수를 정의하고 싶다면 다음과 같이 하면 된다.
```java
fun Collection<String>.join()
```

### 3.3.4 확장 함수는 오버라이드 할 수 없다
확장 함수는 오버라이드 할 수 없다. View와 그 하위 클래스인 Button이 있는데 Button이 상위 클래스의 click 함수를 오버라이드하는 경우를 생각해보자.
```java
open class View {
    open fun click() = println("View clicked")
}

class Button: View() {
    override fun click() = println("Button clicked")
}
```

Button이 View의 하위 타입이기 때문에 View 타입 변수를 선언해도 Button 타입 변수를 그 변수에 대입할 수 있다.  
하지만 확장 함수는 클래스의 일부가 아니다. 확장 함수는 클래스 밖에 선언된다. 코틀린은 호출될 확장 함수를 정적으로 결정한다.

* 어떤 클래스를 확장한 함수와 그 클래스의 멤버 함수의 이름과 시그니처가 같다면 멤버 함수가 호출된다.

### 3.3.5 확장 프로퍼티
확장 프로퍼티를 사용하면 기존 클래스 객체에 대한 프로퍼티 형식의 구문으로 사용할 수 있는 API를 추가할 수 있다. 프로퍼티라는 이름으로 불리기는 하지만 상태를 저장할  
적절한 방법이 없기 때문에 실제로 확장 프로퍼티는 아무 상태도 가질 수 없다. 하지만 프로퍼티 문법으로 더 짧게 코드를 작성할 수 있어서 편한 경우가 있다.  
앞 절에서 lastChar라는 함수를 정의했다. 이제 그 함수를 프로퍼티로 바꾸자.
```java
val String.lastChar: char
    get() = get(length - 1)
```

확장 함수의 경우와 마찬가지로 확장 프로퍼티도 일반적인 프로퍼티와 같은데 단지 수신 객체 클래스가 추가됐을 뿐이다. 뒷받침한느 필드가 없어서 기본 게터 구현을 제공할  
수 없으므로 최소한의 게터는 꼭 정의를 해야 한다. 마찬가지로 초기화 코드에서 계산한 값을 담을 장소가 전혀 없으므로 초기화 코드도 쓸 수 없다.  
StringBuilder에 같은 프로퍼티를 정의한다면 StringBuilder의 맨 마지막 문자는 변경 가능하므로 프로퍼티를 var로 만들 수 있다.
```java
var StringBuilder.lastChar: Char
    get() = get(length - 1)
    set(value: Char) {
        this.setChatAt(length - 1, value)
    }
```


## 3.4 컬렉션 처리: 가변 길이 인자, 중위 함수 호출, 라이브러리 지원
이번 절은 컬렉션을 처리할 때 쓸 수 있는 코틀린 표준 라이브러리 함수 몇 가지를 보여준다.
- vararg 키워드를 사용하면 호출 시 인자 개수가 달라질 수 있는 함수를 정의할 수 있다.  
- 중위함수 호출 구문을 사용하면 인자가 하나뿐인 메소드를 간편하게 호출할 수 있다.  
- 구조 분해 선언을 사용하면 복합적인 값을 분해해서 여러 변수에 나눠 담을 수 있다. 

### 3.4.1 자바 컬렉션 API 확장
기존에 리스트에서 마지막 원소를 가져오는 예제와 숫자로 이뤄진 컬렉션의 최댓값을 찾는 예제에서 last와 max는 모두 확장 함수였다. 

### 3.4.2 가변 인자 함수: 인자의 개수가 달라질 수 있는 함수 정의
```kotlin
val list = listOf(1, 2, 3)

fun listOf<T>(vararg values: T): List<T> {...}
```

자바에서 가변 길이 인자를 타입 뒤에 ...를 붙이는 것처럼 코틀린에서는 파라미터 앞에 vararg 변경자를 붙인다.  
이미 배열에 들어있는 원소를 가변 길이 인자로 넘길 때도 코틀린과 자바 구문이 다르다. 자바에서는 배열을 그냥 넘기면 되지만 코틀린에서는 배열을 명시적으로 풀어서 배열의  
각 원소가 인자로 전달되게 해야 한다. 기술적으로는 `스프레드 연산자`가 그런 작업을 해준다. 하지만 실제로는 전달하려는 배열 앞에 *를 붙이기만 하면 된다.
```kotlin
fuc main(args: Array<String>) {
    val list = listOf("args: ", *args)
    print(list)
}
```

### 3.4.3 값의 쌍 다루기: 중위 호출과 구조 분해 선언
맵을 만들려면 mapOf 함수를 사용한다.  
```kotlin
val map = mapOf(1 to "one", 7 to "seven")
```

여기서 to라는 단어는 코틀린 키워드가 아니다. 이 코드는 `중위 호출`이라는 특별한 방식으로 to라는 일반 메소드를 호출한 것이다.  
중위 호출 시에는 수식 객체와 유일한 메소드 인자 사이에 메소드 이름을 넣는다. 다음 두 호출은 동일하다.
```kotlin
1.to("one")
1 to "one"
```
인자가 하나뿐인 일반 메소드나 인자가 하나뿐인 확장 함수에 중위 호출을 사용할 수 있다. 함수를 중위 호출에 사용하게 허용하고 싶으면 infix 변경자를 함수 선언 앞에  
추가해야 한다.
```kotlin
infix fun Any.to(other: Any) = Pair(this, other)
```

이 to 함수는 Pair의 인스턴스를 반환한다. Pair는 코틀린 표준 라이브러리 클래스로, 그 이름대로 두 원소로 이뤄진 순서쌍을 표현한다.  
Pair의 내용으로 두 변수를 즉시 초기화 할 수 있다.
```kotlin
val (number, name) = 1 to "one"
```

이런 기능을 `구조 분해 선언`이라고 부른다.  
to 함수는 확장 함수다. to를 사용하면 타입과 관계없이 임의의 순서쌍을 만들 수 있다. 이는 to의 수신 객체가 제네릭하다는 뜻이다.  
```kotlin
fun <K, V> mapOf(vararg values: Pair<K, V>) : Map<K, V>
```

