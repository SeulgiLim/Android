package kr.go.mapo.android

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


// Application 상속 받기



class MasterApplication : Application() {

    lateinit var service : RetrofitService

    // 1. build.gradle (:app)에 facebook.stetho 추가
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        createRetrofit()

        //chrome://inspect/#devices

    }

    // 2.Retrofit 작성
    fun createRetrofit(){
        val header = Interceptor {
            val original = it.request()


            // 4.login check 하기 위해 함수 추가
            if(checkIsLogin()) {

                // 6. UserToken 불러주기
                    getUserToken()?.let { token ->
                        val requeset = original.newBuilder()
                            .header("Authorization", "token " + token)
                            .build()
                        it.proceed(requeset)
                    }

            }else{
                it.proceed(original)
            }

        }
        val client = OkHttpClient.Builder()
            .addInterceptor(header)
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()


        //service = retrofit.create(RetrofitService::class.java)
        service = retrofit.create(RetrofitService::class.java)
    }

    // 3. login check 함수 만들기
    fun checkIsLogin() : Boolean {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val token = sp.getString("login_sp","null")
        if (token != "null") return true
        else return false
    }

    // 5. Token 값 함수 만들기
    fun getUserToken(): String? {                                              // 로그인이 안된 경우 Null 허용 하기 위해 String 옆에 ? 넣기
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val token = sp.getString("login_sp", "null")
        if (token == "null")return null
        else return token
    }
}



