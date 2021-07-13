package kr.co.gooroomeelite.views.home

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Chronometer
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_stopwatch.*
import kr.co.gooroomeelite.R
import kr.co.gooroomeelite.entity.Subject
import kr.co.gooroomeelite.utils.LoginUtils.Companion.getUid
import splitties.resources.drawable
import java.util.concurrent.TimeUnit

/*
보통 리스너는 버튼클릭같은거 입력같은거 할때 자동 실행되는 이벤트함수
뷰 만들어서 변수에 뷰 인자 맞춰끼우고
이벤트 발생하면 리스너 실행
리스너 실행하면서 안에있는 함수실행 끝
*/

private lateinit var subject : Subject
private lateinit var documentId : String


// 스탑워치
class StopwatchFragment : Fragment() {
    private var buttonStartPause: Button? = null                                                        // 스탑워치 시작버튼
    private var buttonReset: Button? = null                                                             // 스탑워치 리셋버튼
   // private var buttonRestart: Button? = null                                                           // 스탑워치 다시시작

    private var stopwatch: Chronometer? = null                                                          // 시간 측정
    private var pauseOffset: Long = 0                                                                   // 일시중지
    private var running: Int = 0                                                                         // 스탑워치 실행중 (0, 1, 2)
    private var curTime: Long = 0                                                                       // curTime : 현재시간, 타입 Long 맞음

    val intent = Intent()                                                                               // 인텐트 생성성

   @Nullable                                                                                           // @Nullable : 무조건 null인지 확인
    // (@는 어노테이션 - 용도 : 문서화, 컴파일러 체크, 코드 분석용도 명시 : 패키지,클래스,메소드, 프로퍼티, 변수에 명시가능)

    override fun onCreateView(                                                                          // override : 모든 메서드에 대해서 붙여서 스펠링 에러 확인 가능
            // 현재 메소드가 수퍼클래스의 메소드를 오버라이드한 메소드임을 컴파일러에게 명시한다. 만일 수퍼클래스에 해당하는 메소드가 없다면 컴파일러가 인지하고 에러를 발생시켜 준다.
           @NonNull inflater: LayoutInflater,                                                          // @NonNull : null 일 수 없고, null 일 수도 있다는 애너테이션
            @Nullable container: ViewGroup?,
            @Nullable savedInstanceState: Bundle?
    ): View {
        val v = inflater.inflate(R.layout.fragment_stopwatch, container, false)              // 매개변수 설명 : inflate( 1.객체화하고픈 xml파일, 2.객체화한 뷰를 넣을 부모 레이아웃/컨테이너, 3.true(바로 인플레이션 하고자 하는지)) // R : res 폴더, layout : R의 내부 클래스

        stopwatch = v.findViewById(R.id.stopwatch)                                                      // 레이아웃 안에 있는 View들을 가져와 사용하는 메소드 (findViewByld)



        // 현재 타이머 값 표시 (stopwatch 포멧 문자 타입으로 변환)
        stopwatch?.setFormat("00:%s")
        stopwatch?.setOnChronometerTickListener ({
                stopwatch ->
            val elapsedMillis = SystemClock.elapsedRealtime() - stopwatch!!.base
            if (elapsedMillis > 3600000L) {
                stopwatch.format = "0%s"
            }
            else {
                stopwatch.format = "00:%s"
            }
        })


        stopwatch?.setBase(SystemClock.elapsedRealtime())                                       // 시간측정시 사용, Import한 SystemClock에서 elapedRealtime의 계산식을 가져옴 / getBase()를 통해 설정된 기준 시간을 반환 함
        // SystemClock.elapsedRealtime() 부팅된 시점부터 현재까지의 시간을 millisecond로 리턴

        buttonStartPause = v.findViewById(R.id.btn_start)                                              // buttonStartPause 클릭시 findViewById를 이용하여 layout의 button_start_pause 가져옴 / 시작, 일시정지 버튼
        // buttonRestart = v.findViewById(R.id.btn_restart)
        buttonReset = v.findViewById(R.id.btn_end)                                                 // buttonReset 클릭시  findViewById를 이용하여 layout의 button_reset 가져옴

    /*   // buttonStartPause 클릭시 상세동작
       buttonStartPause?.setOnClickListener(View.OnClickListener {                                     // buttonStartPause 클릭시
           if (running) {
               pauseStopwatch()                                                                        // 스탑워치 정지 실행
           } else {
               startStopwatch()                                                                        // 스탑워치 시작 실행
           }
       })
       return v                                                                                        // 변수 v(fragment_stopwatch.xml)에 결과 return
   }*/

       // buttonRestart?.setOnClickListener ({ resetStopwatch () })

       // resetbutton 클릭시 상세동작
       buttonReset?.setOnClickListener(View.OnClickListener { resetStopwatch()                         // buttonReset 클릭시 resetStopwatch() 실행
           // val studytime = stopwatch. 태수님이 제시해 준 예시
           // 결과값 Intent로 보내주기

           // StopwatchFragment에서 StudyEndActivity로 화면 전환 선언
           val studyEndActivity = Intent(requireContext(), StudyEndActivity::class.java)

           // intent에 값 저장
           //studyEndActivity.putExtra("nowstuytime",curTime)

           // Bundle의 값을 저장
           // 1) Bundle 객체 필요
           //val bundle = Bundle()
           // bundle.putLong("공부진행시간", curTime)

           // 화면 전환하기
           startActivity(studyEndActivity)

           // 3. 완료/계속 버튼으로 전환 - 시작Btn : gone, 정지 Btn : gone, 완료/계속 Btn : visible

           // 4. 완료 버튼 클릭시 측정완료 화면으로 전환

       })

        // buttonStartPause 클릭시 상세동작
        buttonStartPause?.setOnClickListener(View.OnClickListener {                                     // buttonStartPause 클릭시
            if (running == 0)
            {
                pauseStopwatch()
            }                                                                                          // 스탑워치 정지 실행
            else {
                if (running == 1) {
                    startStopwatch()
                }        // 스탑워치 시작 실행
                else {
                    if (running == 2){
                        resetStopwatch()
                    }
                }
            }
        })
       return v                                                                                       // 변수 v(fragment_stopwatch.xml)에 결과 return
    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn_end.setOnClickListener {
            btn_start.visibility = View.GONE
            btn_finish.visibility = View.GONE
            btn_restart.visibility = View.GONE
            btn_end.visibility = View.VISIBLE
        }
    }*/



