package kr.go.mapo.fastpart2ch4_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    // 계산식 표시
    private val expressionTextView: TextView by lazy {
        findViewById<TextView>(R.id.expressionTextView)
    }

    // 계산 결과 표시
    private val resultTextView: TextView by lazy {
        findViewById<TextView>(R.id.resultTextView)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun buttonClicked(v: View){
        when(v.id){

            // 숫자 Btn
            R.id.Button0 -> numberButtonClicked("0")
            R.id.Button1 -> numberButtonClicked("1")
            R.id.Button2 -> numberButtonClicked("2")
            R.id.Button3 -> numberButtonClicked("3")
            R.id.Button4 -> numberButtonClicked("4")
            R.id.Button5 -> numberButtonClicked("5")
            R.id.Button6 -> numberButtonClicked("6")
            R.id.Button7 -> numberButtonClicked("7")
            R.id.Button8 -> numberButtonClicked("8")
            R.id.Button9 -> numberButtonClicked("9")

            // 연산 Btn
            R.id.ButtonPlus -> operatorButtonClicked("+")
            R.id.ButtonMinus -> operatorButtonClicked("-")
            R.id.ButtonMulti -> operatorButtonClicked("*")
            R.id.ButtonDivider -> operatorButtonClicked("/")
            R.id.ButtonModulo -> operatorButtonClicked("%")
        }
    }

    // 숫자 버튼 눌렀을 때 함수 호출
    private fun numberButtonClicked(number: String){


        val expressionText = expressionTextView.text.split(" ")
        if (expressionText.isNotEmpty() && expressionText.last().length >= 15){
            Toast.makeText(this, "15자리 까지만 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
            return
        }else if (number == "0" && expressionText.last().isEmpty()){
            android.widget.Toast.makeText(this, "0은 제일 앞에 올 수 없습니다.", android.widget.Toast.LENGTH_SHORT).show()

        }
        expressionTextView.append(number)

    }

    // 연산자 버튼 눌렸을 때 함수 호출
    private fun operatorButtonClicked(operator:String){

    }

    fun resultButtonClicked(v: View){

    }

    fun historyButtonClicked(v: View){

    }

    fun clearButtonClicked(v: View){

    }
}