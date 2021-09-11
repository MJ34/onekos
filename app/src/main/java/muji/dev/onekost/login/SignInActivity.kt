package muji.dev.onekost.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import muji.dev.onekost.R
import muji.dev.onekost.databinding.ActivitySignInBinding
import muji.dev.onekost.home.HomeScreenActivity
import muji.dev.onekost.home.ProggresDialog

class SignInActivity : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 163
        const val TAG = "SignInActivity"
    }

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth

    private lateinit var signInBinding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInBinding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(signInBinding.root)

        mAuth = FirebaseAuth.getInstance()

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        signInBinding.btnLogin.setOnClickListener {
            signIn()
        }

        Glide.with(this)
            .load("https://firebasestorage.googleapis.com/v0/b/one-kos.appspot.com/o/pexels-pixabay-271743.jpg?alt=media&token=97ed5d74-95cd-4c01-ade6-f60a3620ae09")
            .placeholder(R.drawable.progress_animation)
            .into(signInBinding.imageLogin)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val show = ProggresDialog(this)
        show.showLoading()
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            show.dismissLoading()
            if (task.isSuccessful) {

                try {
                    val account = task.getResult(ApiException::class.java)
                    Log.d(TAG,"FirebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken)
                } catch (e: ApiException) {
                    Log.w(TAG, "Google sign In Failed", e)
                    show.showLoading()
                }
            } else {
                Log.w(TAG, exception.toString())
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val show = ProggresDialog(this)
        show.showLoading()
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) {
                show.dismissLoading()
                if (it.isSuccessful) {
                    Log.d(TAG, "SignInBerhasil :: success")
                    val intent = Intent(this, HomeScreenActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Log.w(TAG, "SignInCredential : Failure", it.exception)
                    show.dismissLoading()
                }
            }
    }
}