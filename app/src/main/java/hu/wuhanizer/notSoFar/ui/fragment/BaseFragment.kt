package hu.wuhanizer.notSoFar.ui.fragment

import androidx.fragment.app.Fragment


/**
 * Created by tszabo on 2018. 04. 02..
 */
abstract class BaseFragment: Fragment()
{
    abstract fun getTitle():String
}