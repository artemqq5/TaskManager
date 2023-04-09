package com.traghwrls.timeseverywhere

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.traghwrls.timeseverywhere.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val clientBuilder by lazy {
        ClientBuilder()
    }

    private val adapterList by lazy {
        AdapterList()
    }

    companion object {
        private val listNet = MutableLiveData<TimeModel?>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.adapter = adapterList

        val corotExc = CoroutineExceptionHandler { _, _ ->
            Snackbar.make(binding.root, "Some error, try change connection", Snackbar.LENGTH_SHORT)
                .show()

            lifecycleScope.launch(Dispatchers.Main) {
                binding.searchButton.isEnabled = true
            }
        }

        listNet.observe(this) {
            it?.let {
                val list = listOf(it).toSet().toList()
                adapterList.changeList((adapterList.myList + list))
                binding.clearButton.isEnabled = (adapterList.myList + list).isNotEmpty()
            }
        }

        binding.clearButton.setOnClickListener {
            adapterList.changeList(emptyList())
            binding.clearButton.isEnabled = false
        }

        binding.searchButton.setOnClickListener {
            binding.searchButton.isEnabled = false

            lifecycleScope.launch(corotExc) {
                listNet.postValue(getRequest())

                withContext(Dispatchers.Main) {
                    binding.searchButton.isEnabled = true
                }
            }

        }

        binding.inputText.editText?.addTextChangedListener {
            binding.searchButton.isEnabled = it!!.isNotEmpty() && !it.startsWith(" ")
        }


    }

    private suspend fun getRequest(): TimeModel? {
        return withContext(Dispatchers.IO) {
            clientBuilder.clientRetrofit.getTimeLocation(binding.inputText.editText?.text.toString())
        }
    }
}