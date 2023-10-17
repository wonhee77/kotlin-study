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

