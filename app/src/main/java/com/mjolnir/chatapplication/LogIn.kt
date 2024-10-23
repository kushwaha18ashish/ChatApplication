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

class LogIn : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogIn: Button
    private lateinit var tvSignUp: TextView
    private lateinit var tvForgetPassword: TextView
    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_log_in)

        mAuth=FirebaseAuth.getInstance()

        etEmail=findViewById(R.id.etemail)
        etPassword=findViewById(R.id.etpassword)
        btnLogIn=findViewById(R.id.btnLogin)
        tvSignUp=findViewById(R.id.tvSignUp)
        tvForgetPassword=findViewById(R.id.tvforgetPassword)

        btnLogIn.setOnClickListener {
           val email=etEmail.text.toString()
           val password=etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email and password cannot be empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
           
           login(email,password) 
        }

        tvSignUp.setOnClickListener {
            val intent=Intent(this,SignUp::class.java)
            startActivity(intent)

        }

        tvForgetPassword.setOnClickListener {
            showForgotPasswordDialog()
        }



    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent=Intent(this,MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this,"User Doesn't Exists.",Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun showForgotPasswordDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Reset Password")

        val input = EditText(this)
        input.hint = "Enter your email"
        builder.setView(input)

        builder.setPositiveButton("Send") { dialog, _ ->
            val email = input.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                sendPasswordResetEmail(email)
            }
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    private fun sendPasswordResetEmail(email: String) {
        mAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Reset email sent to $email", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error sending reset email", Toast.LENGTH_SHORT).show()
                }
            }
    }
}