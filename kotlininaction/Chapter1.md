# 1장 코틀린이란 무엇이며, 왜 필요한가?

## 1.1 코틀린 맛보기
Person이라는 클래스를 정의, 컬렉션을 만들고 나이가 가장 많은 사람을 찾아 결과를 출력한다.
```kotlin
data class Person(val name: String, val age: Int? = null)

fun main(args: Array<String>) {
    val persons = listOf(Person("영희"), Person("철수", age = 29))
    val oldest = persons.maxBy {it.age ?: 0}
    println("나이가 가장 많은 사람 : $oldest")
}

// 결과: 나이가 가장 많은 사람: Person(name=철수, age=29)
```

age의 default 값은 따로 정의하지 안으면 null이다. maxBy 함수에 전달한 람다식은 파라미터를 하나 받는다. it이라는 이름을 사용하면 람다 식의 유일한 인자를  
사용할 수 있다. 엘비스 연산자라고 부르는 ?:는 age가 null인 경우 0을 반환하고, 그렇지 않은 경우 age의 값을 반환한다. 

