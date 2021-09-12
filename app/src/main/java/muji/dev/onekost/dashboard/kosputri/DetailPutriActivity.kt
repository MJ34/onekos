package muji.dev.onekost.dashboard.kosputri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import muji.dev.onekost.Helper.formatPrice
import muji.dev.onekost.R
import muji.dev.onekost.dashboard.model.KosPutri
import muji.dev.onekost.databinding.ActivityDetailPutriBinding

class DetailPutriActivity : AppCompatActivity() {

    private lateinit var detailPutriBinding: ActivityDetailPutriBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailPutriBinding = ActivityDetailPutriBinding.inflate(layoutInflater)
        setContentView(detailPutriBinding.root)

        val putri = intent.getParcelableExtra<KosPutri>("putri")

        Glide.with(this)
            .load(putri?.poster)
            .placeholder(R.drawable.progress_animation)
            .into(detailPutriBinding.ivPutriposterDetail)

        Glide.with(this)
            .load(putri?.gambar1)
            .placeholder(R.drawable.progress_animation)
            .into(detailPutriBinding.Putrigambar1)

        Glide.with(this)
            .load(putri?.gambar2)
            .placeholder(R.drawable.progress_animation)
            .into(detailPutriBinding.Putrigambar2)

        Glide.with(this)
            .load(putri?.gambar3)
            .placeholder(R.drawable.progress_animation)
            .into(detailPutriBinding.Putrigambar3)

        detailPutriBinding.tvPutrilokasiDetail.text = putri?.tempat
        detailPutriBinding.tvPutriratingDetail.text = putri?.rating
        detailPutriBinding.tvPutriDesc.text = putri?.desc

        detailPutriBinding.tvPutriWifi.text = putri?.wifi
        detailPutriBinding.tvPutriBathroom.text = putri?.bathroom
        detailPutriBinding.tvPutriBed.text = putri?.bed
        detailPutriBinding.tvPutriBreakfast.text = putri?.breakfast
        detailPutriBinding.tvPutrihargaDetail.formatPrice(putri?.price)
        detailPutriBinding.tvPutriKos.text = putri?.category

        detailPutriBinding.Putriback.setOnClickListener {
            finish()
        }

        detailPutriBinding.btnPutriBooking.setOnClickListener {

        }
    }
}