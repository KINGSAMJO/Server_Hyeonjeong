# Server_Hyeonjeong
무럭무럭 성장하는 Server 개발자입니다.

<details> 
 # 1차세미나
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
