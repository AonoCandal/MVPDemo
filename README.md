# MVPDemo
封装好的, 不会发生内存泄漏的MVP框架

注意: Presenter层使用的是抽象类

BaseActivity 可以进行扩展, 使用Rxjava处理CompositeSubscription, 其他什么都不做

继承自BaseActivity的类只需要实现createPresenter()方法来创建需要的Presenter(需要继承自BasePresenter)
mPresenter做完成员变量可以直接使用
在Presenter中调用ObtainView获取View