    // 스탑워치 시작
    private fun startStopwatch() {
        if (running == 0) {                                                                                 // if은 true 여부 판별, true일 경우 안의 내용 실행 (이 경우 true가 아닐경우 실행)
                                                                                                        // running 앞에 !는 반대로 이해 -> true면 false false면 true로 비교
            curTime = SystemClock.elapsedRealtime() - pauseOffset                                       // 스탑워치 진행 시간 계산식
            stopwatch!!.base = curTime                                                                  //  stopwatch!!.base에 curTime에 넣은 실시간 셋팅
            stopwatch!!.start()                                                                         // 스탑워치 시작 함수 실행
            //running = 0

            // 스탑워치가 실행중일 경우 다시시작 못하도록 ture 넣음

            // 1. 시적버튼 - 시작 Btn : visible, 정지 Btn : gone, 완료/계속 Btn : visible
            buttonStartPause!!.text = "일시정지"                                                           // buttonStartPause에 text "Pause" 바꾸고 종료
            Log.d("aaa",pauseOffset.toString())
        }
    }

    // 스탑워치 일시정지
    private fun pauseStopwatch() {
        if (running == 1) {
            stopwatch!!.stop()                                                                          // 스탑워치 종료 함수 실행
            pauseOffset = SystemClock.elapsedRealtime() - stopwatch!!.base                              // pauseOffset : 동작시간 - 시작시간
            //running = 1                                                                                // 스탑워치 실행중일 경우 다시 시작하도록 false 넣음

            // 2. 정지버튼으로 전환 - 시작Btn : gone, 정지 Btn : Visible, 완료/계속 Btn : gone
            buttonStartPause!!.text = "시작하기"                                                           // buttonStartPause에 text "Start" 바꾸고 종료

            //buttonStartPause!!.setBackgroundDrawable(ContextCompat.getDrawable(Context, R.drawable.ic_btn_restart))   //XML Selector 사용
        }
        Log.d("aaa2",pauseOffset.toString())
    }


    // 스탑워치 시간 초기화 (초기화와 동시에 나가기 화면 연결)
    private fun resetStopwatch() {
        stopwatch!!.base = SystemClock.elapsedRealtime()                                                // stopwatch!!.base는 동작시간????
        pauseOffset = 0                                                                                 // 진행시간 (pauseOffset) 0으로 초기화

    }



    // SharedPreferences는 데이터를 파일로 저장 -> 파일이 앱 폴더 내에 저장
    // 저장 파일 위치 : data/data/(package_name)/shared_prefs/SharedPreference
    // 스탑워치 종료
    override fun onStop() {
        super.onStop()
        val prefs = activity!!.getSharedPreferences(                              // getPreference() 함수 : 자동으로 액티비티 이름의 파일 내에 저장함
                SW_PREFS,
                Context.MODE_PRIVATE                                              // Mode = 접근 권한, PRIVATE = 해당 앱에서만 접근 가능하게 해줌
        )


        val editor = prefs.edit()                                                 // 데이터 기록을 위한 Editor
        editor.putLong(CUR_TIME, curTime)                                         // 데이터 저장 : editor.putString(key, value)
        editor.apply()                                                            // 데이터 저장시 editor를 사용, apply 적용해야 동작함

        if (stopwatch != null) {
        }
        Log.d("aaa3",curTime.toString())
    }


