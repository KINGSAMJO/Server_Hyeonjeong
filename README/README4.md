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