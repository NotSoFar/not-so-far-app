package hu.wuhanizer.notSoFar.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import hu.wuhanizer.notSoFar.R
import hu.wuhanizer.notSoFar.databinding.ActivityMainBinding
import hu.wuhanizer.notSoFar.ui.fragment.BaseFragment
import hu.wuhanizer.notSoFar.ui.fragment.DonationFragment
import hu.wuhanizer.notSoFar.ui.fragment.GameFragment
import hu.wuhanizer.notSoFar.ui.fragment.ProfileFragment


/**
 * Created by tszabo on 2018. 02. 20..
 */

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val fragments= arrayOf(mutableListOf<BaseFragment>(GameFragment()), mutableListOf<BaseFragment>(DonationFragment()), mutableListOf<BaseFragment>(ProfileFragment()))

    private var fragmentIndex=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.bottomNavigation.setOnNavigationItemSelectedListener {

            val index=when(it.itemId)
            {
                R.id.action_alerts -> 0
                R.id.action_projects -> 1
                else -> 2
            }

            setFragment(index)

            true
        }

        setFragment(0)

    }

    private fun setFragment(index: Int)
    {
        fragmentIndex=index

        val transaction=supportFragmentManager.beginTransaction()


        if (fragments[fragmentIndex].size>1)
        {
            transaction.setCustomAnimations(R.anim.slide_in_right, android.R.anim.slide_out_right)
//            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }else
        {
//            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }

        title=fragments[fragmentIndex].last().getTitle()


        transaction.replace(R.id.fragmentContainer,fragments[fragmentIndex].last()).commit()


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val itemId = item!!.itemId
        when (itemId) {
            android.R.id.home -> {

                fragments[fragmentIndex].removeAt((fragments[fragmentIndex].size-1) )

                setFragment(fragmentIndex)

            }
        }

        return super.onOptionsItemSelected(item)

    }

    fun addFragment(fragment: BaseFragment)
    {
        fragments[fragmentIndex].add(fragment)

        setFragment(fragmentIndex)
    }

//    fun showSnackBar(message:String)
//    {
//        val snackbar = Snackbar.make(binding.coordinatorLayout, message, Snackbar.LENGTH_LONG)
//
//        snackbar.show()
//    }

}
