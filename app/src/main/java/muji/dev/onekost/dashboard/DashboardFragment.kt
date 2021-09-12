package muji.dev.onekost.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import muji.dev.onekost.R
import muji.dev.onekost.dashboard.kosputra.AdapterPutra
import muji.dev.onekost.dashboard.kosputra.DetailPutraActivity
import muji.dev.onekost.dashboard.kosputri.AdapterPutri
import muji.dev.onekost.dashboard.model.ImageData
import muji.dev.onekost.dashboard.model.KosPutra
import muji.dev.onekost.dashboard.model.KosPutri
import muji.dev.onekost.dashboard.profile.ProfileActivity
import muji.dev.onekost.databinding.FragmentDashboardBinding
import muji.dev.onekost.home.ProggresDialog

class DashboardFragment : Fragment() {

    private var imageList = ArrayList<ImageData>()
    private var dataPutra = ArrayList<KosPutra>()
    private var dataPutri = ArrayList<KosPutri>()

    private lateinit var adapterPutra: AdapterPutra
    private lateinit var adapterPutri: AdapterPutri
    private lateinit var databaseReference: DatabaseReference
    private lateinit var adapter: ImageAdapter
    private lateinit var dashboardBinding: FragmentDashboardBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardBinding = FragmentDashboardBinding.inflate(layoutInflater)
        return (dashboardBinding.root)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getKos()

        auth = FirebaseAuth.getInstance()

        Glide.with(requireContext())
            .load(auth.currentUser?.photoUrl)
            .placeholder(R.drawable.progress_animation)
            .into(dashboardBinding.ivProfile)

        imageList.add(
            ImageData(
            "https://firebasestorage.googleapis.com/v0/b/one-kos.appspot.com/o/Frame%203.jpg?alt=media&token=978a38c3-9d66-4d8d-ba30-7c528b68b265"
        )
        )

        imageList.add(
            ImageData(
                "https://firebasestorage.googleapis.com/v0/b/one-kos.appspot.com/o/Frame%204.jpg?alt=media&token=f0815ba4-c579-48b1-88a9-d604bbb6bd52"
            )
        )

        adapter = ImageAdapter(imageList)
        dashboardBinding.viewPager.adapter = adapter

        dashboardBinding.ivProfile.setOnClickListener {
            val intent = Intent(context, ProfileActivity::class.java)
            startActivity(intent)
        }

        adapterPutra = AdapterPutra(dataPutra) {
            val intent = Intent(requireContext(), DetailPutraActivity::class.java)
                .putExtra("putra", it)
            startActivity(intent)
        }

        adapterPutri = AdapterPutri(dataPutri) {

        }


        dashboardBinding.rvKosputra.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        dashboardBinding.rvKosputri.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun getKos() {
        val show = ProggresDialog(requireActivity())
        show.showLoading()
        databaseReference = FirebaseDatabase.getInstance().getReference("Dashboard")
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dataPutra.clear()
                for (item in snapshot.child("KosPutra").children) {
                    val putra = item.getValue(KosPutra::class.java)
                    dataPutra.add(putra!!)
                }
                dashboardBinding.rvKosputra.adapter = adapterPutra

                dataPutri.clear()
                for (item in snapshot.child("KosPutri").children) {
                    val putri = item.getValue(KosPutri::class.java)
                    dataPutri.add(putri!!)
                }
                dashboardBinding.rvKosputri.adapter = adapterPutri
                show.dismissLoading()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
                show.showLoading()
            }

        })
    }
}