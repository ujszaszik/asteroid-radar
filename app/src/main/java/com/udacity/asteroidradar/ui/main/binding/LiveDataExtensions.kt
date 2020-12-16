package com.udacity.asteroidradar.ui.main.binding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

fun <T, O, R> LiveData<T>.combineWith(
    other: LiveData<O>,
    combineFunction: (T?, O?) -> R
): LiveData<R> {
    return MediatorLiveData<R>().also { result ->
        result.addSource(this) {
            result.value = combineFunction(this.value, other.value)
        }
        result.addSource(other) {
            result.value = combineFunction(this.value, other.value)
        }
    }
}