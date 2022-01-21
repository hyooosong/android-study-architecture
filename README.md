[[Android] 아키텍처 패턴 (Architecture Pattern), MVC ? _ hyooosong velog](https://velog.io/@hyooosong/Android-Architecture-Pattern-MVC)

<br>


## ✨ Packaging
📂com.example.moviereview  
┣ 📂network  
┣ 📂room  
┣ 📂ui  
┃ ┣ 📂main  
┃ ┣ 📂search  
┃ ┣ 📂review  
┗ 📂utils  

<br>

### 🌈 Model
`data class` and `data Processing`
- Room
- Network
- ReviewModel in Room, MovieResponse in Network

### 🌈 View + Controller
`User Event` and `View Update`
- Activity
- Fragment
