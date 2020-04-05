package hu.wuhanizer.notSoFar.ui.fragment.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import hu.wuhanizer.notSoFar.R
import hu.wuhanizer.notSoFar.data.addToPending
import hu.wuhanizer.notSoFar.data.getPending
import hu.wuhanizer.notSoFar.databinding.FragmentGameOpponentInfoBinding
import hu.wuhanizer.notSoFar.databinding.FragmentGameSearchBinding
import hu.wuhanizer.notSoFar.model.PendingUser
import hu.wuhanizer.notSoFar.model.Room
import hu.wuhanizer.notSoFar.ui.activity.GameActivtiy
import hu.wuhanizer.notSoFar.ui.fragment.game.gametypes.ChainWordsGameFragment
import java.sql.Time

class OpponentInfoPanelFragment:Fragment() {


    lateinit var binding: FragmentGameOpponentInfoBinding

    var roomId:String?=null
    var counter:Int=10

    var running=true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_game_opponent_info,container,false)


        Thread{

            while(counter>0 && running)
            {
                Thread.sleep(1000)

                counter--

                activity!!.runOnUiThread {

                    if(running)
                        binding.txtCounter.text = "Visszaszámlálás... " + counter

                }
            }

            if(running){
                activity!!.runOnUiThread {

                    var fragment=ChainWordsGameFragment()

                    fragment.roomId=roomId

                    (activity as GameActivtiy).setFragment(fragment)

                }
            }

        }.start()

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        running=false
    }
}