    //스탑워치 시작
    override fun onStart() {
        super.onStart()
        val prefs = activity!!.getSharedPreferences(                               // getPreference() 함수 : 자동으로 액티비티 이름의 파일 내에 저장함
                SW_PREFS,
                Context.MODE_PRIVATE                                               // Mode = 접근 권한, PRIVATE = 해당 앱에서만 접근 가능하게 해줌
        )
        curTime = prefs.getLong(CUR_TIME, 0)
        if (running == 2) {
            curTime = -SystemClock.elapsedRealtime() - stopwatch!!.base
        }
    }


    companion object {                                                                              // 동반자 객체 (companion object) : 클래스 안에 포함되는 이름 없는 객체
                                                                                                    // 어떤 클래스의 모든 인스턴스가 공유하는 객체를 만들고 싶을 때 사용 (java - static 효과)
        private const val SW_PREFS = "sWPrefs"
        private const val CUR_TIME = "curTime"
    }
}


// 1순위 해결!!!!!!!!!!
// 경과시간 완료 화면으로 보내기
/*private fun stopStopWatch(){
    // val studytime = stopwatch. 태수님이 제시해 준 예시
    // 결과값 Intent로 보내주기

    val StudyEndActivity_Intent = Intent(StopwatchFragment, StudyEndActivity::class.java)

    StudyEndActivity_Intent.putExtra("nowstuytime",curTime)
    startActivity(StudyEndActivity_Intent)
}*/


// 정지버튼 (스탑워치 종료 후 리셋)


// 1순위 해결시 이부분 태수님이 작업해주시기로 함
//서버로 공부시간 업데이트 하기(프래그먼트에서 작성)
/*val nowstudytime = 1; // test
FirebaseFirestore.getInstance().collection("subject")
        .document(getUid()!!)
        .update(hashMapOf("studytime" to nowstudytime) as Map<String, Any>) // nowstudytime (오늘공부시간)
        .addOnSuccessListener {
            //여기에 서버에 저장 성공했을 경우 다음 행동 코드를 작성


        }


// 타이머설정 버튼 클릭시 타이머 설정 화면으로 이동
btn_timermode.setOnClickListener{
    val intent = Intent(requireContext(), TimersettingActivity::class.java)
    startActivity(intent)                       // 새로운 Activity를 화면에 띄울 때
}*/

/*
/*// 스탑워치 버튼
binding.btnTimermode.setOnClickListener {
    val StopwatchFragment: StopwatchFragment = StopwatchFragment()
    val fragmentManager: FragmentManager = supportFragmentManager


    val fragmentTransaction = fragmentManager.beginTransaction()        // 시작
    fragmentTransaction.replace(R.id.container, StopwatchFragment)            // 할 일
    fragmentTransaction.commit()                                        // 끝
}*/
 */


