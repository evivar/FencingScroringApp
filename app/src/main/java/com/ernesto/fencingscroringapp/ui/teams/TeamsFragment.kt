package com.ernesto.fencingscroringapp.ui.teams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ernesto.fencingscroringapp.R

class TeamsFragment : Fragment() {

    private lateinit var teamsViewModel: TeamsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        teamsViewModel =
            ViewModelProviders.of(this).get(TeamsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_teams, container, false)

        return root
    }
}
