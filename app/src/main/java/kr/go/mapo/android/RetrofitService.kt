package kr.go.mapo.android


import retrofit2.Call
import retrofit2.http.GET
import java.util.*


// 2. RetrofitService
// 주소의 변하는 부분 관리
interface RetrofitService{

    // @ : 어노테이션

    @GET("json/studnets/")
    fun getStudentsList() : Call<ArrayList<PersonFromServer>>
}