# KotlinMvp
Rx+retrofit+mvp
mvp 
是一种设计模式，p层相当于中介，用来m层和v层的交互

这里主要用rx,reftrofit,okhttp 来做请求网络的 

1.V层  用来请求网络的开始，和返回数据（对象或者请求数据的错误）


    interface Contract {
    interface View : BaseView<Presenter> {
        fun <T> setData(type: String, bean: T)
        fun  onError( type: String,error: Throwable)
    }

    interface Presenter : BasePresenter {
        fun <T> requestData(type: String, method:String,url: String?, map: HashMap<*, *>?,vararg param: Any?)
    }

    }

setData  是p层解析完成 返回的对象 或者是list集合
onError 联网错误的回调
requestData 请求网络参数     下面详细说明
这个BasePresenter 是p层的   下面详细说明

2.m层
来处理真是的网络的请求的 

    class HomeModel {
    
    val retrofitClient = RetrofitClient.getInstance()
    val apiService = retrofitClient.create(ApiService::class.java)
    fun <T> loadData(vararg value: Any?): Observable<T>? {
    var isfalg = true
    if (value.size == 2) {
    isfalg = false
    }
    when (isfalg) {
    true -> return apiService?.getHomeData<HomeBean>() as Observable<T>
    false -> return apiService?.getHomeMoreData<HomeBean>(value[1] as String, "2") as Observable<T>
    }
    }
    
    
    fun <T> FindData(vararg value: Any?): Observable<T>? {
    return apiService?.getFindData() as Observable<T>
    }
    }

3.p层

    interface BasePresenter {
    fun<T> start(vararg value:Any)
    fun<T> start( value:HashMap<String,Any>)
    }
BasePresenter 用来进行网络请求 ，这个传递参数 写两种方式  一种是长参数  （就是随意穿第多个参数） 一个使用map的方式

   长参数

    /**
    * value[0] 返回类型 多个请求同时进行 ，根据此字段来判断 必传
    * value[1] 请求网络的方法名 必传
    * value[2] 如果是用rf 直接用 URL 来请求的url地址
    * value[3] 如果 是请求 网络的时候，有表单 此map为表单
    * value[4] 用 rf 请求所带的参数 如 fun <T> getHomeMoreData(@Query("date") date: String, @Query("num") num: String): Observable<HomeBean>
    * date num 传递的就是这两个的值
    */
    override fun <T> start(vararg value: Any) {
    if (value == null || value.size < 2) {
    return
    }
    var type: String = value[0] as String
    var method: String = value[1] as String
    var url: String? = null
    if (value.size > 2) {
    url = value[2] as String
    }
    
    var map: HashMap<*, *>? = null
    if (value.size == 4) {
    map = value[3] as HashMap<*, *>
    }
    requestData<T>(type, method, url, map)
    }


使用map

    /**
    * 传递参数 通过map的方式
    */
    override fun <T> start(value: HashMap<String, Any>) {
    
    requestData<T>(
    value.get("type") as String,//返回类型 多个请求同时进行 ，根据此字段来判断
    value.get("method") as String, //请求网络的方法名
    value.get("url") as? String, //如果是用rf 直接用 URL 来请求的url地址
    value.get("map") as? HashMap<*, *>,//如果 是请求 网络的时候，有表单 此map为表单
    value.get("param") //用 rf 请求所带的参数 如 fun <T> getHomeMoreData(@Query("date") date: String, @Query("num") num: String): Observable<HomeBean>
    //date num 传递的就是这两个的值
    )
    }

两个都调用了requestData（）方法

    override fun <T> requestData(type: String, method: String, url: String?, map: HashMap<*, *>?, vararg param: Any?) {
    //通过反射进行动态代理
    val observable: Observable<T>? = let { Dynamic.invoke(mModel.javaClass.name, method, param) }
    //p层与v层的交互 对view 返回数据
    CustomData(observable, type)
    }
    requestData是通过反射来进行对网络的请求进行动态的代理
    
    class Dynamic {
    companion object {
    fun <T> invoke(className: String, methodName: String, vararg value: Any): T? {
    val clazz = Class.forName(className)
    val instance = clazz.newInstance()
    val methods = clazz.declaredMethods
    var executeMethods: Method? = null
    
    for (method in methods) {
    if (method.name == methodName) {
    executeMethods = method
    break
    }
    }
    executeMethods?.isAccessible = true
    Log.e("executeMethods", "executeMethods--" + executeMethods)
    return executeMethods?.invoke(instance, value) as T?
    }
    }
    }
