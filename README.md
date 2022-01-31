[[Android] 아키텍처 패턴 (Architecture Pattern), MVVM ? _ hyooosong velog](https://velog.io/@hyooosong/Android-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98-%ED%8C%A8%ED%84%B4-Architecture-Pattern-MVVM)

<br>


## ✨ Packaging
📂com.example.moviereview  
┣ 📂data  
┃ ┣ 📂local  
┃ ┣ 📂remote  
┣ 📂di  
┣ 📂ui  
┃ ┣ 📂main  
┃ ┣ 📂search  
┃ ┣ 📂review  
┗ 📂utils  
┗ 📂ext  

<br>

### 🌈 Model
`data class` and `data Processing`
- local package (Room)  
- remote package (Network)  
- ReviewModel in Room, MovieResponse in Network

### 🌈 View
`User Event` and `View Update`
- Activity
- Fragment

### 🌈 ViewModel
`Model for View` and `Presentation Logic`
- SeearchViewModel
- ReviewViewModel

### 🌈 Coroutine
- Use viewModelScope in ViewModel
- `viewModelScope.launch(Dispatchers.IO)`
- Use setValue in `withContext(Dispatchers.Main)`

### 🌈 DI
- Use Annotation `@HiltAndroidApp` in APP
- Use Annotation `@Inject contructor()` where to inject
  - Use `@HiltViewModel` in ViewModel
- Use Annotation `@Module & @InstallIn` in Module to inject
  - `SingletonComponent::class` for singleton
  - `ActivityRetainedComponent::class` for ViewModel
