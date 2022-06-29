# Server_Hyeonjeong
무럭무럭 성장하는 Server 개발자입니다.

<details> 
 <summary>1차 세미나</summary> 
  <div markdown="1">
   # 필수 과제 1-1
> 로그인 페이지 만들기
> (SignInActivity)

<img width="397" alt="1" src="https://user-images.githubusercontent.com/81394850/162618531-506ff87d-1ee9-4193-903f-975cf141c1b0.png">


**1. 아이디, 비밀번호 모두 입력 되어있을 경우만 Activity 이동**
```kotlin
//로그인 버튼 클릭시  
binding.btnLogin.setOnClickListener {  
  if(etid.text.toString().isNotBlank() && etpw.text.toString().isNotBlank()){//값이 있는 경우  
  Toast.makeText(this,"로그인 성공",Toast.LENGTH_SHORT).show()  
        val intent = Intent(this,HomeActivity::class.java)  
        startActivity(intent)  
    }else {  
        Toast.makeText(this,"아이디/비밀번호를 확인해주세요",Toast.LENGTH_SHORT).show()  
    }  
}
```
➡️ 로그인 버튼 클릭시
`isNotBlank` : 비어있거나 공백이 아닌 경우 true 반환
- `Toast` 메시지를 통해 '로그인 성공' 출력하기
- `Intent` 객체를 통해 HomeActivity로 이동하기

<img width="422" alt="2" src="https://user-images.githubusercontent.com/81394850/162618590-bc3b874a-ca25-4e94-9d09-a59994c30f11.png">


**2. 비밀번호 입력 내용 가리기**
`android:inputType="textPassword"` 를 통해 입력한 비밀번호를 가리도록 설정하기

**3. 미리보기 글씨 설정**
`android:hint="비밀번호를 입력해주세요."` 로 입력 전 미리보기 내용을 설정해준다.

**4. 회원가입 버튼 클릭시 SignUpActivity로 이동**
```kotlin
//회원가입 버튼 클릭시  
binding.btnSignup.setOnClickListener {  
  val intent = Intent(this,SignUpActivity::class.java)  
  startActivity(intent)
}
```
<img width="430" alt="3" src="https://user-images.githubusercontent.com/81394850/162618624-f4b0083d-0b17-4f33-9382-b2ec82a444ad.png">


`Intent(context, 호출할 액티비티::class.java)` 
-> 회원가입 Activity 호출하여 이동하기

# 필수과제 1-2

> 회원가입 페이지 만들기
> (SignUpActivity)


**1. 모두 입력된 경우만 회원가입 완료 클릭시 Activity 이동**

```kotlin
private fun signup(){  
    if(binding.etSignupId.text.toString().isNotBlank() && binding.etSignupName.text.toString().isNotBlank() && binding.etSignupPw.text.toString().isNotEmpty()){  
        Toast.makeText(this,"회원가입성공", Toast.LENGTH_SHORT).show()
        finish()
}

override fun finish() {  
    super.finish()  
}
```

* ` isNotBlank` 속성을 통해 EditText에 값이 있는 경우만 회원가입 성공하도록 설정
* `finish()` 함수를 호출하여 현재 엑티비티를 종료하고 Intent하기 전에 있었던 Activity로 이동

> 모두 입력하지 않은 경우

<img width="413" alt="4" src="https://user-images.githubusercontent.com/81394850/162618715-8fd68f0b-1205-4685-8580-493d175bc8b0.png">

> 모두 입력한 경우

<img width="437" alt="5" src="https://user-images.githubusercontent.com/81394850/162618774-cfc97f49-01c9-4fe7-bdc8-e85aced10a25.png">

-> 회원가입 성공

# 필수과제 1-3

> 자기소개 페이지 만들기
> (HomeActivity)

constraint 설정 후 view간의 위치 관계를 통해 자기소개 페이지 설정


# 성장과제 2-1
> 화면이동 + @

1. 회원가입 성공 후 회원가입 시 입력했던 아이디와 비밀번호 입력되어 있어야함
> SignInActivity.kt
```kotlin
//회원가입 버튼 클릭시  
binding.btnSignup.setOnClickListener {  
  val intent = Intent(this,SignUpActivity::class.java)  
    getSignUpActivityResult.launch(intent) //startActivity 대신 사용해서 값 받아올 수 있도록 함  
}
```
* SignActivity에서 값을 받아오기 위해 getSignUpActivityResult 변수를 시작하여 Intent 진행한다.

```kotlin
//회원가입 후 ID, PW 가져오기  
getSignUpActivityResult = registerForActivityResult(  
    ActivityResultContracts.StartActivityForResult()) {  
  result ->  
  //회원가입에서 돌아올 때 결과 값 받아옴  
  if(result.resultCode == RESULT_OK){  
            var signupId = result.data?.getStringExtra("id")  
            var signupPw = result.data?.getStringExtra("pw")  
            etid.setText(signupId)  
            etpw.setText(signupPw)  
        }  
    }
```
2. `private lateinit var getSignUpActivityResult : ActivityResultLauncher<Intent>` 
-> registerForActivityResult를 사용할 변수를 선언한다.
3. `if(result.resultCode == RESULT_OK)` RESULT_OK 를 통해 결과코드가 참일 경우 회원가입에서 돌아올 때 결과 값을 받아온다.
4. `result.data?.getStringExtra("id")` id라는 key값을 담아온 데이터를 저장하여 ID EditText 자리에 넣어준다.(비밀번호도 동일하게 진행)
5. `etid.setText(signupId)` 받아온 id값을 setText로 텍스트를 설정해준다.


