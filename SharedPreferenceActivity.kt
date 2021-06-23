package kr.go.mapo.fast_android

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_shared_preference.*

class SharedPreferenceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_preference)

        // Pomodoro / Stopwatch 내  override fun onStop(), onStart()
        // 에러 잡을 때 사용해야하는 개념임

        /*override fun onStop() {
            super.onStop()
            val prefs =
                    activity!!.getSharedPreferences(POM_PREFS, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putLong(START_MILLIS, startTimeInMillis)
            editor.putLong(MILLIS_LEFT, timeLeftInMillis)
            editor.putBoolean(TIMER_RUNNING, isTimerRunning)
            editor.putLong(END_TIME, endTime)
            editor.apply()
            if (countdownTimer != null) {
                countdownTimer!!.cancel()
            }
        }*/

        // SharedPreference
        // 1. SharedPreference에 값을 저장하는 방법
        val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)

        // Mode
        // - MODE_PRIVATE         : 생성한 application에서만 사용가능 ★ 많이 사용!!!!
        // - MODE_WORLD_READBLE   : 다른 application 사용가능     -> 읽을 수 만 있다
        // - MODE_WORLD_WRITEABLE : 다른 application 사용가능     -> 기록도 가능
        // - MODE_MULTI_PROCESS   : 이미 호출되어 사용중인지 체크
        // - MODE_APPEND          : 기존 preference에 신규로 추가

//        val editor: SharedPreferences.Editor = sharedPreferences.edit()
//        editor.putString("hello1", "안녕하세요")    // editor를 통해 data를 넣어줌
//        editor.commit()                           // commit을 지워도 이미 저장된 값은 나옴

        //sp1 -> Sharedpreference
        //      (Key, Value)    ->  ("Hello", "안녕하세요")
        //sp2 -> Sharedpreference
        //      (Key, Value)    ->  ("Hello", "안녕하세요!!)

        save_btn.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("hello", "안녕하세요~")
            editor.putString("goodbye", "안녕히가세요!")
            editor.commit()
        }

        load_btn.setOnClickListener {
            // 2. SharedPreference에 값을 불러오는 방법
            val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
            val value1 = sharedPreferences.getString("hello", "데이터 없음")
            val value2 = sharedPreferences.getString("goodbye", "데이터 없음")
            Log.d("key-value","Value 1 : " + value1)
            Log.d("key-value","Value 2 : " + value2)
        }

        delete_btn.setOnClickListener {
            val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.remove("hello")      // remove : 특정 Key-Value 값만 지워줌
            editor.commit()
        }

        delete_all_btn.setOnClickListener {
            val sharedPreferences = getSharedPreferences("sp1", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()                  // clear : 모든 Key-Value 값 지워줌ㅂ
            editor.commit()
        }
    }
}