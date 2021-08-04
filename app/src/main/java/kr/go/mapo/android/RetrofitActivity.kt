package kr.go.mapo.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class RetrofitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

//        http://mellowcode.org/json/studnets
//        http://mellowcode.org/test/code

        // 1. Retrofit 만들기 -> 2. RetrofitService Interface로 생성
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")                  // 주소 중 변하지 않는 부분
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // 3. Retrofit에 RetrofitService 연결
        val service = retrofit.create(RetrofitService::class.java)

        service.getStudentsList().enqueue(object : Callback<ArrayList<PersonFromServer>>{
            override fun onFailure(call: Call<ArrayList<PersonFromServer>>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(
                    call: Call<ArrayList<PersonFromServer>>,
                    response: Response<ArrayList<PersonFromServer>>
            ) {
                if (response.isSuccessful){
                    val personList = response.body()
                }
            }
        })


    }
}