package hu.wuhanizer.notSoFar.ui.fragment.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import hu.wuhanizer.notSoFar.R
import hu.wuhanizer.notSoFar.data.*
import hu.wuhanizer.notSoFar.databinding.FragmentGameSearchBinding
import hu.wuhanizer.notSoFar.model.PendingUser
import hu.wuhanizer.notSoFar.ui.activity.GameActivtiy

class SearchOpponentFragment:Fragment() {


    lateinit var binding: FragmentGameSearchBinding

    var foundOpponent=false
    var needSearch=true
    var myPendingUser:PendingUser?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_game_search,container,false)

        getPending().continueWith{ task ->

            if(task.isSuccessful)
            {
                if (task.result!=null)
                {

                    println("Found opponent")

                    //removePending(task.result!!)

                    startNextFragment(task.result!!)

                }else
                {

                    println("Add to pending")
                    addToPending().continueWith{task ->

                        myPendingUser=task.result

                        getOpponent()

                    }
                }
            }

        }

        return binding.root
    }

    fun startNextFragment(opponent:PendingUser)
    {
        createRoom(opponent).continueWith{ task ->

            if(task.isSuccessful)
            {
                opponent.room=task.result?.id!!
                updatePending(opponent).continueWith{task->

                    var fragment=OpponentInfoPanelFragment()
                    fragment.roomId=opponent.room

                    (activity as GameActivtiy).setFragment(fragment)

                }
            }

        }

    }

    fun getOpponent()
    {
        getMyPending().continueWith { t->

            if(t.isSuccessful)
            {
                if (t.result?.room!= null && t.result?.room!!.trim().length>0)
                {
                    println("Somebody called me")

                    removeFromPending()

                    var fragment=OpponentInfoPanelFragment()
                    fragment.roomId=t.result?.room

                    (activity as GameActivtiy).setFragment(fragment)
                }else
                {
                    println("Nobody called me")
                    if(!foundOpponent && needSearch)
                        Thread.sleep(300)

                    if(!foundOpponent && needSearch)
                    {
                        getOpponent()
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()

        needSearch=false
        removeFromPending()
    }
}