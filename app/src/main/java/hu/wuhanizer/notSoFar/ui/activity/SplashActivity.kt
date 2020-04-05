package hu.wuhanizer.notSoFar.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.security.ProviderInstaller
import com.google.firebase.auth.FirebaseAuth
import hu.wuhanizer.notSoFar.R

class SplashActivity: AppCompatActivity() {

    val SPLASH_TIME=1500

    private var run = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        run = true

        ProviderInstaller.installIfNeeded(applicationContext)

        object : Thread() {

            override fun run() {

                try {
                    sleep(SPLASH_TIME.toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                runOnUiThread {
                    if (run) {
                        startApp()
                    }
                }

            }
        }.start()



    }

    private fun startApp() {

        val intent=if(FirebaseAuth.getInstance().currentUser==null)
        {
            Intent(this@SplashActivity,LoginActivity::class.java)
        }else
        {
            Intent(this@SplashActivity,MainActivity::class.java)
        }

        startActivity(intent)

        finish()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        run = false
    }
}