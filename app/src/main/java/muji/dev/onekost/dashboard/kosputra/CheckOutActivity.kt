package muji.dev.onekost.dashboard.kosputra

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import muji.dev.onekost.databinding.ActivityCheckOutBinding

class CheckOutActivity : AppCompatActivity() {

    private lateinit var checkOutBinding: ActivityCheckOutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkOutBinding = ActivityCheckOutBinding.inflate(layoutInflater)
        setContentView(checkOutBinding.root)
    }
}