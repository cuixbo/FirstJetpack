package com.example.firstapp

import android.view.View
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test() {
        val typeFa = Int::fa
        val typeFb = ::fb

        f1(typeFa)
        f1(typeFb)

        f2(typeFa)
        f2(typeFb)
        //        foo(3);
//        boo2(3)
        foo2(1) // () -> Unit
        foo2(1).invoke() // Unit

        foo3(1) // Int->Int
        foo3(1).invoke(2) // Int
        foo3(1)(2) // Int


        foo4(4, strings = arrayOf("a", "b", "c"))
        foo5("he", 3)
        "jj".foo5(4)

    }


}

fun Int.fa(x: Int): String {
    return ""
}

fun fb(receiver: Int, x: Int): String {
    return ""
}

fun f1(v: Int.(Int) -> String) {
    println("f1")
}

fun f2(v: (Int, Int) -> String) {
    println("f2")
}

val sum: (Int, Int) -> Int = fun(x: Int, y: Int): Int {
    return x + y
}

// （等号加花括号） 这种叫 lambda表达式
val sum2: (Int, Int) -> Int = { x, y -> x + y }

// （没有等号）代码块函数体
fun foo(a: Int) {
    println(a)
}

// （有等号）单表达式函数体
fun foo1(a: Int) = println(a)

fun boo(a: Int): () -> Int {
    return fun(): Int {
        return a + 1
    }
}

fun boo1(a: Int): () -> Int {
    return {
        a + 1
    }
}

// （等号加花括号） 这种叫 lambda表达式
val sum3: (Int, Int) -> Int = { x, y -> x + y }

// （等号加花括号）这种叫 lambda表达式函数体
// fun关键字声明的lambda表达式 类型是什么？ "Int -> ( () -> Unit )"
fun foo2(a: Int) = { ->
    println(a)
}

// fun关键字声明的lambda表达式 类型是什么？ "Int -> ( Int -> Int )"
fun foo3(a: Int) = { b: Int ->
    a + b
}

// 普通接口

interface IntPredicate2 {
    fun accept(i: Int): Boolean
}

private fun predicateTest2() {
    // 创建一个IntPredicate2类的实例
    val isEven = object : IntPredicate2 {
        override fun accept(i: Int): Boolean {
            return i > 0
        }
    }
}

// SAM-Single Abstract Method

// fun关键字修饰的接口是 "函数式接口"，只能有一个抽象成员（可以有多个非抽象成员）
fun interface IntPredicate {
    fun accept(i: Int): Boolean
}

private fun predicateTest() {
    // 创建一个IntPredicate类的实例，可以使用lambda表达式来实现SAM转换
    val isEven = IntPredicate { it > 0 }
}

// 扩展函数
fun View.isRealyVisible(): Boolean {
    return true
}

class Parent {
    val name = "qq"

    // 这个是嵌套类
    class Nest {
        fun show() = println("name")    // 报错
    }

    // 这个是内部类 使用inner关键字
    inner class Inner {
        fun show() = println(name)    // OK
    }
}

/**
 *
 */
fun parentTest() {
    Parent.Nest().show()
    Parent().Inner().show()
}

fun foo4(name: Int, vararg strings: String) { /*……*/
//        val ff: List<Int>
//        ff.forEach()
}

val foo5: String.(a: Int) -> Boolean = { x ->
    this.length > x;
}

val sum7: Int.(Int) -> Int = { other -> this.plus(other) }
val sum8 = fun Int.(other: Int): Int = this + other

inline fun foo6(action: () -> Unit): Unit {
    action()
}

inline fun foo8(action: (Int) -> Boolean): Unit {
    action(4)
}

fun foo7() {
    println("foo7")
    foo6 {
        println("foo6")
        return@foo6
    }
    // 代码块函数体
    foo6(fun() { println("hh") })

    // 单表达式函数体
    foo6(fun() = println("hh"))
    foo8(fun(item) = item > 0)
}