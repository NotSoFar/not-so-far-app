package hu.wuhanizer.notSoFar.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import hu.wuhanizer.notSoFar.R
import hu.wuhanizer.notSoFar.databinding.FragmentGameBinding
import hu.wuhanizer.notSoFar.ui.activity.GameActivtiy
import hu.wuhanizer.notSoFar.ui.activity.LoginActivity

class GameFragment: BaseFragment(){

    lateinit var binding: FragmentGameBinding


    override fun getTitle():String {
        return "Játék"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_game,container,false)

        binding.btnNewGame.setOnClickListener {

            startActivity(Intent(activity, GameActivtiy::class.java))

        }

        return binding.root
    }


}