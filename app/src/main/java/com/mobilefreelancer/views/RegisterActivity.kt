package com.mobilefreelancer.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.mobilefreelancer.R
import com.mobilefreelancer.extensions.Extensions.toast
import com.mobilefreelancer.utils.FirebaseUtils.firebaseAuth
import kotlinx.android.synthetic.main.register.*

class RegisterActivity : AppCompatActivity() {
    lateinit var userEmail: String
    private lateinit var userPassword: String
    private lateinit var createAccountInputsArray: Array<EditText>
    lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        createAccountInputsArray = arrayOf(input_email_register, input_password_register)

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)
                if(account != null){
                    firebaseAuthWithGoogle(account.idToken!!)
                }
            } catch (e: ApiException){
                toast("Api exception")
            }
        }

        button_register.setOnClickListener {
            signUp()
        }
        button_google_register.setOnClickListener {
            signInWithGoogle()
        }

        button_footer_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            toast("please sign into your account")
            finish()
        }
        button_eye_register.setOnClickListener {
            changeVision()
        }
    }


    private fun notEmpty(): Boolean = input_email_register.text.toString().trim().isNotEmpty() &&
            input_password_register.text.toString().trim().isNotEmpty()

    private fun signUp() {
        userEmail = input_email_register.text.toString().trim()
        userPassword = input_password_register.text.toString().trim()

        if (notEmpty()) {
            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        toast("created account successfully !")
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        toast("failed to Authenticate !")
                    }
                }
        }else {
            createAccountInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required"
                }
            }
        }
    }

    private fun getClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(this, gso)
    }

    private fun signInWithGoogle(){
        val signInClient = getClient()
        launcher.launch(signInClient.signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String){
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if(it.isSuccessful){
                toast("Signed with Google")
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                toast("Google sign-in error")
            }
        }
    }

    private fun changeVision(){
        if(input_password_register.transformationMethod.equals(PasswordTransformationMethod.getInstance())){
            //Show Password
            input_password_register.transformationMethod = HideReturnsTransformationMethod.getInstance();
        }
        else{
            //Hide Password
            input_password_register.transformationMethod = PasswordTransformationMethod.getInstance();
        }
    }
}