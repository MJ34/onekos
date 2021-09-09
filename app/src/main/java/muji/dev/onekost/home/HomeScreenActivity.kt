package muji.dev.onekost.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import muji.dev.onekost.R
import muji.dev.onekost.chat.ChatFragment
import muji.dev.onekost.dashboard.DashboardFragment
import muji.dev.onekost.databinding.ActivityHomeScreenBinding
import muji.dev.onekost.location.LocationFragment
import muji.dev.onekost.waiting.WaitingFragment

class HomeScreenActivity : AppCompatActivity() {
    private lateinit var homeBinding: ActivityHomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        replaceFragment(DashboardFragment.newInstance())

        homeBinding.bottomNavigation.show(0)
        homeBinding.bottomNavigation.add(MeowBottomNavigation.Model(0, R.drawable.ic_home))
        homeBinding.bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_hotel))
        homeBinding.bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_chat_))
        homeBinding.bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.ic_location))

        homeBinding.bottomNavigation.setOnClickMenuListener {
            when(it.id) {
                0 -> {
                    replaceFragment(DashboardFragment.newInstance())
                }
                1 -> {
                    replaceFragment(WaitingFragment.newInstance())
                }
                2 -> {
                    replaceFragment(ChatFragment.newInstance())
                }
                3 -> {
                    replaceFragment(LocationFragment.newInstance())
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragmentContainer, fragment)
        fragmentTransition.commit()
    }
}