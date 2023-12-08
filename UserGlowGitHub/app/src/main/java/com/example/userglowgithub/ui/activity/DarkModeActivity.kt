package com.example.userglowgithub.ui.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.example.userglowgithub.R
import com.example.userglowgithub.databinding.ActivityDarkModeBinding
import com.google.android.material.switchmaterial.SwitchMaterial

class DarkModeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDarkModeBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDarkModeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val isNightModeEnabled = sharedPreferences.getBoolean("night_mode", false)
        switchTheme.isChecked = isNightModeEnabled

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPreferences.edit().putBoolean("night_mode", true).apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPreferences.edit().putBoolean("night_mode", false).apply()
            }
        }
    }
}
