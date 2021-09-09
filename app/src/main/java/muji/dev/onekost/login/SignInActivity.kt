package muji.dev.onekost.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import muji.dev.onekost.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var signInBinding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInBinding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(signInBinding.root)
    }
}