package muji.dev.onekost.dashboard.kosputra

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import muji.dev.onekost.Helper.formatPrice
import muji.dev.onekost.R
import muji.dev.onekost.dashboard.model.CheckIn

class AdapterCheckout(private var datalist: ArrayList<CheckIn>,
                      private var listnerCheckout: (CheckIn) -> Unit): RecyclerView.Adapter<AdapterCheckout.ViewHolder>() {

    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCheckout.ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        context = parent.context
        var inflatedView = layoutInflater.inflate(R.layout.item_adapter_checkout, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: AdapterCheckout.ViewHolder, position: Int) {
        holder.bindItem(datalist[position], listnerCheckout, context)
    }

    override fun getItemCount(): Int = datalist.size

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private var nama: TextView = view.findViewById(R.id.tvNama)
        private var status: TextView = view.findViewById(R.id.tvStatus)
        private var kamar: TextView = view.findViewById(R.id.tvKamar)
        private var sewa: TextView = view.findViewById(R.id.tvSewa)
        private var alamat: TextView = view.findViewById(R.id.tvAlamat)
        private var tanggal: TextView = view.findViewById(R.id.tvTanggal)
        private var total: TextView = view.findViewById(R.id.tvTotal)

        fun bindItem(data: CheckIn, listnerCheckout: (CheckIn) -> Unit, context: Context) {
            nama.text = data.nama
            status.text = data.statusUser
            kamar.text = data.kamarUser
            sewa.text = "${data.sewaUser} Bulan"
            alamat.text = data.alamatUser
            tanggal.text = data.tanggal
            total.formatPrice(data.priceUser)

            itemView.setOnClickListener {
                listnerCheckout(data)
            }

        }
    }

}