package com.thehoegrls.taskmasponsibly

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
import com.thehoegrls.taskmasponsibly.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        var idMessage = 0
    }

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

        binding.scheduleButton.setOnClickListener {
            picker.addOnPositiveButtonClickListener {
                val timeInMillis = ((picker.hour) * 600000L * 6L + (picker.minute) * 1000L * 60L)

                val hour = timeInMillis / (600000L * 6L)
                val minute = ((timeInMillis % (600000L * 6L)) / (1000L * 60L))

                Snackbar.make(
                    binding.root,
                    resources.getString(R.string.notification_text, hour, minute),
                    Snackbar.LENGTH_LONG
                ).show()

                Intent(this, MyAlarmReceiver::class.java).apply {
                    putExtra("textData", binding.textField.editText!!.text.toString())
                    PendingIntent.getBroadcast(
                        this@MainActivity,
                        ++idMessage,
                        this,
                        FLAG_IMMUTABLE
                    ).apply {
                        alarmManager.set(
                            AlarmManager.RTC_WAKEUP,
                            System.currentTimeMillis() + timeInMillis,
                            this
                        )
                    }
                }


            }

            picker.show(supportFragmentManager, "tag")
        }

    }
}

