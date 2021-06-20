package kr.go.mapo.fast_android

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity



class SharedPreferenceActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_preference)


        //SharedPreference
        val sharedPreference = getSharedPreferences("sp1", Context.MODE_PRIVATE)
        //Mode
        // - MODE_PRIVATE : 생성한 application에서만 사용 가능
        // - MODE_WORLD_READABLE : 다른 application 사용가능 -> 읽을수만 있다.
        // - MODE_WORLD_WRITEABLE : 다른 application 사용가능 -> 기록도 가능
        // - MODE_MULTI_PROCESS : 이미 호출되어 사용중인지 체크
        // - MODE_APPEND : 기존 preference에 신규로 추가

        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putString("hello", "안녕하세요")
        editor.commit()

        //sp1 -> Sharedpreference  : 이름 넣는 이유 구분하기 위해서 sp1, sp2 기입
        //      (Key, Value) -> ("Hello". "안녕하세요")
        //sp2 -> Sharedpreference
        //      (Key, Value) -> ("Hello", "안녕하세욬")
    }
}