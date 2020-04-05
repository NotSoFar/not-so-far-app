package hu.wuhanizer.notSoFar.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import hu.wuhanizer.notSoFar.R
import hu.wuhanizer.notSoFar.databinding.FragmentDonationBinding
import hu.wuhanizer.notSoFar.databinding.FragmentGameBinding

class DonationFragment: BaseFragment(){

    lateinit var binding: FragmentDonationBinding


    override fun getTitle():String {
        return "Adományok"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_donation,container,false)

        return binding.root
    }


}