package hu.wuhanizer.notSoFar.ui.fragment.game.gametypes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import hu.wuhanizer.notSoFar.R
import hu.wuhanizer.notSoFar.data.*
import hu.wuhanizer.notSoFar.databinding.FragmentGameTypeChainWordsBinding
import hu.wuhanizer.notSoFar.model.Room
import hu.wuhanizer.notSoFar.model.Word
import hu.wuhanizer.notSoFar.ui.activity.GameActivtiy
import hu.wuhanizer.notSoFar.ui.fragment.game.RatingFragment
import hu.wuhanizer.notSoFar.ui.fragment.game.gametypes.adapter.DataChangedListener
import hu.wuhanizer.notSoFar.ui.fragment.game.gametypes.adapter.WordsAdapter
import java.sql.Time


class ChainWordsGameFragment: Fragment(),DataChangedListener {

    lateinit var binding: FragmentGameTypeChainWordsBinding

    var roomId:String?=null

    var room:Room?=null

    var running=true
    var timeLeft=60

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_game_type_chain_words,container,false)

        getRoom(roomId!!).continueWith { task->

            if(task.isSuccessful)
            {
                println("Room got")
                room=task.result

                binding.recyclerWords.adapter=WordsAdapter(getWordQuery(room!!))

                (binding.recyclerWords.adapter as WordsAdapter).changedListener=this

                binding.recyclerWords.layoutManager = LinearLayoutManager(context)

                (binding.recyclerWords.adapter as WordsAdapter).startListening()

                startRoom(room!!)

                binding.myTurn= room!!.startUserId == getUserId()

                binding.btnSend.setOnClickListener {

                    if(binding.editWord.text.toString().trim().isNotEmpty())
                    {
                        addWord(room!!,binding.editWord.text.toString()).continueWith { task->
                            binding.editWord.setText("")
                        }
                    }
                }

                Thread{

                    while(timeLeft>0 && running)
                    {
                        Thread.sleep(1000)

                        timeLeft--

                        if(running)
                        {
                            activity!!.runOnUiThread {
                                binding.txtTime.text = "" + timeLeft + " mp"
                            }
                        }

                    }

                    if(running) {

                        val fragment = RatingFragment()

                        fragment.room=room

                        (activity as GameActivtiy).setFragment(fragment)

                    }

                }.start()

            }

        }

        return binding.root
    }


    override fun onStart() {
        super.onStart()

        if(binding.recyclerWords.adapter != null)
            (binding.recyclerWords.adapter as WordsAdapter).startListening()
    }

    override fun onStop() {
        super.onStop()
        (binding.recyclerWords.adapter as WordsAdapter).stopListening()
        running=false
    }

    override fun onDataChanged() {

        println("Dataset changed")

        val adapter=(binding.recyclerWords.adapter as WordsAdapter)

        if(adapter.itemCount>0)
            binding.myTurn= adapter.getItem(adapter.itemCount-1).userId != getUserId()
    }
}