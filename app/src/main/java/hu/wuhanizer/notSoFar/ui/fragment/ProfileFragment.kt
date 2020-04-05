package hu.wuhanizer.notSoFar.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import hu.wuhanizer.notSoFar.R
import hu.wuhanizer.notSoFar.databinding.FragmentGameBinding
import hu.wuhanizer.notSoFar.databinding.FragmentProfileBinding

class ProfileFragment: BaseFragment(){

    lateinit var binding: FragmentProfileBinding


    override fun getTitle():String {
        return "Profil"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_profile,container,false)

        return binding.root
    }


}