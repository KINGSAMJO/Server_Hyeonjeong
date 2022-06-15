
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