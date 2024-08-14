package me.hossamohsen.recipeapp.utils

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class DebouncingTextWatcher(
    lifecycle: Lifecycle,
    private val onDebouncingQueryTextChange: (String?) -> Unit
) : TextWatcher {
    private val debouncePeriod: Long = 500

    private val coroutineScope = lifecycle.coroutineScope

    private var searchJob: Job? = null

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(newText: CharSequence?, start: Int, before: Int, count: Int) {
        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            newText?.let {
                delay(debouncePeriod)
                onDebouncingQueryTextChange(newText.toString())
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {
    }
}