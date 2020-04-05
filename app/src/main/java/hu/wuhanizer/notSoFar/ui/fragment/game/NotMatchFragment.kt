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
import hu.wuhanizer.notSoFar.databinding.FragmentMatchBinding
import hu.wuhanizer.notSoFar.databinding.FragmentNotMatchBinding
import hu.wuhanizer.notSoFar.databinding.FragmentRatingBinding
import hu.wuhanizer.notSoFar.model.Rating
import hu.wuhanizer.notSoFar.model.Room
import hu.wuhanizer.notSoFar.ui.fragment.game.gametypes.adapter.WordsAdapter

class NotMatchFragment:Fragment() {


    lateinit var binding: FragmentNotMatchBinding

    var room: Room?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_not_match,container,false)

        binding.btnFinish.setOnClickListener {

            activity!!.finish()
        }

        return binding.root
    }

}