> SignUpActivity.kt
```kotlin
//로그인 페이지로 이동할 때 입력 값을 보내도록 하는 코드  
val intent = Intent(this, SignInActivity::class.java).apply{  
  putExtra("id",signupId)  
    putExtra("pw",signupPw)  
}  
  
setResult(RESULT_OK,intent)  
if (!isFinishing) {  
    finish()  
}
```
* `putExtra`를 통해  key값과 함께 입력한 값을 저장해준다.
* 값 입력 후 RESULT_OK를 통해 받은 입력 값을 전달하여 사용할 수 있도록 한다.

<img width="437" alt="6" src="https://user-images.githubusercontent.com/81394850/162618897-34dcc36f-e0ed-412e-bd54-e66bcf910cc2.png">


# 성장과제 2-2

**1. ScrollView 설정**
```xml
<ScrollView  
	  android:layout_width="match_parent"  
	  android:layout_height="0dp"  
	  app:layout_constraintTop_toTopOf="parent"  
	  app:layout_constraintBottom_toBottomOf="parent">  
	 <androidx.constraintlayout.widget.ConstraintLayout  android:layout_width="match_parent"  
	  android:layout_height="wrap_content"  
	  android:layout_marginBottom="30dp"  
	  app:layout_constraintTop_toTopOf="parent">
      </androidx.constraintlayout.widget.ConstraintLayout>  
  
</ScrollView>
  ```
* ScrollView는 imageview나 TextView와 같은 View들을 바로 포함할 수 없다. 또한 한개의 뷰만 포함 가능하다. 그래서 layout을 만들고 그 내부에 다른 view들을 포함해야한다.
* ConstraintLayout을 내부에 만들고 그 안에 다른 뷰들을 포함하여 만든다.

<img width="401" alt="7" src="https://user-images.githubusercontent.com/81394850/162618912-60f04285-3618-44d4-aa8f-624bd592cea1.png">
<img width="421" alt="8" src="https://user-images.githubusercontent.com/81394850/162618937-bf8c7f7c-6051-4fbf-a367-389f5244c4bf.png">

-> 내용이 많을 경우, 스크롤뷰를 통해 내릴 수 있도록 함

<br>

**2. 사진 비율 1:1로 설정하기**

`app:layout_constraintDimensionRatio="1:1"` -> 가로, 세로 비율을 1:1로 설정하기 
 
`app:layout_constraintWidth_percent="0.4"` -> Width에 40% 크기로 image 크기를 설정한다. 이때 가로, 세로 비율이 같으므로 Length 또한 같은 크기로 설정된다.


  </div> 
</details>

<details>  
<summary>2차 세미나</summary>  
<div markdown="1">
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
</div>
</details>

<details>  
<summary>3차 세미나</summary>  
<div markdown="1">
 # 필수 과제 1
> 폰트 적용하기

1. 사용할 font xml 만들기	
```xml
<font-family xmlns:android="http://schemas.android.com/apk/res/android"  
  xmlns:app="http://schemas.android.com/apk/res-auto">  
 <font  android:font="@font/noto_sans_kr_regular"  
  android:fontStyle="normal"  
  android:fontWeight="400"/>  
  
 <font  android:font="@font/noto_sans_kr_bold"  
  android:fontStyle="normal"  
  android:fontWeight="700"/>  
  
</font-family>
```
-> fontweight에 따라 다르게 설정됨
2. values폴더에서 fontstyle.xml 만들기
```xml
<resources>  
 <style name="noto_sans_textView" parent="@android:style/Widget.DeviceDefault.TextView">  
 <item name="android:fontFamily">@font/font_noto_sans</item>  
 </style>  
 <style name="noto_sans_button" parent="@android:style/Widget.DeviceDefault.Button.Borderless">  
 <item name="android:fontFamily">@font/font_noto_sans</item>  
 </style>  
 <style name="noto_sans_editText" parent="@android:style/Widget.DeviceDefault.EditText">  
 <item name="android:fontFamily">@font/font_noto_sans</item>  
 </style>  
</resources>
```
-> textview, button, editText 별로 폰트 적용시킴
3. style.xml에서 custom font-family 적용시킴
```xml
<resources>  
  <!--custom font-family 적용-->  
	  <style name="AppStyle" parent="Theme.AppCompat.Light.NoActionBar">  
	 <item name="android:textViewStyle">@style/noto_sans_textView</item>  
	 <item name="android:buttonStyle">@style/noto_sans_button</item>  
	 item name="android:editTextStyle">@style/noto_sans_editText</item>
	  
 </style>
 </resources>
 ```
-> 폰트 자동 적용시킨다.

## HomeActivity -> ProfileFragment로 만들기

-> HomeActivity에서 프레그먼트 띄우고 BottomNavigation 만들어서 fragment 교체시키기

1. ViewPager2 사용하기
2. bottomnavigationView 사용하기
```xml
<androidx.viewpager2.widget.ViewPager2  
  android:id="@+id/vp_home"  
  android:layout_width="match_parent"  
  android:layout_height="match_parent"/>  
  
 <com.google.android.material.bottomnavigation.BottomNavigationView  android:id="@+id/bnv_home"  
  android:layout_width="match_parent"  
  android:layout_height="wrap_content"  
  android:background="#FFFFFF"  
  app:itemIconTint="@color/selector_bottom_navi"  
  app:itemRippleColor="#6424D5"  
  app:itemTextColor="@color/selector_bottom_navi"  
  app:layout_constraintBottom_toBottomOf="parent"  
  app:menu="@menu/menu_home"/>
  ```
  * bottomNavitgation menu 연결시킨 후 클릭한 `@menu/menu_home`에 따라 ViewPager2에 나오는 화면 다르게 설정하기

