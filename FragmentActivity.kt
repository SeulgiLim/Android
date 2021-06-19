package kr.go.mapo.fast_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_fragment.*

// 5-21. Fragment 실습 (1)
// 5-22. Fragment 실습 (2)


class FragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        Log.d("life_cycle", "onCreate")

        button.setOnClickListener {
            //프레그먼트를 동적으로 작동하는 방법

            val fragmentOne : FragmentOne = FragmentOne()
            val fragmentManager : FragmentManager = supportFragmentManager

            // Transaction
            // 작업의 단위 -> 시작과 끝이 있다
            val fragmentTransaction = fragmentManager.beginTransaction()        // 시작
            fragmentTransaction.replace(R.id.container, fragmentOne)            // 할 일
            fragmentTransaction.commit()                                        // 끝

            // 끝을 내는 방법
            // commit                       -> ★ 시간 될 때 해~ (좀 더 안정적임)
            // commitnow                    -> 지금 당장해!!
            // commitAllowingStateLoss      -> 실행이 안되도 상관없으니 실행해달라~

        }

    }

    override fun onStart() {
        super.onStart()
        Log.d("life_cycle", "onStart")
    }


    override fun onResume() {
        super.onResume()
        Log.d("life_cycle", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("life_cycle", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("life_cycle", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("life_cycle", "onDestory")
    }

}