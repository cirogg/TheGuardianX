package com.example.theguardianx.viewmodels

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner

abstract class StateViewModel (val state: SavedStateHandle) : ViewModel() {
    class Factory(owner: SavedStateRegistryOwner, args: Bundle?) : AbstractSavedStateViewModelFactory(owner, args) {
        override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
            return modelClass.getConstructor(SavedStateHandle::class.java).newInstance(handle)
        }
    }

    /** Save these lines in each VM */
    protected fun <T> bindData(key: String, data: MutableLiveData<T>): T? {
        data.value = state[key]
        data.observeForever { state.set(key, it) }
        return state[key]
    }
}