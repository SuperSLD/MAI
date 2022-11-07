package com.raspisanie.mai.ui.main.info.roadmap.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.raspisanie.mai.R

class FragmentA(val firstName: String, val lastName: String) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_info, container, false)

    // more code
}

class FragmentB() : Fragment(R.layout.fragment_info) {
    // more code
}