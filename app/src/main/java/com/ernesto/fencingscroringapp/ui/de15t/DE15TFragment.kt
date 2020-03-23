package com.ernesto.fencingscroringapp.ui.de15t

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ernesto.fencingscroringapp.R

class DE15TFragment : Fragment() {

    private lateinit var dE15TViewModel: DE15TViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dE15TViewModel =
            ViewModelProviders.of(this).get(DE15TViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_direct_elimination_15_t, container, false)

        return root
    }

}
