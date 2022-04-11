package com.nmssalman.qualitapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nmssalman.qualitapps.voting.VotingFragment
import com.nmssalman.qualitapps.userslist.UsersFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    private val usersListFragment = UsersFragment()
    private val votingFragment = VotingFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(usersListFragment)
        bottomNavigation = findViewById(R.id.bottom_navigation) as BottomNavigationView
        bottomNavigationHandler()
    }
    private fun bottomNavigationHandler(){
        bottomNavigation.setOnNavigationItemSelectedListener{
            when(it.itemId)
            {
                R.id.menu_list -> replaceFragment(usersListFragment)
                R.id.menu_firebase -> replaceFragment(votingFragment)
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment)
    {
        if(fragment != null)
        {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}