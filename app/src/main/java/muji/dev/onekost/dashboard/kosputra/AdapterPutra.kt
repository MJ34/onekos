package muji.dev.onekost.dashboard.kosputra

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
import muji.dev.onekost.dashboard.model.KosPutra
import kotlin.collections.ArrayList

class AdapterPutra(private var dataPutra: ArrayList<KosPutra>, private var listenerPutra: (KosPutra) -> Unit): RecyclerView.Adapter<AdapterPutra.ViewHolder>() {

    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPutra.ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        context = parent.context
        var inflatedView = layoutInflater.inflate(R.layout.item_list_putra, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: AdapterPutra.ViewHolder, position: Int) {
        holder.bindItem(dataPutra[position], listenerPutra, context)
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private var poster: ImageView = view.findViewById(R.id.ivPoster)
        private var lokasi: TextView = view.findViewById(R.id.tvLokasi)
        private var harga: TextView = view.findViewById(R.id.tvHarga)
        private var rating: TextView = view.findViewById(R.id.tvRate)

        fun bindItem(dataPutra: KosPutra, listenerPutra: (KosPutra) -> Unit, context: Context) {
            Glide.with(context)
                .load(dataPutra.poster)
                .placeholder(R.drawable.progress_animation)
                .into(poster)

            lokasi.text =  dataPutra.tempat
            harga.formatPrice(dataPutra.price)
            rating.text = dataPutra.rating

            itemView.setOnClickListener {
                listenerPutra(dataPutra)
            }
        }
    }

    override fun getItemCount(): Int = dataPutra.size
}