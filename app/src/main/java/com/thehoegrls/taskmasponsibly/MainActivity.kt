package com.thehoegrls.taskmasponsibly

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_MUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.format.DateFormat.is24HourFormat
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
import com.thehoegrls.taskmasponsibly.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val isSystem24Hour = is24HourFormat(this)
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(clockFormat)
                .setInputMode(INPUT_MODE_CLOCK)
                .setHour(12)
                .setMinute(10)
                .setTitleText(resources.getString(R.string.choice_time))
                .build()

        binding.textField.editText?.addTextChangedListener {
            binding.scheduleButton.isEnabled = it!!.isNotEmpty() && !it.toString().startsWith(" ")
        }

        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    picker.addOnPositiveButtonClickListener {
                        val timeInMillis =
                            ((picker.hour) * 600000L * 6L + (picker.minute) * 1000L * 60L)

                        val hour = timeInMillis / (600000L * 6L)
                        val minute = ((timeInMillis % (600000L * 6L)) / (1000L * 60L))

                        Snackbar.make(
                            binding.root,
                            resources.getString(R.string.notification_text, hour, minute),
                            Snackbar.LENGTH_LONG
                        ).show()

                        val intent = Intent(this, MyAlarmReceiver::class.java).apply {
                            putExtra("textData", binding.textField.editText!!.text.toString())
                        }

                        val pendingIntent = PendingIntent.getBroadcast(
                            this@MainActivity,
                            System.currentTimeMillis().toInt(), // Якийсь унікальний код запиту
                            intent,
                            FLAG_IMMUTABLE // використання FLAG_IMMUTABLE
                        )

                        alarmManager.set(
                            AlarmManager.RTC_WAKEUP,
                            System.currentTimeMillis() + timeInMillis,
                            pendingIntent
                        )


                    }

                    picker.show(supportFragmentManager, "tag")
                } else {
                    Snackbar.make(binding.root, "On notification in settings", Snackbar.LENGTH_LONG)
                        .setAction("OK") {
                            val intent = Intent().apply {
                                when {
                                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                                        action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                                        putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                                    }

                                    else -> {
                                        action = "android.settings.APP_NOTIFICATION_SETTINGS"
                                        putExtra("app_package", packageName)
                                        putExtra("app_uid", applicationInfo.uid)
                                    }
                                }
                            }
                            startActivity(intent)
                        }.show()
                }
            }


        binding.scheduleButton.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

    }

}