这个是反射的进行的操作，通过反射，去HomeModel 中找相对应的方法
CustomData(observable, type) 方法是p层与v层的交互 对view 返回数据

fun <T> CustomData(observable: Observable<T>?, type: String) {
if (mView == null) {
return
}
observable?.applySchedulers()?.subscribe({ beans: T ->
mView?.setData(type, beans)
}, { error: Throwable ->
mView?.onError(type, error)
})

}

然后就是在高度的封装一下，可以通过继承 baseActivity或者 baseFragment可以直接使用
activity 实现Contract.View

    abstract class BaseActivity : AppCompatActivity(), Contract.View {
    
    override fun onStart() {
    super.onStart()
    initview()
    }
    
    override fun onDestroy() {
    super.onDestroy()
    }
    
    abstract fun initview()
    }
    使用
    
    class VideoPlayerActivity : BaseActivity() {
    override fun onError(type: String, error: Throwable) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    
    override fun <T> setData(type: String, bean: T) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    
    
    }

fragment 实现Contract.View


    abstract class BaseFragment : Fragment(), Contract.View {
    var isFirst: Boolean = false
    var rootView: View? = null
    var isFragmentVisiable: Boolean = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    rootView = getLayoutView()
    initView()
    }
    
    
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
    super.setUserVisibleHint(isVisibleToUser)
    if (isVisibleToUser) {
    isFragmentVisiable = true
    }
    if (rootView == null) {
    return
    }
    //可见，并且没有加载过
    if (!isFirst && isFragmentVisiable) {
    onFragmentVisiableChange(true)
    return
    }
    //由可见——>不可见 已经加载过
    if (isFragmentVisiable) {
    onFragmentVisiableChange(false)
    isFragmentVisiable = false
    }
    }
    
    open protected fun onFragmentVisiableChange(b: Boolean) {
    
    }
    
    
    // abstract fun getLayoutResources(): Int
    abstract fun getLayoutView(): View
    
    abstract fun initView()
    
    override fun onDestroy() {
    super.onDestroy()
    rootView = null
    }
    }

使用

    class FindFragment : BaseFragment() {
    override fun onError(type: String, error: Throwable) {
    //请求错误进行处理
    }
    
    
    var mPresenter: ParsingPresenter? = null
    var mList: MutableList<FindBean>? = null
    var binding: FindHomeBinding? = null
    override fun <T> setData(type: String, bean: T) {//返回数据进行渲染
    if (!"findFragment".equals(type)) {
    return
    }
    mList = bean as MutableList<FindBean>
    binding?.data = mList as ArrayList<FindBean>
    
    }
    
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, R.layout.find_home, container, false) as FindHomeBinding
    return binding?.root
    }
    
    override fun getLayoutView(): View {
    return binding!!.root
    }
    
    override fun initView() {
    mPresenter = ParsingPresenter(this)
    var map = HashMap<String, Any>()
    map.put("type", "findFragment")
    map.put("method", "FindData")
    mPresenter?.start<MutableList<FindBean>>(map)//通过map 进行网络请求
    binding?.data = mList as ArrayList<FindBean>?
    }
    
    }
然后就拦截器和缓存

/**
* 日志拦截
*/

    fun getLogInterceptor(): HttpLoggingInterceptor {
    // 日志显示级别
    val level = HttpLoggingInterceptor.Level.BODY
    // 新建日志拦截器
    val loggingInterceptor = HttpLoggingInterceptor { message -> Log.e("ApiUrl", "--->$message") }
    loggingInterceptor.level = level
    return loggingInterceptor
    }
    
    class CacheInterceptor(context: Context) : Interceptor {
    val context = context
    override fun intercept(chain: Interceptor.Chain?): Response? {
    var request = chain?.request()
    if (Utils.isNetworkAvailable(context)) {
    val response = chain?.proceed(request)
    // read from cache for 60 s
    val maxAge = 60
    return response?.newBuilder()?.removeHeader("Pragma")?.removeHeader("Cache-Control")?.header("Cache-Control", "public, max-age=" + maxAge)?.build()
    } else {
    request = request?.newBuilder()?.cacheControl(CacheControl.FORCE_CACHE)?.build()
    val response = chain?.proceed(request)
    //set cahe times is 3 days
    val maxStale = 60 * 60 * 24 * 3
    return response?.newBuilder()?.removeHeader("Pragma")?.removeHeader("Cache-Control")?.header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)?.build()
    }
    }
    }
    
    
    
    
    
    
    