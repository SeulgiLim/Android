package com.example.myapplication.Kotlin

// + 만들기
fun plusThree(first: Int, second:Int, third:Int) : Int{
//    val result = first + second + third
//    return result

//    위의 코드 줄여서 사용 가능함
    return first + second + third
}

// 간단하게 사용하는 방법 응용하여 - 만들기
fun minusThree(first: Int, second: Int, third: Int) = first - second - third


// 디폴트 값을 사용하는 함수 응용하여 만들기
fun multiplyThree(first: Int = 1, second: Int = 1, third: Int = 1) : Int {
    return first * second * third
}


// 내부 함수 - 자주 사용안되나 어떻게 동작하는지 알아야함
// - 함수 안에 함수가 있다!
// ctrl + 인자 클릭 = 관련 코드로 이동함
fun showMyPlus(first: Int, second: Int): Int {
    println(first)
    println(second)

    //  return first + second (기존에 이렇게 끝냄)

    fun plus(first: Int, second: Int) : Int {
        return first + second
    }
    return plus(first, second)
}

fun main(array: Array<String>){
    val result = plusThree(1,2,3)
    println(result)

    val result2 = minusThree(10, 1, 2)
    println(result2)

    val result3 = multiplyThree(2, 2, 2)
    println(result3)

    val result4 = multiplyThree()
    println(result4)

    println()
    val result5 = showMyPlus(4,5)
    println(result5)
}