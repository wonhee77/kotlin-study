package com.dietmate.dietmate

import com.dietmate.dietmate.files.ExtensionProperty
import com.dietmate.dietmate.files.getTwo
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class LeaningTest {

    @Test
    @DisplayName("String template test")
    fun stringTemplate() {
        val name = "wonhee"
        println("My name is $name")
    }

    @Test
    @DisplayName("function의 매개 변수는 val이다.")
    fun functionParameterTest() {
//        fun valTypeParameter(name: String) {
//            name = "new name" //val can not be assigned error
//        }
    }

    @Test
    @DisplayName("객체 인스턴스 생성에는 new가 필요하지 않다.")
    fun classTest_unnecessary_new_keyword() {
        val person = Person("wonhee", 1)
    }

    @Test
    @DisplayName("컴팩트 객체는 getter, setter, 모든 파라미터를 가진 생성자를 가진다.")
    fun classTest_compact_class() {
        val name = "wonhee"
        val person = Person(name, 1)
        assertThat(person.name).isEqualTo(name)
    }

    class Person(val name: String, val age: Int)

    @Test
    @DisplayName("객체 생성 파라미터가 var인 경우 set을 할 수 있다.")
    fun classTest_varParameter() {
        val person = Person2("name ", true)
        person.isMarried = false
        assertThat(person.isMarried).isFalse()
    }

    class Person2(val name: String, var isMarried: Boolean)

    @Test
    @DisplayName("enum 생성 테스트")
    fun enumTest() {
        val red = Fruit.BANANA
        assertThat(red.getSum()).isEqualTo(11)
    }

    enum class Fruit(val min: Int, val max: Int) {
        BANANA(1, 10), APPLE(2, 4);

        fun getSum() = min + max
    }

    @Test
    @DisplayName("when 테스트")
    fun whenTest() {
        val fruit = Fruit.APPLE
        val result = when (fruit) {
            Fruit.BANANA -> "banana"
            Fruit.APPLE -> "apple"
        }
        assertThat(result).isEqualTo("apple")
    }

    interface RemoteController

    class SamsungController(val name: String, val price: Int) : RemoteController
    class LGController(val name: String, val size: Int) : RemoteController

    @Test
    @DisplayName("타입 검사를 마치면 명시적 타입 캐스트가 필요하지 않다.")
    fun smartCast() {
        val remoteController = SamsungController("samsung", 100)
        if (remoteController is RemoteController) {
            val price = remoteController.price
            assertThat(price).isEqualTo(100)
        }
    }

    @Test
    @DisplayName("for iterator")
    fun iterator() {
        var cnt = 0
        for (i in 1..10) {
            cnt++
        }
        assertThat(cnt).isEqualTo(10)
    }

    @Test
    @DisplayName("map iteration")
    fun mapIterator() {
        val myMap = HashMap<Int, String>()
        myMap[1] = "one"
        myMap[2] = "two"
        myMap[1] = "three"
        assertThat(myMap).hasSize(2)
        assertThat(myMap[1]).isEqualTo("three")
        for ((index, value) in myMap) {
            println("index = $index")
            println("value = $value")
        }
    }

    @Test
    @DisplayName("range 검사")
    fun rangeCheck() {
        val range = 1..10
        assertThat(1 in range).isTrue()
        assertThat(10 in range).isTrue()
        assertThat(11 in range).isFalse()
    }

    @Test
    @DisplayName("확장 프로퍼티")
    fun extensionProperty() {
        val extensionProperty = ExtensionProperty()
        assertThat(extensionProperty.getTwo()).isEqualTo(2)
    }

    private fun localFunction(firstName: String, middleName: String, lastName: String): String {
        fun concatenateWithBlank(val1: String, val2: String) = val1 + " " + val2

        return concatenateWithBlank(concatenateWithBlank(firstName, middleName), lastName)
    }

    @Test
    @DisplayName("로컬 함수")
    fun localFunctionTest() {
        val fullName = localFunction("a", "b", "c")
        assertThat(fullName).isEqualTo("a b c")
    }

    @Test
    @DisplayName("커스텀 접근자")
    fun customGetter() {
        val rectangular1 = Rectangular(1, 2)
        assertThat(rectangular1.height).isEqualTo(1)
        assertThat(rectangular1.width).isEqualTo(2)
        assertThat(rectangular1.isSquare).isFalse()

        val rectangular2 = Rectangular(2, 2)
        assertThat(rectangular2.height).isEqualTo(2)
        assertThat(rectangular2.width).isEqualTo(2)
        assertThat(rectangular2.isSquare).isTrue()
    }

    class Rectangular(val height: Int, val width: Int) {
        val isSquare: Boolean
            get() = height == width
    }
}