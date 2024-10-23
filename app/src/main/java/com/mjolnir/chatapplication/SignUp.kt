package com.mjolnir.chatapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var tvLogin: TextView
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        mAuth=FirebaseAuth.getInstance()
        etName=findViewById(R.id.etUsername)
        etEmail=findViewById(R.id.etEmail)
        etPassword=findViewById(R.id.etPassword)
        btnSignUp=findViewById(R.id.btnSignUp)
        tvLogin=findViewById(R.id.tvLogin)

        btnSignUp.setOnClickListener {
            val email=etEmail.text.toString()
            val password=etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email and password cannot be empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(email.length<6){
                Toast.makeText(this, "Password must be at least 6 characters long!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            signUp(email,password)
        }

        tvLogin.setOnClickListener {
            val intent=Intent(this,LogIn::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signUp(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent=Intent(this,LogIn::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this,"Something went wrong!",Toast.LENGTH_SHORT).show()
                }
            }
    }


}