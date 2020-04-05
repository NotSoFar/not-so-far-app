package hu.wuhanizer.notSoFar.ui.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import hu.wuhanizer.notSoFar.R
import hu.wuhanizer.notSoFar.databinding.ActivityLoginBinding
import java.util.*

class LoginActivity : AppCompatActivity() {

    val REQUEST_CODE_AUTH_UI=42

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_login)

        binding.btnLogin.setOnClickListener {

            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(
                    Arrays.asList<AuthUI.IdpConfig>(
                        AuthUI.IdpConfig.GoogleBuilder().build(),
                        AuthUI.IdpConfig.FacebookBuilder().build()
                    )
                )
                .setLogo(R.drawable.btn_positive)
                .setTheme(R.style.AuthUI).build(), REQUEST_CODE_AUTH_UI)


        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_AUTH_UI)
        {
            val response = IdpResponse.fromResultIntent(data)

            // Successfully signed in
            if (resultCode == Activity.RESULT_OK)
            {
                val intent:Intent?

                if(response!!.isNewUser)
                {
                    intent = Intent(this@LoginActivity, NewUserActivity::class.java)

                }else{
                    intent = Intent(this@LoginActivity, MainActivity::class.java)
                }

                startActivity(intent)
                finish()

            } else {
                // Sign in failed
                if (response == null)
                {
                    finish()
                    return
                }

                if (response.error!!.errorCode == ErrorCodes.NO_NETWORK)
                {
                    Toast.makeText(this,getString(R.string.no_network_connection), Toast.LENGTH_LONG).show()
                    return
                }

                Toast.makeText(this,getString(R.string.unknown_error), Toast.LENGTH_LONG).show()
            }
        }
    }

}