> HomeActivity.kt에서 연결할 프레그먼트 어뎁터로 연결하기
```kotlin
private fun initAdapter(){  
    val fragmentList = listOf(ProfileFragment(), HomeFragment(), CameraFragment())  
    homeViewPagerAdapter = HomeViewPagerAdapter(this)  
    homeViewPagerAdapter.fragments.addAll(fragmentList)  
  
    binding.vpHome.adapter = homeViewPagerAdapter  
}
```
-> 세개의 프레그먼트 연결하여 어뎁터에서 리스트 받아 보여줄 fragment 설정하기
```kotlin
class HomeViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {  
    val fragments = mutableListOf<Fragment>()  
    override fun getItemCount(): Int = fragments.size  
  
  override fun createFragment(position: Int): Fragment = fragments[position]  
  
}
```
*  엑티비티에서 작동하는것이라서 fragmentActivity에서 만든다.
* `createFragment(position: Int): Fragment = fragments[position]  ` 를 통해 현재 position에 있는 fragment를 반환해준다.

> BottomNavigationView 사용해서 연결시키기
-> initBottomNavi() 불러오기

```kotlin
private fun initBottomNavi(){  
    binding.vpHome.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){  
        override fun onPageSelected(position: Int) {  
            binding.bnvHome.menu.getItem(position).isChecked = true  
  }  
    })  
    binding.bnvHome.setOnItemSelectedListener {  
  when(it.itemId){  
            R.id.menu_profile -> {  
                binding.vpHome.currentItem = FIRST_FRAGMENT  
  return@setOnItemSelectedListener true  
  }  
            R.id.menu_home -> {  
                binding.vpHome.currentItem = SECOND_FRAGMENT  
  return@setOnItemSelectedListener true  
  }  
            else -> {  
                binding.vpHome.currentItem = THIRD_FRAGMENT  
  return@setOnItemSelectedListener true  
  }  
        }  
    }  
}
```
* 현재 선택한 메뉴일 경우 해당하는 위치의 fragment 보여주도록 설정하기

> 클릭이벤트에 따라 선택되는 bottomNavi 다르게 설정하기

```kotlin
private fun initTransaction(){  
    val followerFragment = FollowerFragment()  
    val repositoryFragment = RepositoryFragment()  
  
    childFragmentManager.beginTransaction().add(R.id.fcv_profile,followerFragment).commit()  
    binding.btnFollower.setOnClickListener{  
  binding.btnFollower.isSelected = true  
  binding.btnRepository.isSelected = false  
  childFragmentManager.beginTransaction().replace(R.id.fcv_profile,repositoryFragment).commit()  
    }  
  binding.btnRepository.setOnClickListener {  
  binding.btnFollower.isSelected = false  
  binding.btnRepository.isSelected = true  
  childFragmentManager.beginTransaction().replace(R.id.fcv_profile,followerFragment).commit()  
    }  
  
}
```
➡️원래 supportFragmentManager를 통해 activity에서 프레그먼트들을 연결했었다. 여기서는 fragment 내부에서 다른 fragment들을 띄어야하기 때문에 `childFragmentManager`를 사용한다. 그러면 부모 프레그먼트가 자식 프레그먼트들을 관리할 수 있도록 한다.

➡️이런식으로 bottomNavi도 어뎁터의 기능을 이용하여 페이지를 엑티비티에 보여주는 거임

⭐fragmentManager에 supportFragmentManager만 쓰다가 childFragmentManager, parentFragmentManager도 사용할 수 있다는 것을 깨달음. fragment의 현재 상태에 따라 적적하게 사용해야겠다.

## 구현화면

