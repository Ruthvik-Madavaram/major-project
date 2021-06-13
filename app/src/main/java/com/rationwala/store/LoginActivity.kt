package com.rationwala.store


import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.leo.simplearcloader.ArcConfiguration
import com.leo.simplearcloader.SimpleArcDialog
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.TimeUnit

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {
   var pd: SimpleArcDialog ? = null
    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var verificationid:String
    private lateinit var auth: FirebaseAuth
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        create.setOnClickListener {
           startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
        }

        login.setOnClickListener {
            // startActivity(Intent(this@LoginActivity,MainActivity::class.java))
            var cm=getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var e=email.text.toString()
            var p = password.text.toString()
            if(e.isEmpty())
                email.setError("Email!")
            else if(p.isEmpty())
                password.setError("Password!")
            else if(p.length<8)
                password.setError("less than 8 digits")
            else if(cm.activeNetworkInfo==null)
            {
                Toast.makeText(this@LoginActivity,"Please connect to internet", Toast.LENGTH_LONG).show()

            }
            else
            {
                var pd = ProgressDialog(this@LoginActivity)
                pd.setTitle("Authenticating..")
                pd.show()
                var auth = FirebaseAuth.getInstance()

                auth.signInWithEmailAndPassword(e,p).addOnCompleteListener{
                    if(it.isSuccessful)
                    {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                        pd.dismiss()
                    }
                    else
                    {
                        Toast.makeText(this@LoginActivity,"invalid email or password", Toast.LENGTH_LONG).show()
                        pd.dismiss()
                    }
                }
            }
        }
    }



    override fun onStart() {
        super.onStart()
        var user = FirebaseAuth.getInstance().currentUser
        if(user!=null)
        {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }

}
