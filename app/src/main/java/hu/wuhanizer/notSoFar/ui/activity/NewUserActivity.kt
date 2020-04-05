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
import hu.wuhanizer.notSoFar.data.getUserId
import hu.wuhanizer.notSoFar.data.saveUser
import hu.wuhanizer.notSoFar.databinding.ActivityLoginBinding
import hu.wuhanizer.notSoFar.databinding.ActivityNewUserBinding
import hu.wuhanizer.notSoFar.model.User
import java.util.*

class NewUserActivity : AppCompatActivity() {

    lateinit var binding: ActivityNewUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_new_user)

        binding.btnSave.setOnClickListener {

            if(binding.editName.text.toString().isNotEmpty() &&
                binding.editContactLink.text.toString().isNotEmpty())
            {
                val user=User(userId = getUserId(),name=binding.editName.text.toString(),birthDate = Date(),contactLink = binding.editContactLink.text.toString())

                saveUser(user).continueWith { task->

                    if (task.isSuccessful)
                    {
                        val intent = Intent(this@NewUserActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                }
            }

        }
    }
}
