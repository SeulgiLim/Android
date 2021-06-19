package kr.go.mapo.fast_android

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FragmentOne : Fragment(){

    override fun onAttach(context: Context) {
        Log.d("life_cyccle", "f_onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("life_cyccle", "f_onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        // 프레그먼트가 인터페이스를 처음으로 그릴 때 호출된다.
        // inflater -> View를 그려주는 역할
        // container -> 부모 View
        Log.d("life_cycle", "f_onCreateView")

        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("life_cycle", "f_onViewCreated")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d("life_cycle", "f_onActivityCreated")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        Log.d("life_cycle", "f_onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d("life_cycle", "f_onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d("life_cycle", "f_onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d("life_cycle", "f_onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d("life_cycle", "f_onDestroyView")
        super.onDestroyView()
    }

    override fun onDetach() {
        Log.d("life_cycle", "f_onDetach()")
        super.onDetach()
    }
}