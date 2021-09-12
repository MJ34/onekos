package muji.dev.onekost.dashboard.kosputra

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import muji.dev.onekost.Helper.formatPrice
import muji.dev.onekost.R
import muji.dev.onekost.dashboard.model.CheckIn
import muji.dev.onekost.dashboard.model.KosPutra
import muji.dev.onekost.databinding.ActivityCheckInBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CheckInActivity : AppCompatActivity() {

    private lateinit var checkInBinding: ActivityCheckInBinding
    private lateinit var refData: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkInBinding = ActivityCheckInBinding.inflate(layoutInflater)
        setContentView(checkInBinding.root)

        val data = intent.getParcelableExtra<KosPutra>("checkIn")

        val kamarPerson = arrayOf("A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10")
        val arrayKamar = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, kamarPerson)

        val bulanPerson = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12)
        val arrayBulan = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, bulanPerson)

        val statusPerson = arrayOf("Mahasiswa", "Mahasiswi", "Pelajar", "Pekerja", "Menikah")
        val arrayStatus = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, statusPerson)

        checkInBinding.back.setOnClickListener {
            finish()
        }

        checkInBinding.btnCheckout.setOnClickListener {
            saveData()
        }

        Glide.with(this)
            .load(data?.poster)
            .placeholder(R.drawable.progress_animation)
            .into(checkInBinding.ivCheckout)

        Glide.with(this)
            .load(data?.gambar1)
            .placeholder(R.drawable.progress_animation)
            .into(checkInBinding.gambar1)

        Glide.with(this)
            .load(data?.gambar2)
            .placeholder(R.drawable.progress_animation)
            .into(checkInBinding.gambar2)

        Glide.with(this)
            .load(data?.gambar3)
            .placeholder(R.drawable.progress_animation)
            .into(checkInBinding.gambar3)

        checkInBinding.tvlokasiCheckout.text = data?.tempat
        checkInBinding.tvrateCheckout.text = data?.rating
        checkInBinding.tvKosCheckout.text = data?.category
        checkInBinding.tvPrice.formatPrice(data?.price)

        val myCalender = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalender.set(Calendar.YEAR, year)
            myCalender.set(Calendar.MONTH, month)
            myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            systemCalender(myCalender)
        }

        checkInBinding.btnDate.setOnClickListener {
            DatePickerDialog(this, datePicker, myCalender.get(Calendar.YEAR), myCalender.get(Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH)).show()
        }

        checkInBinding.spKamar.adapter = arrayKamar
        checkInBinding.spKamar.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                checkInBinding.resultKamar.text = kamarPerson[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(applicationContext, "Silahkan masukan no Kamar", Toast.LENGTH_SHORT).show()
            }
        }

        checkInBinding.spBulan.adapter = arrayBulan
        checkInBinding.spBulan.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                checkInBinding.resultBulan.text = bulanPerson[position].toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(applicationContext, "Silahkan masukan jumlah Sewa", Toast.LENGTH_SHORT).show()
            }
        }

        checkInBinding.spStatus.adapter = arrayStatus
        checkInBinding.spStatus.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                checkInBinding.resultStatus.text = statusPerson[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(applicationContext, "Silahkan pilih Status", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun saveData() {
        refData = FirebaseDatabase.getInstance().getReference("UserCheckIn")
        auth = FirebaseAuth.getInstance()
        val data = intent.getParcelableExtra<KosPutra>("checkIn")

        val nama = auth.currentUser?.displayName.toString()
        val idUser = auth.currentUser?.uid.toString()
        val emailUser = auth.currentUser?.email.toString()
        val photoUser = auth.currentUser?.photoUrl.toString()
        val poster = data?.poster
        val statusUser = checkInBinding.resultStatus.text.toString()
        val kamarUser = checkInBinding.resultKamar.text.toString()
        val sewaUser = checkInBinding.resultBulan.text.toString()
        val phoneUser = checkInBinding.etPhone.text.toString()
        val alamatUser = checkInBinding.etAlamat.text.toString()
        val hasil = data?.price
        val priceUser = hasil!!.toInt() * sewaUser.toInt()
        val fasilitas = checkInBinding.tvFasilitas.text.toString()
        val pending = checkInBinding.pending.text.toString()
        val tanggal = checkInBinding.tvDate.text.toString()

        if (phoneUser.isEmpty()) {
            checkInBinding.etPhone.error = "Silahkan masukan no WhatsApp anda"
            checkInBinding.etPhone.requestFocusFromTouch()
        } else if (alamatUser.isEmpty()) {
            checkInBinding.etAlamat.error = "Silahkan masukan alamat lengkap anda"
            checkInBinding.etAlamat.requestFocusFromTouch()
        }

        val userId = refData.push().key
        refData.setValue("Free", fasilitas)
        refData.setValue("Proses", pending)
        val checkIn = CheckIn(nama, idUser, emailUser, photoUser, poster, statusUser, kamarUser, sewaUser.toInt(), phoneUser, alamatUser, priceUser, fasilitas, pending, tanggal)

        if (phoneUser.isEmpty() && alamatUser.isEmpty()) {
            if (userId != null) {
                refData.child(userId).setValue(checkIn).addOnCanceledListener {
                    refData.removeValue()
                }
            }
        } else {
            if (userId != null) {
                refData.child(userId).setValue(checkIn).addOnCompleteListener {
                    val intent = Intent(this, CheckOutActivity::class.java)
                    startActivity(intent)
                    finishAndRemoveTask()
                }
            }
        }
    }

    private fun systemCalender(myCalender: Calendar) {
        val sdf = DateFormat.getDateInstance(SimpleDateFormat.FULL)
        checkInBinding.tvDate.text = sdf.format(myCalender.time)
    }
}