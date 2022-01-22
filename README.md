
[[Android] 아키텍처 패턴 (Architecture Pattern), MVP ? _ hyooosong velog](https://velog.io/@hyooosong/Android-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98-%ED%8C%A8%ED%84%B4-Architecture-Pattern-MVP)

<br>

## ✨ Packaging
📂com.example.moviereview   
┣ 📂network  
┣ 📂room   
┣ 📂presenter  
┣ 📂ui  
┃ ┣ 📂main  
┃ ┣ 📂search    
┃ ┣ 📂review  
┗ 📂utils  

<br>

### 🌈 Model
`data class` and `data processing`
- Room
- Network

### 🌈 View
`View Update` using `interface`
- Activity
- Fragment

### 🌈 Presenter
`User Event Action` using `interface`
- Contract
- Presenter

<br>
<hr>


**1. Base Presenter**
```kotlin
interface BasePresenter<T> {
    fun takeView(view: T)
    fun dropView()
}
```
- `fun takeView(view: T)` : Create or Bind view
- `fun dropView()` : Destroy or Unbind view

<br>

**2. Contract**
```kotlin
interface SearchContract {
    interface SearchView {
        fun listToAdapter(list: List<MovieResponse.Item>?)
        fun showToast(message: String)
        fun showReviewDialog(item: MovieResponse.Item, application: Application)
        fun onClickMore(link: String)
    }

    interface SearchPresenter : BasePresenter<SearchView> {
        fun callMovieList()
    }
}
```
- `interface SearchContract` : interface to connect View and Presenter
- `interface SearchView` : interface for methods on View
- `interface searchPresenter` : interface for methods of User Event

<br>

**3. SearchPresenter**
```kotlin
class SearchPresenter : SearchContract.SearchPresenter {
    private var searchView: SearchContract.SearchView? = null
    ...

    override fun takeView(view: SearchContract.SearchView) {
        searchView = view
    }

    override fun dropView() {
        searchView = null
    }

    override fun callMovieList() {
        if (searchMovie.value.isNullOrEmpty()) {
            searchView?.showToast("검색어를 입력해주세요")
        }
        
        ...
        
        call.enqueueListener(
            onSuccess = {
                searchView?.listToAdapter(it.body()?.items)
            },
            onError = {
                searchView?.showToast("다시 시도해주세요")
            }
        )
    }
}
```
- `takeView(view)` : Connect View and Presenter (searchView = view)
- `dropView()` : Notify Presenter that View has been removed
- `callMovieList()` : 
  - `searchView?.showToast()` : Ask View to show Toast message
  - `searchView?.listToAdapter()` : Pass the **Data** recevied from the Model(Network) **to the View**

<br>

**4. SearchFragment**
```kotlin
class SearchFragment : Fragment(), SearchContract.SearchView {
    private lateinit var searchPresenter: SearchPresenter
    ...
   
    private fun initPresenter() {
        searchPresenter = SearchPresenter()
        searchPresenter.takeView(this)
    }

    private fun onClickSearch() {
        binding.btnSearch.setOnClickListener {
            searchPresenter.callMovieList()
            requireContext().hideKeyboard(binding.editTextSearch)
        }
    }

    override fun listToAdapter(list: List<MovieResponse.Item>?) {
        adapter.submitList(list)
    }

    ...
    
    override fun onDestroy() {
        super.onDestroy()
        searchPresenter.dropView()
    }
}
```
- `initPresenter()` : **Initialize Presenter** in the Fragment, when Fragment is created
- `onClickSearch()` : When Search Button Click, Notify Presenter of the Event and Get data from Model
- `listToAdapter()` : **Data** from Model is passed **from Presenter to View**. and Visible in Recyclerview using adapter
