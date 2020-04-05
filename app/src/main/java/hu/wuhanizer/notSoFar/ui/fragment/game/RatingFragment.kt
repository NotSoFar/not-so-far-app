package hu.wuhanizer.notSoFar.ui.fragment.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import hu.wuhanizer.notSoFar.R
import hu.wuhanizer.notSoFar.data.rateRoom
import hu.wuhanizer.notSoFar.data.setRoomSnapshotListener
import hu.wuhanizer.notSoFar.databinding.FragmentRatingBinding
import hu.wuhanizer.notSoFar.model.Rating
import hu.wuhanizer.notSoFar.model.Room
import hu.wuhanizer.notSoFar.ui.activity.GameActivtiy
import hu.wuhanizer.notSoFar.ui.fragment.game.gametypes.adapter.WordsAdapter

class RatingFragment:Fragment(), EventListener<DocumentSnapshot?> {


    lateinit var binding: FragmentRatingBinding

    var listenerHandler: ListenerRegistration?=null

    var room: Room?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_rating,container,false)

        binding.btnPositive.setOnClickListener{

            rateRoom(room!!,Rating.POSITIVE)

            binding.btnPositive.isEnabled=false
            binding.btnNeutral.visibility=View.GONE
            binding.btnNegative.visibility=View.GONE

        }

        binding.btnNeutral.setOnClickListener{

            rateRoom(room!!,Rating.NEUTRAL)

            binding.btnPositive.visibility=View.GONE
            binding.btnNeutral.isEnabled=false
            binding.btnNegative.visibility=View.GONE

        }

        binding.btnNegative.setOnClickListener{

            rateRoom(room!!,Rating.NEGATIVE)

            binding.btnPositive.visibility=View.GONE
            binding.btnNeutral.visibility=View.GONE
            binding.btnNegative.isEnabled=false

        }

        listenerHandler=setRoomSnapshotListener(room!!,this)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        if(listenerHandler == null)
            listenerHandler=setRoomSnapshotListener(room!!,this)
    }

    override fun onStop() {
        super.onStop()
        listenerHandler?.remove()
    }

    override fun onEvent(snapshot: DocumentSnapshot?, p1: FirebaseFirestoreException?) {

        println("Room update")

        val r=snapshot?.toObject(Room::class.java)

        if(r!=null)
            room=r

        if(room!!.user1Rating!=null && room!!.user2Rating!=null)
        {
            println("Rating finished")

            if ((room!!.user1Rating == Rating.POSITIVE && room!!.user2Rating == Rating.POSITIVE) ||
                (room!!.user1Rating == Rating.NEUTRAL && room!!.user2Rating == Rating.POSITIVE) ||
                (room!!.user1Rating == Rating.POSITIVE && room!!.user2Rating == Rating.NEUTRAL)
            )
            {
                val fragment=MatchFragment()

                fragment.room=room

                (activity as GameActivtiy).setFragment(fragment)

            }else
            {
                (activity as GameActivtiy).setFragment(NotMatchFragment())
            }

            listenerHandler?.remove()
        }
    }
}