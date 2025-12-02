package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {

    private lateinit var edtMssv: EditText
    private lateinit var edtHoTen: EditText
    private lateinit var edtPhone: EditText
    private lateinit var edtAddress: EditText
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        edtMssv = findViewById(R.id.edtMssv)
        edtHoTen = findViewById(R.id.edtHoTen)
        edtPhone = findViewById(R.id.edtPhone)
        edtAddress = findViewById(R.id.edtAddress)
        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)

        btnSave.setOnClickListener {
            val mssv = edtMssv.text.toString().trim()
            val hoTen = edtHoTen.text.toString().trim()
            val phone = edtPhone.text.toString().trim()
            val address = edtAddress.text.toString().trim()

            if (mssv.isEmpty() || hoTen.isEmpty()) {
                Toast.makeText(this, "MSSV và Họ tên không được trống", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val student = Student(mssv, hoTen, phone, address)
            val resultIntent = Intent()
            resultIntent.putExtra(MainActivity.EXTRA_STUDENT, student)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        btnCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}
