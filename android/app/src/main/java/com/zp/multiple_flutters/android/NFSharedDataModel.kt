package com.zp.multiple_flutters.android

import java.lang.ref.WeakReference


/**
 * Interface for getting notifications when the DataModel is updated.
 */
interface NFSharedDataModelObserver {
    fun onCountUpdate(newCount: Int)
}


/**
 * A singleton/observable data model for the data shared between Flutter and the host platform.
 *
 * This is the definitive source of truth for all data.
 *
 * Created by ZhaoPan on 2024/3/4
 */
object NFSharedDataModel {

    private val observers = mutableListOf<WeakReference<NFSharedDataModelObserver>>()

    var counter = 0
        set(value) {
            field = value
            for (observer in observers) {
                observer.get()?.onCountUpdate(value)
            }
        }

    fun addObserver(observer: NFSharedDataModelObserver) {
        observers.add(WeakReference(observer))
    }

    fun removeObserver(observer: NFSharedDataModelObserver) {
        observers.removeIf {
            if (it.get() != null) it.get() == observer else true
        }
    }

    fun incrementCount() {
        counter += 1
    }
}