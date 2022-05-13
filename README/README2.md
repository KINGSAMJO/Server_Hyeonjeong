# 필수 과제 1


> FollowerAdapter

```kotlin
override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {   
    val binding = ItemFollowerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)  
    return FollowerViewHolder(binding)  
}  
```
-> 뷰홀더를 만드는 코드 부모 context에 부착시키며 뷰홀더에 넘겨준다.

```kotlin
override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {  
    holder.onBind(followerList[position])    
}  
```
-> 현재 위치에 있는 값들을 뷰홀더에 넘겨줌

  ```kotlin
override fun getItemCount(): Int {  
    return followerList.size  
}  
```
-> 리사이클러뷰에서 구현할 리스트 개수를 넘겨주기  
  
  ```kotlin
class FollowerViewHolder(private val binding : ItemFollowerListBinding):RecyclerView.ViewHolder(binding.root) {  
    fun onBind(data: FollowerData){  
        binding.tvFollowerListName.text = data.followerName  
		binding.tvFollowerListIntro.text = data.followerIntro  
  
  }  
  
}
```
-> 해당 position에 있는 값들을 뷰홀더에 연결시켜준다.

> fragment 변경하기

```kotlin
private fun initTransaction(){
		val fragment1 = FollowerFragment()
		val fragment2 = RepositoryFragment()

		supportFragmentManager.beginTransaction().add(R.id.fcv_home,fragment1).commit()
		binding.btnFollower.setOnClickListener {
			supportFragmentManager.beginTransaction().replace(R.id.fcv_home,fragment1).commit()
		}
		binding.btnRepository.setOnClickListener {
			supportFragmentManager.beginTransaction().replace(R.id.fcv_home,fragment2).commit()
		}
		}
}
```
-> initTransaction을 통해 현재 선택한 프레그 먼트 교체시킴
이때 add로 먼저 선택할 것을 지정해주고 그다음에 변경 할 때는 replace로 해줘야함
이렇게 클릭이벤트를 걸어줘서 변경이 가능하도록 한다!

## 구현화면
<img width="276" alt="image" src="https://user-images.githubusercontent.com/81394850/167561388-ccdcd24f-3ed0-440f-8938-244695825100.png">

# 필수과제 1-2

```xml
<androidx.recyclerview.widget.RecyclerView

android:id="@+id/rv_follower"
android:layout_width="match_parent"
android:layout_height="wrap_content"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintTop_toTopOf="parent"
app:layoutManager="androidx.recyclerview.widget.GridLayoutManager"
app:spanCount="2"/>
```
리사이클러뷰 Grid Layout으로 변경시키기
`GridLayoutManager` 이용!
`spanCount` -> 아이템 2개씩 들어올 수 있도록 함

## 구현화면

<img width="291" alt="image" src="https://user-images.githubusercontent.com/81394850/167560637-463e7e12-c48d-47f9-9d87-c54d688b6fe2.png">

# 성장과제
```kotlin
override fun onBindViewHolder(holder: FollowerAdapter.FollowerViewHolder, position: Int) {

holder.onBind(followerList[position])
holder.itemView.setOnClickListener {
	val intent = Intent(mContext,DetailActivity::class.java)
	intent.putExtra("name", followerList[position].followerName)
	intent.putExtra("desc", followerList[position].followerIntro)
	mContext.startActivity(intent)
}
}
```
-> onBindViewHolder에 아이템 생겼을 시점에 클릭이벤트 연결하기!
-> 해당 뷰 위치에 대한 이름과 설명을 함께 putExtra로 보내주기
* 다른 방식으로 하는 것이 좋을 것 같아 그 부분을 공부해야 할 것 같다...


## 구현화면

<img width="285" alt="image" src="https://user-images.githubusercontent.com/81394850/167560792-69d456ec-39f6-4f0a-a45c-0e606c1d66fd.png">
-> 클릭시 이 화면으로 이동!