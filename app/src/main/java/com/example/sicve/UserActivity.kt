package com.example.sicve

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.sicve.utils.Utils
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        getSupportActionBar()?.hide()
        var intent = getIntent()
        val username = intent.getStringExtra("username")
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        toolbar.setTitle("username: $username")

        val logoutButton = findViewById<Button>(R.id.logout_icon_id)
        logoutButton.setBackgroundResource(R.drawable.ic_logout)
        Utils.buttonEffect(logoutButton)
        logoutButton.setOnClickListener{
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val firstFragment = WalkHighwayFragment()
        val secondFragment = MessagesFragment()
        val thirdFragment = MyVehicleFragment()
        thirdFragment.setCurrentUser(username)
        firstFragment.setCurrentUser(username)
        secondFragment.setCurrentUser(username)

        selectFragment(firstFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.transit_hw_block->selectFragment(firstFragment)
                R.id.messages->selectFragment(secondFragment)
                R.id.my_vehicle->selectFragment(thirdFragment)

            }
            true
        }
    }

    private fun selectFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.user_fragment_id, fragment)
            commit()
        }
    }
}