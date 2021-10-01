package com.mobilefreelancer.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mobilefreelancer.R
import com.mobilefreelancer.extensions.Extensions.toast
import com.mobilefreelancer.fragments.ChatFragment
import com.mobilefreelancer.fragments.FeedFragment
import com.mobilefreelancer.fragments.ProfileFragment
import com.mobilefreelancer.fragments.SearchFragment
import com.mobilefreelancer.utils.FirebaseUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_feed.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val feedFragment = FeedFragment()
        val searchFragment = SearchFragment()
        val chatFragment = ChatFragment()
        val profileFragment = ProfileFragment()

        setCurrentFragment(feedFragment)


        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_feed -> setCurrentFragment(feedFragment)
                R.id.nav_chat -> setCurrentFragment(chatFragment)
                R.id.nav_search -> setCurrentFragment(searchFragment)
                R.id.nav_profile -> setCurrentFragment(profileFragment)
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }


    internal fun signOut(){
        FirebaseUtils.firebaseAuth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        toast("signed out")
        finish()
    }
}

