package muji.dev.onekost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth
import muji.dev.onekost.databinding.ActivityMainBinding
import muji.dev.onekost.home.HomeScreenActivity
import muji.dev.onekost.login.SignInActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        Handler(Looper.getMainLooper()!!).postDelayed({
            if (user != null) {
                val homeIntent = Intent(this, HomeScreenActivity::class.java)
                startActivity(homeIntent)
                finishAffinity()
            } else {
                val signIntent = Intent(this, SignInActivity::class.java)
                startActivity(signIntent)
                finish()
            }
        }, 3000)
    }
}