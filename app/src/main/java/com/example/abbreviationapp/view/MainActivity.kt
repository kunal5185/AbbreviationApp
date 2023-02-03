package com.example.abbreviationapp.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.abbreviationapp.R
import com.example.abbreviationapp.databinding.ActivityMainBinding
import com.example.abbreviationapp.utils.ValidationUtil
import com.example.abbreviationapp.viewmodel.MainViewModel

/**
 * This is MainActivity class, it's the entry point of this app.
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: MainViewModel
    private val adapter = LfListAdapter()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.recyclerview.adapter = adapter

        viewModel =
            ViewModelProvider(
                this,
            )[MainViewModel::class.java]

        binding.mainViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.largeFormList.observe(this) {
            adapter.setLfList(it)
            viewModel.rvVisibility.postValue(View.VISIBLE)
        }

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            binding.searchBtn.id -> {
                binding.abbEditText.hideKeyboard()
                val abbreviation = binding.abbEditText.text.toString()
                val isValidAbbreviation = ValidationUtil.isValid(abbreviation)

                when {
                    !isValidAbbreviation.first -> {
                        Toast.makeText(this, isValidAbbreviation.second, Toast.LENGTH_LONG)
                            .show()
                    }
                    else -> {
                        viewModel.getMeaningsData(abbreviation)
                        binding.recyclerview.smoothScrollToPosition(0)
                    }
                }
            }
            binding.resetBtn.id -> {
                binding.abbEditText.text?.clear()
                viewModel.rvVisibility.postValue(View.GONE)
            }
        }
    }

    private fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}
