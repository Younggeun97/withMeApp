package com.example.withmeapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.withmeapp.databinding.ActivityLoginBinding

import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.core.view.View
import kotlinx.android.synthetic.main.activity_login.*
import java.security.NoSuchAlgorithmException
import java.util.*


class LoginActivity : AppCompatActivity() {
    //    var auth: FirebaseAuth? = null
//    var googleSignInClient: GoogleSignInClient = null
    lateinit var auth: FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient
    var GOOGLE_LOGIN_CODE = 9001


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        //Google 로그인 옵션 구성. requestIdToken 및 Email 요청
        binding.logingoogle.setOnClickListener {
            //First step
            googleLogin()
        }
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("203273459612-n47f6um6u28smldvjh49rm3ap0ho399q.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }


    fun googleLogin() {
        var signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, GOOGLE_LOGIN_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_LOGIN_CODE) {
            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)
//             val result = GoogleSignIn.getSignedInAccountFromIntent(data)
            // result가 성공했을 때 이 값을 firebase에 넘겨주기
            if (result!!.isSuccess) {
                var account = result.signInAccount
                // Second step
                firebaseAuthWithGoogle(account!!)
            }
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                val account = result.getResult(ApiException::class.java)
//                firebaseAuthWithGoogle(account!!)
//
//            } catch (e: ApiException) {
//                // Google Sign In failed, update UI appropriately
//                Log.w("LoginActivity", "Google sign in failed", e)
//            }

        }
    }

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        Log.d("LoginActivity", "firebaseAuthWithGoogle:" + account.id!!)

        //Google SignInAccount 객체에서 ID 토큰을 가져와서 Firebase Auth로 교환하고 Firebase에 인증
        var credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login, 아이디와 패스워드가 맞았을 때
                    Toast.makeText(this, "success", Toast.LENGTH_LONG).show()
                    moveMainPage(task.result?.user)
                    Log.d("LoginActivity", "moveMainPage proceeded")
                } else {
                    // Show the error message, 아이디와 패스워드가 틀렸을 때
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }


    // 로그인이 성공하면 다음 페이지로 넘어가는 함수
    fun moveMainPage(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }


//    private fun signOut() { // 로그아웃
//        // Firebase sign out
//        firebaseAuth.signOut()
//
//        // Google sign out
//        googleSignInClient.signOut().addOnCompleteListener(this) {
//            //updateUI(null)
//        }
//    }


}





