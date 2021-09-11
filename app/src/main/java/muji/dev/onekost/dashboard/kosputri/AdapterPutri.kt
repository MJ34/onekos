package muji.dev.onekost.dashboard.kosputri

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import muji.dev.onekost.Helper.formatPrice
import muji.dev.onekost.R
import muji.dev.onekost.dashboard.model.KosPutri

class AdapterPutri(private var dataPutri: ArrayList<KosPutri>, private var listenerPutri: (KosPutri) -> Unit): RecyclerView.Adapter<AdapterPutri.ViewHolder>() {

    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPutri.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.item_list_putri, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: AdapterPutri.ViewHolder, position: Int) {
        holder.bindItem(dataPutri[position], listenerPutri, context)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private var poster: ImageView = view.findViewById(R.id.ivPutri)
        private var lokasi: TextView = view.findViewById(R.id.tvLokasiPutri)
        private var harga: TextView = view.findViewById(R.id.tvHargaPutri)
        private var rating: TextView = view.findViewById(R.id.tvRatingPutri)

        fun bindItem(dataPutri: KosPutri, listenerPutri: (KosPutri) -> Unit, context: Context) {
            Glide.with(context)
                .load(dataPutri.poster)
                .placeholder(R.drawable.progress_animation)
                .into(poster)

            lokasi.text =  dataPutri.tempat
            harga.formatPrice(dataPutri.price)
            rating.text = dataPutri.rating

            itemView.setOnClickListener {
                listenerPutri(dataPutri)
            }
        }
    }

    override fun getItemCount(): Int = dataPutri.size
}