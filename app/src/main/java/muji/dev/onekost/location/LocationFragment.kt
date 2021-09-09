package muji.dev.onekost.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import muji.dev.onekost.R
import muji.dev.onekost.databinding.FragmentLocationBinding

class LocationFragment : Fragment() {

    private lateinit var locationBinding: FragmentLocationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        locationBinding = FragmentLocationBinding.inflate(layoutInflater)
        return (locationBinding.root)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            LocationFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}