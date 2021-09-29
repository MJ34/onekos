package muji.dev.onekost.dashboard.kosputra

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import muji.dev.onekost.Helper.formatPrice
import muji.dev.onekost.R
import muji.dev.onekost.dashboard.model.CheckIn
import muji.dev.onekost.dashboard.model.KosPutra
import muji.dev.onekost.databinding.ActivityCheckOutBinding

class CheckOutActivity : AppCompatActivity() {

    private lateinit var checkOutBinding: ActivityCheckOutBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var datalist = ArrayList<CheckIn>()
    private lateinit var adapter: AdapterCheckout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkOutBinding = ActivityCheckOutBinding.inflate(layoutInflater)
        setContentView(checkOutBinding.root)

        val data = intent.getParcelableExtra<KosPutra>("data")

        Glide.with(this)
            .load(data?.poster)
            .placeholder(R.drawable.progress_animation)
            .into(checkOutBinding.ivPoster)

        checkOutBinding.tvLokasi.text = data?.tempat
        checkOutBinding.tvCategory.text = data?.category
        checkOutBinding.tvPrice.formatPrice(data?.price)

        checkOutBinding.rvCheckout.layoutManager = LinearLayoutManager(applicationContext)

        adapter = AdapterCheckout(datalist) {

        }
        getData()
    }

    private fun getData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Checkin")
        auth = FirebaseAuth.getInstance()
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                datalist.clear()
                val id = auth.currentUser?.uid
                val idUser = snapshot.getValue(CheckIn::class.java)?.idUser
                if (id == idUser) {
                    Toast.makeText(applicationContext, "Tidak boleh sewa lebih dari 1 tempat kost", Toast.LENGTH_SHORT).show()
                } else {
                    for (item in snapshot.children) {
                        datalist.clear()
                        val checkout = item.getValue(CheckIn::class.java)
                        datalist.add(checkout!!)
                    }
                    checkOutBinding.rvCheckout.adapter = adapter
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}