/*
// 21.07.06 스탑워치
class StopwatchFragment : Fragment() {
    private var buttonStartPause: Button? = null                                                        // 스탑워치 시작버튼
    private var buttonReset: Button? = null                                                             // 스탑워치 리셋버튼
    private var stopwatch: Chronometer? = null                                                          // 시간 측정
    private var pauseOffset: Long = 0                                                                   // 일시중지
    private var running = false                                                                         // 스탑워치 실행중
    private var curTime: Long = 0                                                                       // curTime : 현재시간, 타입 Long 맞음

    @Nullable                                                                                           // @Nullable : 무조건 null인지 확인
    // (@는 어노테이션 - 용도 : 문서화, 컴파일러 체크, 코드 분석용도 명시 : 패키지,클래스,메소드, 프로퍼티, 변수에 명시가능)

    override fun onCreateView(                                                                          // override : 모든 메서드에 대해서 붙여서 스펠링 에러 확인 가능
            // 현재 메소드가 수퍼클래스의 메소드를 오버라이드한 메소드임을 컴파일러에게 명시한다. 만일 수퍼클래스에 해당하는 메소드가 없다면 컴파일러가 인지하고 에러를 발생시켜 준다.

           @NonNull inflater: LayoutInflater,                                                          // @NonNull : null 일 수 없고, null 일 수도 있다는 애너테이션
            @Nullable container: ViewGroup?,
            @Nullable savedInstanceState: Bundle?
    ): View {
        val v = inflater.inflate(R.layout.fragment_stopwatch, container, false)              // 매개변수 설명 : inflate( 1.객체화하고픈 xml파일, 2.객체화한 뷰를 넣을 부모 레이아웃/컨테이너, 3.true(바로 인플레이션 하고자 하는지)) // R : res 폴더, layout : R의 내부 클래스

        stopwatch = v.findViewById(R.id.stopwatch)                                                      // 레이아웃 안에 있는 View들을 가져와 사용하는 메소드 (findViewByld)
        stopwatch?.setFormat("%s")                                                                      // stopwatch 포멧 문자 타입으로 변환
        stopwatch?.setBase(SystemClock.elapsedRealtime())                                               // 시간측정시 사용, Import한 SystemClock에서 elapedRealtime의 계산식을 가져옴
        // SystemClock.elapsedRealtime() 부팅된 시점부터 현재까지의 시간을 millisecond로 리턴

        buttonStartPause = v.findViewById(R.id.button_start_pause)                                      // buttonStartPause 클릭시 findViewById를 이용하여 layout의 button_start_pause 가져옴
        buttonReset = v.findViewById(R.id.button_reset)                                                 // buttonReset 클릭시  findViewById를 이용하여 layout의 button_reset 가져옴

        // resetbutton 클릭시 상세동작
        buttonReset?.setOnClickListener(View.OnClickListener { resetStopwatch() })                      // buttonReset 클릭시 resetStopwatch() 실행

        // buttonStartPause 클릭시 상세동작
        buttonStartPause?.setOnClickListener(View.OnClickListener {                                     // buttonStartPause 클릭시
            if (running) {
                pauseStopwatch()                                                                        // 스탑워치 정지 실행
            } else {
                startStopwatch()                                                                        // 스탑워치 시작 실행
            }
        })
        return v                                                                                        // 변수 v(fragment_stopwatch.xml)에 결과 return
    }

    // 스탑워치 시작
    private fun startStopwatch() {
        if (!running) {                                                                                 // if은 true 여부 판별, true일 경우 안의 내용 실행 (이 경우 true가 아닐경우 실행)
                                                                                                        // running 앞에 !는 반대로 이해 -> true면 false false면 true로 비교

            curTime = SystemClock.elapsedRealtime() - pauseOffset                                       // 스탑워치 진행 시간 계산식
            stopwatch!!.base = curTime                                                                  //  stopwatch!!.base에 curTime에 넣은 실시간 셋팅
            stopwatch!!.start()                                                                         // 스탑워치 시작 함수 실행
            running = true                                                                              // 스탑워치가 실행중일 경우 다시시작 못하도록 ture 넣음
            buttonStartPause!!.text = "Pause"                                                           // buttonStartPause에 text "Pause" 바꾸고 종료
            Log.d("aaa",pauseOffset.toString())
        }
    }

    // 스탑워치 종료
    private fun pauseStopwatch() {
        if (running) {
            stopwatch!!.stop()                                                                          // 스탑워치 종료 함수 실행
            pauseOffset = SystemClock.elapsedRealtime() - stopwatch!!.base                              // pauseOffset : 동작시간 - 시작시간
            running = false                                                                             // 스탑워치 실행중일 경우 다시 시작하도록 false 넣음
            buttonStartPause!!.text = "Start"                                                           // buttonStartPause에 text "Start" 바꾸고 종료
        }
        Log.d("aaa2",pauseOffset.toString())
    }

    // 스탑워치 시간 초기화
    private fun resetStopwatch() {
        stopwatch!!.base = SystemClock.elapsedRealtime()                                                // stopwatch!!.base는 동작시간????
        pauseOffset = 0                                                                                 // 진행시간 (pauseOffset) 0으로 초기화
    }


    //
    override fun onStop() {
        super.onStop()
        val prefs = activity!!.getSharedPreferences(
                SW_PREFS,
                Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putLong(CUR_TIME, curTime)
        editor.apply()
        if (stopwatch != null) {
        }
        Log.d("aaa3",curTime.toString())
    }


    //
    override fun onStart() {
        super.onStart()
        val prefs = activity!!.getSharedPreferences(
                SW_PREFS,
                Context.MODE_PRIVATE
        )
        curTime = prefs.getLong(CUR_TIME, 0)
        if (running) {
            curTime = -SystemClock.elapsedRealtime() - stopwatch!!.base
        }
    }

    companion object {                                                                              // 동반자 객체 (companion object) : 클래스 안에 포함되는 이름 없는 객체
                                                                                                    // 어떤 클래스의 모든 인스턴스가 공유하는 객체를 만들고 싶을 때 사용 (java - static 효과)
        private const val SW_PREFS = "sWPrefs"
        private const val CUR_TIME = "curTime"
    }
}

 */

