package com.zp.multiple_flutters.android

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner


/**
 * Created by ZhaoPan on 2024/2/28
 */
object AppScopeVMProvider : ViewModelStoreOwner {
    private val _viewModelStore: ViewModelStore = ViewModelStore()

    override val viewModelStore: ViewModelStore
        get() = _viewModelStore

    private val mApplicationProvider: ViewModelProvider by lazy {
        ViewModelProvider(
            AppScopeVMProvider,
            ViewModelProvider.AndroidViewModelFactory.getInstance(MyApp.getInstance())
        )
    }

    fun <T : ViewModel> getAppScopeVM(modelClass: Class<T>): T {
        return mApplicationProvider[modelClass]
    }

}

//定义扩展方法
inline fun <reified VM : ViewModel> Fragment.getAppViewModel(): VM {
    (this.requireActivity().application as? MyApp).let {
        if (it == null) {
            throw NullPointerException("Application does not inherit from BaseApplication")
        } else {
            return AppScopeVMProvider.getAppScopeVM(VM::class.java)
        }
    }
}

//定义扩展方法
inline fun <reified VM : ViewModel> AppCompatActivity.getAppViewModel(): VM {
    (this.application as? MyApp).let {
        if (it == null) {
            throw NullPointerException("Application does not inherit from BaseApplication")
        } else {
            return AppScopeVMProvider.getAppScopeVM(VM::class.java)
        }
    }
}