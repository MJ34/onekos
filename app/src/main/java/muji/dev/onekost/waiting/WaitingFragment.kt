package muji.dev.onekost.waiting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import muji.dev.onekost.dashboard.model.Waiting
import muji.dev.onekost.databinding.FragmentWaitingBinding

class WaitingFragment : Fragment() {

    private lateinit var waitingBinding: FragmentWaitingBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var waitlist = ArrayList<Waiting>()
    private lateinit var adapterWaiting: AdapterWaiting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        waitingBinding = FragmentWaitingBinding.inflate(layoutInflater)
        return (waitingBinding.root)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            WaitingFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        waitingBinding.rvWait.layoutManager = LinearLayoutManager(context)

        adapterWaiting = AdapterWaiting(waitlist) {

        }
        showDatabase()
    }

    private fun showDatabase() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Checkin")
        auth = FirebaseAuth.getInstance()
        databaseReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                waitlist.clear()
                val id = auth.currentUser?.uid
                for (i in snapshot.children) {
                    val wait = i.getValue(Waiting::class.java)
                    if (wait!!.idUser.equals(id)) {
                        waitlist.add(wait!!)
                    }
                    waitingBinding.rvWait.adapter = adapterWaiting
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}