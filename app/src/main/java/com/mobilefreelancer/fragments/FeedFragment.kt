package com.mobilefreelancer.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobilefreelancer.R
import com.mobilefreelancer.databinding.FragmentFeedBinding
import com.mobilefreelancer.extensions.Extensions.toast
import com.mobilefreelancer.utils.FirebaseUtils
import com.mobilefreelancer.views.LoginActivity
import com.mobilefreelancer.views.MainActivity
import kotlinx.android.synthetic.main.fragment_feed.*

class FeedFragment : Fragment(R.layout.fragment_feed) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logout_button.setOnClickListener {
            (activity as MainActivity).signOut()
        }


    }

}