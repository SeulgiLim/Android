package kr.go.mapo.fast_android

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_intent.*

// 5-13. Intent 실습 (1)

class Intent1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent)

        change_activity.setOnClickListener {

            // 명시적 인텐트

//            val intent = Intent(this@Intent1, Intent2::class.java)       // Intent 만듬 -> 첫번째 인자를 , 두번째 인자로
//
//            // 5-14. Intent 실습 (2)
//            // Key, Value 방식 -> Key와 Value를 쌍으로 만들어 저장한다.
//
//            intent.putExtra("number1", 1)
//            intent.putExtra("number2", 2)
//
//            // Intent2 Activity로 넘어감!
//
//            startActivity(intent)


/*            // 아래 방법이 차후 유지보수를 위해서 좋음 (습관 들이기)
            val intent2 = Intent(this@Intent1, Intent2::class.java)
            // Apply ->
            intent2.apply {
                this.putExtra("number1", 1)
                this.putExtra("number2", 1)
            }
            //startActivity(intent2)

            startActivityForResult(intent2, 200)*/

            // 5-16. Intent 실습 (4)

            // 암시적 인텐트
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://m.naver.com"))       //ACTION_VIEW란 View 뒤 2번째 항목을 보여주라는 명령어임
            startActivity(intent)

        }           // 04. Context로 넘어감
    }

    // 꼭 추가 필요
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        if(requestCode==20){
            Log.d("number","" + requestCode)
            Log.d("number", "" + resultCode)
            val result = data?.getIntExtra("result", 0)
            Log.d("number", "" + result)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}  // 뭐가 문제지 Log.d가 정상적으로 안 찍히는데... ㅠㅠㅠ