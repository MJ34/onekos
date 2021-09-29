package muji.dev.onekost.waiting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import muji.dev.onekost.Helper.formatPrice
import muji.dev.onekost.R
import muji.dev.onekost.dashboard.model.Waiting

class AdapterWaiting(private var datalist: ArrayList<Waiting>,
                     private var listnerWaiting: (Waiting) -> Unit): RecyclerView.Adapter<AdapterWaiting.ViewHolder>() {

    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterWaiting.ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        context = parent.context
        var inflatedView = layoutInflater.inflate(R.layout.item_adapter_waiting, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: AdapterWaiting.ViewHolder, position: Int) {
        holder.bindItem(datalist[position], listnerWaiting, context)
    }

    override fun getItemCount(): Int = datalist.size

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private var poster: ImageView = view.findViewById(R.id.ivWait)
        private var status: TextView = view.findViewById(R.id.tvStatus)
        private var price: TextView = view.findViewById(R.id.tvPrice)
        private var sewa: TextView = view.findViewById(R.id.tvSewa)
        private var pending: TextView = view.findViewById(R.id.tvCaWait)
        private var delete: ImageView = view.findViewById(R.id.ictrash)

        fun bindItem(data: Waiting, listnerWaiting: (Waiting) -> Unit, context: Context) {
            val database = FirebaseDatabase.getInstance().getReference("Checkin").child(data.idUser!!)

            Glide.with(context)
                .load(data.poster)
                .placeholder(R.drawable.progress_animation)
                .into(poster)

            status.text = data.statusUser
            price.formatPrice(data.priceUser)
            sewa.text = "${data.sewaUser} Bulan"
            pending.text = data.pending

            delete.setOnClickListener {
                database.removeValue()
                Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
            }

            itemView.setOnClickListener {
                listnerWaiting(data)
            }

        }
    }

}