package muji.dev.onekost.dashboard.kosputri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import muji.dev.onekost.databinding.ActivityDetailPutriBinding

class DetailPutriActivity : AppCompatActivity() {

    private lateinit var detailPutriBinding: ActivityDetailPutriBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailPutriBinding = ActivityDetailPutriBinding.inflate(layoutInflater)
        setContentView(detailPutriBinding.root)
    }
}