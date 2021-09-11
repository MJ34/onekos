package muji.dev.onekost.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import muji.dev.onekost.R
import muji.dev.onekost.dashboard.kosputra.model.ImageData
import muji.dev.onekost.dashboard.profile.ProfileActivity
import muji.dev.onekost.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var imageList = ArrayList<ImageData>()
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
    ): View? {
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
    }
}