
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