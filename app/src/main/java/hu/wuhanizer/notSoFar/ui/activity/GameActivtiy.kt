package hu.wuhanizer.notSoFar.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import hu.wuhanizer.notSoFar.R
import hu.wuhanizer.notSoFar.databinding.ActivityGameBinding
import hu.wuhanizer.notSoFar.databinding.ActivityMainBinding
import hu.wuhanizer.notSoFar.ui.fragment.GameFragment
import hu.wuhanizer.notSoFar.ui.fragment.game.SearchOpponentFragment

class GameActivtiy: AppCompatActivity() {

    lateinit var binding: ActivityGameBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this, R.layout.activity_game)

        setFragment(SearchOpponentFragment())
    }

    fun setFragment(fragment: Fragment)
    {
        val transaction=supportFragmentManager.beginTransaction()

        transaction.setCustomAnimations(R.anim.slide_in_right, android.R.anim.slide_out_right)

        transaction.replace(R.id.fragmentContainer,fragment).commit()

    }
}