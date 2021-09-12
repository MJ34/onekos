package muji.dev.onekost.dashboard.kosputra

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import muji.dev.onekost.Helper.formatPrice
import muji.dev.onekost.R
import muji.dev.onekost.dashboard.model.KosPutra
import muji.dev.onekost.databinding.ActivityDetailPutraBinding

class DetailPutraActivity : AppCompatActivity() {

    private lateinit var detailPutraBinding: ActivityDetailPutraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailPutraBinding = ActivityDetailPutraBinding.inflate(layoutInflater)
        setContentView(detailPutraBinding.root)

        val putra = intent.getParcelableExtra<KosPutra>("putra")

        Glide.with(this)
            .load(putra?.poster)
            .placeholder(R.drawable.progress_animation)
            .into(detailPutraBinding.ivposterDetail)

        Glide.with(this)
            .load(putra?.gambar1)
            .placeholder(R.drawable.progress_animation)
            .into(detailPutraBinding.gambar1)

        Glide.with(this)
            .load(putra?.gambar2)
            .placeholder(R.drawable.progress_animation)
            .into(detailPutraBinding.gambar2)

        Glide.with(this)
            .load(putra?.gambar3)
            .placeholder(R.drawable.progress_animation)
            .into(detailPutraBinding.gambar3)

        detailPutraBinding.tvlokasiDetail.text = putra?.tempat
        detailPutraBinding.tvratingDetail.text = putra?.rating
        detailPutraBinding.tvDesc.text = putra?.desc

        detailPutraBinding.tvWifi.text = putra?.wifi
        detailPutraBinding.tvBathroom.text = putra?.bathroom
        detailPutraBinding.tvBed.text = putra?.bed
        detailPutraBinding.tvBreakfast.text = putra?.breakfast
        detailPutraBinding.tvhargaDetail.formatPrice(putra?.price)
        detailPutraBinding.tvKos.text = putra?.category

        detailPutraBinding.back.setOnClickListener {
            finish()
        }

        detailPutraBinding.btnBooking.setOnClickListener {
            val intent = Intent(this, CheckInActivity::class.java)
                .putExtra("checkIn", putra)
            startActivity(intent)
        }
    }
}