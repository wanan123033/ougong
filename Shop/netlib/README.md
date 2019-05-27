## 对于大量接口的封装。
因为我们Retrofit只会返回一个service。service中的调用都只会返回作为参数传入Rxjava，这就造成如果有大量接口。
我的解决办法是通过函数作为参数来解决这个问题。

## RxLifecycle 
这个是个有用的东西，但是我目前不太想用，我可以直接取消我的请求就可以了，因为对于RXjava的一些细节暂时不是精通，不想引入太多变量

## 关于设计模式
每一个页面c 都必须有一个回调，这里会有一个问题，是聚合还是继承。其实这里完全把网络请求设计成单例模式如下：
```java
HttpMethods.getInstance().getTopMovie(new ProgressSubscriber(getTopMovieOnNext, MainActivity.this), 0, 10);
```
这确实挺好的，不过因为一些错误的处理能力较差，这里我认为还是直接通过聚合来，也不用乱传contex。
这个设计模式我也搞不懂，这里已经做到很简单了，没必要在这里吹毛求疵了，好好的写代码吧。


## 版本
V1 是通过生命周期自动结束请求，这实例代码如下：
```
Observable observable;
            Disposable disposable = observable.compose(this.<Long>bindUntilEvent(ActivityEvent.PAUSE))   //this 是activity或者Fragment
            .subscribe(new Consumer<Long>() {
                @Override
                public void accept(Long num) throws Exception {
                    Log.i(TAG, "Started in onCreate(), running until onPause(): " + num);
                }
            });
            
```

v1是非常灵活的一种方法，可以写一个present来处理逻辑。这里注意关于okhttp实例尽量少初始化。可以使用单例模式。
对于V2版本，需要写一个专门用来处理所有逻辑的接口。代码量变得很多，但是逻辑会相对清晰。
V1 和V2 暂时觉得V1适合mvp设计模式，稍微进行封装，V2写更复杂的逻辑