![frag](https://user-images.githubusercontent.com/81394850/167087323-4c799d5d-3900-4152-8d5c-e54dd3f2e8ff.gif)


## Button selector 활용하기

1. unclicked일때와 clicked일때의 `shape` 를 만들기
2. selector_btn에서 클릭 여부에 따라 다른 버튼 볼 수 있도록 구현
```xml
<selector xmlns:android="http://schemas.android.com/apk/res/android">  
 <item android:drawable="@drawable/unclicked_btn" android:state_selected="false"/>  
 <item android:drawable="@drawable/clicked_btn" android:state_selected="true"/>  
</selector>
```
-> state_selected 여부에 따라 버튼 모양 다르게 설정하기
3. 버튼의 배경색상 선택
`android:background="@drawable/selector_btn"` -> 위에서 만든 파일로 버튼 색상 적용하기

## 이미지 원형으로 설정

`<de.hdodenhof.circleimageview.CircleImageView>` 를 통해 이미지 원형으로 설정하기

## HomeFragment 완성시키기
-> tabLayout + ViewPager2 사용하기

1. TabLayout과 ViewPager2 설정하기 -> TabLayout에서 선택한거에 따라 ViewPager2에 나오는 화면 다르게 설정할거임
2. 마찬가지로 ViewPager2에 띄울 자식 프레그먼트를 띄우기 위해 어뎁터를 사용해야함
3. 사용할 프레그먼트를 어뎁터와 연결시키기
```kotlin
private fun initAdapter(){  
    var fragmentList = listOf(FollowerTabFragment(), FollowingTabFragment())  
  
    followTabViewpagerAdapter = FollowTabViewPagerAdapter(this)  
    followTabViewpagerAdapter.fragments.addAll(fragmentList)  
  
    binding.vpFragHome.adapter = followTabViewpagerAdapter  
}
```
4. TabLayout에서 사용할 라벨 설정하기
```kotlin
private fun initAdapter(){  
    var fragmentList = listOf(FollowerTabFragment(), FollowingTabFragment())  
  
    followTabViewpagerAdapter = FollowTabViewPagerAdapter(this)  
    followTabViewpagerAdapter.fragments.addAll(fragmentList)  
  
    binding.vpFragHome.adapter = followTabViewpagerAdapter  
}
```
*  어뎁터에 사용할 프레그먼트를 리스트형식으로 보낸다. 
* 보여줄 화면에 어뎁터 설정하기
5. ViewPager2와 TabLayout 연동시키기
```kotlin
private fun initTabLayout(){  
    val tabLabel = listOf("Following", "Follower")  
  
    TabLayoutMediator(binding.tlFragHome, binding.vpFragHome) { tab, position ->  
  tab.text = tabLabel[position] //동적으로 Tablayout 설정  
  }.attach()  
}
```
-> 현재 tab과 positio에 따라 라벨 text 설정하여 붙이기 !

6. custom TabLayout 만들기
* style.xml에 원하는 텝바를 만들기
```xml
<style name ="tab_text" parent="TextAppearance.Design.Tab">  
 <item name="android:textSize">16sp</item>  
 <item name="android:fontFamily">@font/font_noto_sans</item>  
 <item name="android:textStyle">normal</item>  
</style>
```
-> textsize와 폰트 설정함
`app:tabTextAppearance="@style/tab_text"` 사용해서 내가 설정한 스타일로 만들기

* 텝바 선택시와 선택되지 않았을 때의 color를 다르게 설정하기
`app:tabIndicatorColor="@color/selector_bottom_navi"  
app:tabSelectedTextColor="@color/selector_bottom_navi"`
bottom_navi와 같은 방식으로 설정했음 

## 구현화면



# 2. 성장과제
> ViewPager2 중첩 스크롤 문제 해결하기
❗현재 엑티비티에서 bottomNavi에 의해 스크롤되고, homeFragment에서 tabLayout에 의해 변경되는 스크롤 뷰의 방향이 같다. TabLayout의 스크롤을 정상적으로 작동할 수 있도록 해야함

1. NestedScrollableHost라는 클래스를 생성해서 중첩을 해결하는 코드를 구글문서에서 찾아서 넣기
[custom wrapper layout](https://github.com/android/views-widgets-samples/blob/master/ViewPager2/app/src/main/java/androidx/viewpager2/integration/testapp/NestedScrollableHost.kt). 에서 제공하는 깃허브 파일을 넣기 !

중첩을 허용하기 위해 ViewPager2 객체의 requestDisallowInterceptTouchEvent()를 사용해야한다.
2. ViewPager2를 위에서 만든 `com.example.seminar_task1.util.NestedScrollableHost`로 감싸주기
-> 그러면 중첩 스크롤 문제가 해결된다.

## 구현화면
![home](https://user-images.githubusercontent.com/81394850/167087476-4932a67a-c2a1-4453-a2b5-0901c4758765.gif)


# 3. 도전과제
> 갤러리에 접근하여 이미지(Uri)를 Glide를 사용해서 화면에 띄우기
1. 버튼을 클릭하면 sopt이미지를 바꾸도록 설정해야함
<img width="300" alt="image" src="https://user-images.githubusercontent.com/81394850/167083540-038fd182-b641-4dd2-aa7d-e4404eddd110.png">

2. requestPermissionLauncher 사용하기
```kotlin
private val requestPermissionLauncher =  
    registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->  
  if (isGranted) {  
            navigateGallery()  
        } else {  
            Toast.makeText(requireContext(),"갤러리 접근 실패",Toast.LENGTH_SHORT).show()  
        }  
    }
```
-> 접근권한이 부여됐을 때 갤러리에 접근할 수 있도록 설정한다.

3. 갤러리 접근하기
    
  ```kotlin
  private fun navigateGallery() {  
    val photoIntent = Intent(Intent.ACTION_PICK) //open the album  
  photoIntent.type = "image/*"  
  openGallery.launch(photoIntent)  
}
```
-> `Intent.ACTION_PICK` 인 경우 앨범에 접근할 수 있는 Intent 객체를 날리고 type 설정 후 갤러리 열기

> Glide를 통해 이미지 변경시켜주기

* 첨부하기 버튼 클릭시 이미지를 변경시킬 거임

```kotlin
private val openGallery : ActivityResultLauncher<Intent> = registerForActivityResult(  
    ActivityResultContracts.StartActivityForResult()){ result ->  
  if(result.resultCode == RESULT_OK && result.data != null){  
        photoUri = result.data?.data  
  Glide.with(this)  
            .load(photoUri)  
            .into(binding.ivCamera)  
    }else if(result.resultCode == RESULT_CANCELED){  
        Toast.makeText(requireContext(),"사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()  
    }  
}
```
-> resultCode == RESULT_OK이고 가져온 값이 null이 아닌 경우 선택한 이미지의 uri를 Glide를 통해 해당 imageView 위치에 띄어준다.

> 버튼 클릭했을 때 작동하는 코드
```kotlin
private fun changeProfileImage() {  
    //버튼 클릭시 갤러리 접근하여 이미지 uri 전달  
  binding.btCamera.setOnClickListener {  
  when {  
            ContextCompat.checkSelfPermission(  
                requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE  
  ) == PackageManager.PERMISSION_GRANTED -> {  
                navigateGallery()  
            }  
            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {  
                showInContextUI()  
            }  
            else -> {  
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)  
            }  
        }  
    }  
}
```
-> 버튼 클릭헀을 때 발생 -> `navigateGallery()` 접근할 수 있도록 함


* 권한 부여받고 저장소 읽게 되면 `navigateGallery()` 를 불러와서 갤러리 열게됨

⭐조장님의 코드를 참고하여 갤러리 접근 관련 코드가 완벽 이해는 아니었지만 직접 코드로 쳐보고 수정하다 보니 흐름은 이해가 간 것 같다.

## 구현화면

![camera](https://user-images.githubusercontent.com/81394850/167087081-76f9de39-f3d4-4517-b4d6-dfa914e6bbd1.gif)
</div>
</details>

<details>  
<summary>4차 세미나</summary>  
<div markdown="1">
 # 4차 세미나 과제

# 필수 과제

## 로그인/회원가입 API 연동

1. request/response 객체 생성
```kotlin
data class RequestSignIn(  
    @SerializedName("email")  
    val id:String,  
 val password:String  
)
```
로그인시 request 데이터
```kotlin
data class RequestSignUp(  
    val name : String,  
  @SerializedName("email")  
    val id : String,  
 val password : String  
)
````
->  회원가입시 request 데이터

```kotlin
  
data class ResponseSignIn(  
    val status:Int,  
 val message :String,  
 val data : Data  
){  
    data class Data(  
        val name: String,  
 val email: String  
    )  
}
```
->  로그인시 response 데이터

```kotlin
data class ResponseSignUp(  
    val status : Int,  
 val message : String,  
 val data : Data  
){  
    data class Data(  
        val id : Int  
    )  
}
```
-> 회원가입시 response 데이터

이 데이터타입들을 json 데이터 타입과 일치시켜서 서버로 보낼 데이터 타입과 서버로부터 받아올 데이터 타입으로 일치시켜야한다.

2. Retrofit interface 설계
```kotlin
interface SoptService {  
    @POST("auth/signin")  
    fun postLogin(  
        @Body body: RequestSignIn  
    ) : Call<ResponseSignIn>  
  
    @POST("auth/signup")  
    fun postSignUp(  
        @Body body: RequestSignUp  
    ) : Call<ResponseSignUp>  
  
}
```
-> post로 데이터 전송하고 api 주소를 설정하기
아까 선언한 데이터 클래스를 인자값으로 받고 리턴값으로 반환해줄거임

3. Retrofit 구현체 생성
```kotlin
object ServiceCreator {  
    private const val BASE_URL = "http://13.124.62.236/"  
  
  private val retrofit : Retrofit = Retrofit.Builder()  
        .baseUrl(BASE_URL)  
        .addConverterFactory(GsonConverterFactory.create())  
        .build()  
  
    val soptService: SoptService = retrofit.create(SoptService::class.java)  
}
```
-> 기본적인 url 설정하고 retrofit 인터페이스의 실제 구현객체임

3. 로그인 서버 통신

> SignInActivity
```kotlin
private fun signIn(){  
    binding.btnLogin.setOnClickListener {  
  if(etid.text.toString().isNotBlank() && etpw.text.toString().isNotBlank()){//값이 있는 경우  
  val requestSignIn = RequestSignIn(  
                id= binding.etId.text.toString(),  
  password = binding.etPw.text.toString()  
            )  
            val call : Call<ResponseSignIn> = ServiceCreator.soptService.postLogin(requestSignIn)  
  
            call.enqueue(object : Callback<ResponseSignIn> {  
                override fun onResponse(  
                    call : Call<ResponseSignIn>,  
  response: Response<ResponseSignIn>  
                ){  
                    if(response.isSuccessful){  
                        val data = response.body()?.data  
  Toast.makeText(this@SignInActivity, "${data?.email}님 반갑습니다.", Toast.LENGTH_SHORT).show()  
                        startActivity(Intent(this@SignInActivity, HomeActivity::class.java))  
  
                    }else Toast.makeText(this@SignInActivity,"로그인에 실패했습니다.",Toast.LENGTH_SHORT).show()  
                }  
  
                override fun onFailure(call: Call<ResponseSignIn>, t: Throwable) {  
                    Log.e("NetworkTest,","error:$t")  
                }  
            })  
        }else {  
            Toast.makeText(this,"아이디/비밀번호를 확인해주세요",Toast.LENGTH_SHORT).show()  
        }  
  
  
    }  
}
```
-> 입력한 값이 있는 경우 :  call 객체에 enqueue를 호출하여 비동기적으로 서버를 요청한다. 성공하면 Toast 메시지 출력하고 HomeAcitivy로 Intent 시키기
-> 서버 통신 실패시 로그인 실패 Toast 메시지 

> SignUpActivity
```kotlin
private fun signUp(){  
    binding.btnSignupEnd.setOnClickListener{  
  if(binding.etSignupId.text.toString().isNotBlank() && binding.etSignupName.text.toString().isNotBlank() && binding.etSignupPw.text.toString().isNotEmpty()){  
            val requestSignUp = RequestSignUp(  
                name = binding.etSignupName.text.toString(),  
  id = binding.etSignupId.text.toString(),  
  password = binding.etSignupPw.text.toString()  
            )  
            val call : Call<ResponseSignUp> = ServiceCreator.soptService.postSignUp(requestSignUp)  
  
            call.enqueue(object : Callback<ResponseSignUp> {  
                override fun onResponse(  
                    call: Call<ResponseSignUp>,  
  response: Response<ResponseSignUp>  
                ) {  
                    if (response.isSuccessful) {  
                        val data = response.body()  
                        Toast.makeText(this@SignUpActivity, "${data?.message}!!!", Toast.LENGTH_SHORT).show()  
                        //로그인 페이지로 이동할 때 입력 값을 보내도록 하는 코드  
  val intent = Intent(this@SignUpActivity, SignInActivity::class.java).apply{  
  putExtra("id",requestSignUp.id)  
                            putExtra("pw",requestSignUp.password)  
                        }  
  setResult(RESULT_OK,intent)  
                        if (!isFinishing) {  
                            finish()  
                        }  
                    } else Toast.makeText(this@SignUpActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()  
                }  
  
                override fun onFailure(call: Call<ResponseSignUp>, t: Throwable) {  
                    Log.e("Network", "error :$t")  
                }  
            })  
        }else{  
            Toast.makeText(this,"입력되지 않은 정보가 있습니다",Toast.LENGTH_SHORT).show()  
        }  
    }  
  
}
```
-> 입력값 있는 경우 binding객체 할당해서 값 저장
-> call 객체에 enqueue 호출하여 비동기적으로  처리한다.
-> 성공할 경우 현재 입력한 id, password 값을 putExtra로 보내서 로그인 화면에서 입력될 수 있도록 한다.

이렇게 회원가입을 하면 이 id, password로 로그인이 가능함

# 구현화면

## 회원가입 

![device-2022-05-13-165411](https://user-images.githubusercontent.com/81394850/168237857-ab9560b8-2b41-4e86-94f6-f38f9ff99c20.gif)

## 로그인
![login](https://user-images.githubusercontent.com/81394850/168238041-65e06def-1669-4e10-aaaf-f1ff18676c0f.gif)
</div>
</details>

<details>  
<summary>7차 세미나</summary>  
<div markdown="1">
 
# 필수과제

> SharedPreferences를 사용해서 자동로그인 / 자동로그인 해제 구현하기

```kotlin
object SOPTSharedPreferences {  
    private const val STORAGE_KEY = "USER_AUTO"  
  private const val AUTO_LOGIN = "AUTO_LOGIN"  
  private lateinit var preferences: SharedPreferences  
  
    fun init(context: Context) {  
        preferences = context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE)  
    }  
  
    fun getAutoLogin(): Boolean {  
        return preferences.getBoolean(AUTO_LOGIN, false)  
    }  
  
    fun setAutoLogin(value : Boolean){  
        preferences.edit()  
            .putBoolean(AUTO_LOGIN, value)  
            .apply()  
    }  
  
    fun setLogout(context: Context){  
        preferences = context.getSharedPreferences(STORAGE_KEY, Context.MODE_PRIVATE)  
        preferences.edit()  
            .remove(AUTO_LOGIN)  
            .clear()  
            .apply()  
    }  
}
```
-> sharedPreferences 사용해서 key값에 대한 value값을 내장DB에 저장한다.
그러면 그 key에 대한 값을 꺼내올 수 있음

## 자동로그인 설정

> 버튼 설정
```xml
<selector xmlns:android="http://schemas.android.com/apk/res/android">  
 <item android:drawable="@drawable/ic_baseline_check_circle_outline_24" android:state_selected="false"/>  
 <item android:drawable="@drawable/ic_baseline_check_circle_24" android:state_selected="true"/>  
</selector>
```
자동로그인을 클릭했을 때, 안했을 때의 버튼 이미지를 다르게 설정하기

> 버튼 클릭했는지에 대한 Boolean값 전달
```kotlin
private fun initClickEvent(){  
    binding.btnCheckbox.setOnClickListener {  
  binding.btnCheckbox.isSelected = !binding.btnCheckbox.isSelected  
  SOPTSharedPreferences.setAutoLogin(binding.btnCheckbox.isSelected)  
    }  
}
```
-> 클릭된 경우 현재 isSelected 값에 반대로 저장하여 SOPTSharedPreferences에 전달한다.

> 자동로그인
```kotlin
    if(SOPTSharedPreferences.getAutoLogin()){  
        Toast.makeText(this,"자동로그인 되었습니다",Toast.LENGTH_SHORT).show()  
        startActivity(Intent(this@SignInActivity, HomeActivity::class.java))  
        finish()  
    }  
  
}
```
위에서 true 를 얻은 경우는 자동로그인으로 설정됨

## 자동로그인 해제
```kotlin
private fun isAutoLogOut(){  
    binding.clAutoLogout.setOnClickListener{  
  SOPTSharedPreferences.setAutoLogin(false)  
        SOPTSharedPreferences.setLogout(this)  
        Toast.makeText(this,"자동로그인 되었습니다",Toast.LENGTH_SHORT).show()  
        startActivity(Intent(this@SettingActivity, SignInActivity::class.java))  
        finish()  
    }  
}
```
-> 버튼 클릭시 자동로그인 false상태로 바꾸고 현재 로그인 된 부분 제거하기



## 결과물

<img width="201" alt="image" src="https://user-images.githubusercontent.com/81394850/172747011-1f03b946-486b-45f1-a2e5-922e3ecc3508.png">

-> 자동 로그인 버튼 생성하여 클릭시 자동 로그인 설정함

<img width="200" alt="image" src="https://user-images.githubusercontent.com/81394850/172748231-54e2bc17-5f3a-4332-8507-c48b42705418.png">

-> 자동로그인 설정하기

<img width="200" alt="image" src="https://user-images.githubusercontent.com/81394850/172747576-05ce8265-10bf-44f2-b490-148a43259414.png">

-> 자동로그인 해제 버튼 클릭시 로그아웃 됨

<img width="200" alt="image" src="https://user-images.githubusercontent.com/81394850/172748454-217a81a6-272c-4164-a317-9518e87a170a.png">

-> 자동로그인 해제하기

# 성장과제

> 온보딩 화면 만들기

```xml
<navigation xmlns:android="http://schemas.android.com/apk/res/android"  
  xmlns:app="http://schemas.android.com/apk/res-auto"  
  xmlns:tools="http://schemas.android.com/tools"  
  android:id="@+id/nav_host_fragment"  
  app:startDestination="@id/onBoardingFragment1">  
  
 <fragment  android:id="@+id/onBoardingFragment1"  
  android:name="com.example.seminar_task1.OnBoardingFragment1"  
  android:label="fragment_on_boarding1"  
  tools:layout="@layout/fragment_on_boarding1" >  
 <action  android:id="@+id/action_onBoardingFragment1_to_onBoardingFragment2"  
  app:destination="@id/onBoardingFragment2" />  
 </fragment>
  <fragment  android:id="@+id/onBoardingFragment2"  
  android:name="com.example.seminar_task1.OnBoardingFragment2"  
  android:label="fragment_on_boarding2"  
  tools:layout="@layout/fragment_on_boarding2" >  
 <action  android:id="@+id/action_onBoardingFragment2_to_onBoardingFragment3"  
  app:destination="@id/onBoardingFragment3" />  
 </fragment> 
 <fragment  android:id="@+id/onBoardingFragment3"  
  android:name="com.example.seminar_task1.OnBoardingFragment3"  
  android:label="fragment_on_boarding3"  
  tools:layout="@layout/fragment_on_boarding3" />  
</navigation>
```
-> 온보딩 화면에서 사용할 fragment와 이동 action 설정하기
action을 통해 어떤 화면으로 넘어갈지 설정가능

```kotlin
binding.btnNext.setOnClickListener {  
  findNavController().navigate(R.id.action_onBoardingFragment1_to_onBoardingFragment2)  
}
```
-> 버튼 클릭시 액션 불러와서 fragment1 -> fragment2로 이동할 수 있도록 설정한다.

```kotlin
binding.btnNext.setOnClickListener {  
  startActivity(Intent(requireContext(), SignInActivity::class.java))  
    activity?.finish()  
  
}
```
-> 마지막 시작하기에서 버튼 클릭시 로그인 엑티비티로 넘어가고 현재 엑티비티는 finish 시켜줌

# 결과물

<img width="200" alt="image" src="https://user-images.githubusercontent.com/81394850/172749112-b04ed894-823c-44ee-ab7e-032be8e36c48.png">

-> 버튼 클릭시 action 실행됨

<img width="200" alt="image" src="https://user-images.githubusercontent.com/81394850/172749133-3de0dde9-cf8e-428b-84b3-b16ce02057eb.png">

-> 버튼 클릭시 action 실행됨

<img width="200" alt="image" src="https://user-images.githubusercontent.com/81394850/172749152-95590290-9564-4ff3-9fba-05dce172296f.png">

-> 시작하기 클릭시 로그인 페이지로 이동

<img width="200" alt="image" src="https://user-images.githubusercontent.com/81394850/172749173-7b901c6b-6cf7-4e22-b754-790150e992db.png">

-> 로그인 화면 


# 도전 과제
> Room을 사용하여 자동로그인 로직 만들기

### 1. DAO 객체 만들기

```kotlin
@Dao

interface SignInDao {
@Insert
suspend fun insert(isLogin: LoginData)

@Delete
suspend fun delete(isLogin: LoginData)

@Query("DELETE FROM LoginData WHERE id = :user")
suspend fun deleteIsLogin(user: String)

@Query("SELECT * FROM LoginData")
suspend fun getAll(): List<LoginData>

@Query("SELECT * FROM LoginData WHERE id = :user")
suspend fun findIsLogin(user: String): LoginData

@Query("UPDATE LoginData SET isAutoLogin=:isLogin WHERE id = :user")
suspend fun update(user: String, isLogin: Boolean)
}
```
1. Insert : isLogin으로 받은 값을 삽입해준다.
2. Delete : isLogin으로 받은 값을 찾아서 삭제해준다.
3. deleteIsLogin : 현재 받아온 user가 id와 같은 경우 그 데이터를 삭제해준다.
4. getAll : 전체 데이터를 리스트 형식으로 불러와준다.
5. findIsLogin : 해당하는 아이디를 선택해준다.
6. update : 해당하는 아이디를 찾고 그 값을 업데이트 하고 싶은 값으로 세팅해준다.

코루틴을 사용하여 DB 처리시 IO 영역에서 처리할 수 있도록 설정하였다.

### 2. SignInDataBase 생성
```kotlin
@Database(entities = [LoginData::class], version = 5)  
abstract class SignInDatabase : RoomDatabase() {  
  abstract fun signInDao(): SignInDao  
  
  companion object {  
  private var instance: SignInDatabase? = null  
  
 fun getInstance(context: Context): SignInDatabase? {  
  if (instance == null) {  
	  synchronized(SignInDatabase::class) {  
		  instance = Room.databaseBuilder(  
			  context.applicationContext, SignInDatabase::class.java,  
              "signIn-database" //db가 생성될 떄 사용될 이름  
  )  
			  .addMigrations(MIGRATION_3_4)  
			  .fallbackToDestructiveMigration()  
			  .build()  
		  }  
		 }  
		  return instance  
	  }  
  }  
  
}  
val MIGRATION_3_4 = object : Migration(3, 4) {  
  override fun migrate(database: SupportSQLiteDatabase) {  
 }
}
 ```

-> 위에 SignInDao를 사용하여 데이터베이스를 처리해준다.
* MIGRATION을 설정하지 않으면 오류가 발생하여 선언해서 추가해두었다.

### 자동로그인 하기 !

> SignInActivity.kt
```kotlin
private fun initLogin() {
CoroutineScope(Dispatchers.IO).launch {

		val isAuto =

			withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {

			db.signInDao().findIsLogin("UserLogin")

}
		if(isAuto===null){

			db.signInDao().insert(LoginData("UserLogin", false))
		}
	}	
}
```
*  자동로그인 버튼 클릭전 초기화 작업을 진행한다.
* 우선 findIsLogin을 사용하여 UserLgoin가 id인 데이터를 찾아준다.
* 초기화 안된 경우 null일 것이고, UserLogin 이라는 아이디와 로그인 상태는  false인 데이터를 insert해준다.

> 클릭된경우
```kotlin
private fun initClickEvent() {

binding.btnCheckbox.setOnClickListener {
		binding.btnCheckbox.isSelected = !binding.btnCheckbox.isSelected
		CoroutineScope(Dispatchers.IO).launch {
			db.signInDao().update("UserLogin", binding.btnCheckbox.isSelected)
		}
	}
}
```

* 자동로그인 checkbox가 클릭된 경우 현재 selected 상태를 반대로 바꾼다.
* CoroutineScope - 백그라운드 영역에서 DB를 다루어줘야한다.
* 현재 UserLogin 아이디가 초기화 때 생성이 되어있을 것이고, 현재 isSelected 형태로 update해준다.

> 자동로그인
```kotlin
	private fun isAutoLogin() {

	CoroutineScope(Dispatchers.Main).launch {
		val isAuto =
			withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
				db.signInDao().findIsLogin("UserLogin")
	}
	if (isAuto.isAutoLogin) {
		Toast.makeText(applicationContext, "자동로그인 되었습니다", Toast.LENGTH_SHORT).show()
		startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
		finish()
	}
   }
}
```
* UI 그리기 위해 Main thread에서 작업을 진행한다.
* DB를 불러오기 위한 작업은 IO thread에서 진행하며 withContext를 사용하여 실행한다.
* 현재 UserLgoin에 대한 isAutoLgoin 상태가 true인 경우 자동로그인이 성공하며 현재 로그인 화면이 finish된다.

> 자동로그인 해제하기
```kotlin
private fun isAutoLogOut() {  
	  binding.btnRight.setOnClickListener {  
		  CoroutineScope(Dispatchers.IO).launch {  
			  kotlin.runCatching {  
			  db.signInDao().deleteIsLogin("UserLogin")  
		  }.onSuccess {  
			  withContext(Dispatchers.Main) {  
			  Toast.makeText(this@SettingActivity, "자동로그인 해제 되었습니다", Toast.LENGTH_SHORT)  
			  .show()  
			  startActivity(Intent(this@SettingActivity, SignInActivity::class.java))  
			  finish()  
	 }  
		 }.onFailure {  
			  withContext(Dispatchers.Main) {  
			  Toast.makeText(this@SettingActivity, "자동로그인 해제 실패", Toast.LENGTH_SHORT).show()  
			 } 
		 }  
		}  
	 }  
}
```

* DB 접근시 IO thread에서 작업하기
* 해당 로그인 해제 버튼 클릭시 현재 id를 찾아서 제거해준다.(UserLogin으로 저장되어있을 거다)
* runCatching을 사용하여 성공한 경우와 실패한 경우를 나눠서 해당 toast 메시지를 출력해준다.

-> 하나의 아이디를 통해서만 자동로그인을 설정할 수 있도록 하였다. Room을 공부하기 위해 간단한 방식으로 진행하였고, Github 연동한 아이디마다 Room에 자동로그인 상태를 저장할 수 있는 로직의 필요성이 느껴진다.

# Room 사용하여 자동로그인

> 자동로그인 한 후 해당 화면 끄고 다시 화면 킨 경우

<img width="200" alt="1212" src="https://user-images.githubusercontent.com/81394850/174617759-06b3bc88-f638-4dce-9e7e-f59c58778a39.png">

> 자동로그인 해제 클릭

<img width="200" alt="33" src="https://user-images.githubusercontent.com/81394850/174617818-0975a272-045f-4495-a372-373b2b334aa0.png">

> 자동로그인 해제 한 경우 로그인 화면으로 돌아오기

<img width="200" alt="444" src="https://user-images.githubusercontent.com/81394850/174617733-eec12360-6320-4f49-aa2f-0afe3293ab10.png">
</div>
</details>

