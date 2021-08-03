package kr.go.mapo.android

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

lateinit var usernameView: EditText
lateinit var userPassword1View : EditText
lateinit var userPassword2View : EditText

class EmailSignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_signup)

        initView(this@EmailSignupActivity)

    }

    // ID, PW 가져올 수 있는 기능 구현
    fun initView(activity: Activity){
        usernameView = activity.findViewById(R.id.username_inputbox)
        userPassword1View = activity.findViewById(R.id.password1_inputbox)
        userPassword2View = activity.findViewById(R.id.password2_inputbox)
    }

    fun getUserName():String{
        return  usernameView.text.toString()
    }

    fun getUserPassword1():String{
        return userPassword1View.text.toString()
    }

    fun getUserPassword2():String{
        return userPassword2View.text.toString()
    }

    // 레트로핏 사용 예정

}