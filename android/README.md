# Android 根项目


## Feature

### ViewModelProvider
viewModel对象是存储在ViewModelStore中的，那么如果我们创建一个全局使用的ViewModelStore并且在获取viewModel对象的时候从它里面获取就可以了。
viewModel是通过ViewModelProvider的get方法获取的，一般是`ViewModelProvider(owner: ViewModelStoreOwner, factory: Factory).get(ViewModel::class.java)`。
如何将ViewModelProvider与ViewModelStore关联起来?  纽带就是ViewModelStoreOwner, `ViewModelStoreOwner`是一个接口，需要实现`getViewModelStore()`方法，而该方法返回的就是ViewModelStore:
```kotlin
interface ViewModelStoreOwner {

    /**
     * The owned [ViewModelStore]
     */
    val viewModelStore: ViewModelStore
}
```
让某个类实现这个接口，重写方法返回我们定义的ViewModelStore就可以了。
至于上面ViewModelProvider构造方法的第二个参数Factory是什么呢?
源码中提供了二种Factory，一种是`NewInstanceFactory`，一种是`AndroidViewModelFactory`，它们的主要区别是：

1. NewInstanceFactory创建ViewModel时，会为每个Activity或Fragment创建一个新的ViewModel实例，这会导致ViewModel无法在应用程序的不同部分共享数据。（ComponentActivity源码getDefaultViewModelProviderFactory方法）

2. AndroidViewModelFactory可以访问应用程序的全局状态，并且ViewModel实例可以在整个应用程序中是共享的。

> 要创建全局可用的ViewModelProvider，需要用的是AndroidViewModelFactory。



## Reference
[全局可调用的ViewModel对象](https://juejin.cn/post/7233686528328286245)