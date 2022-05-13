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
