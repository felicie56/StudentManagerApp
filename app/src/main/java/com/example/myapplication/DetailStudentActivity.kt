package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailStudentActivity : AppCompatActivity() {

    private lateinit var edtMssv: EditText
    private lateinit var edtHoTen: EditText
    private lateinit var edtPhone: EditText
    private lateinit var edtAddress: EditText
    private lateinit var btnUpdate: Button
    private lateinit var btnBack: Button

    private var position: Int = -1
    private var student: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_student)

        edtMssv = findViewById(R.id.edtMssv)
        edtHoTen = findViewById(R.id.edtHoTen)
        edtPhone = findViewById(R.id.edtPhone)
        edtAddress = findViewById(R.id.edtAddress)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnBack = findViewById(R.id.btnBack)

        student = intent.getSerializableExtra(MainActivity.EXTRA_STUDENT) as? Student
        position = intent.getIntExtra(MainActivity.EXTRA_POSITION, -1)

        if (student == null || position == -1) {
            Toast.makeText(this, "Dữ liệu không hợp lệ", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        edtMssv.setText(student!!.mssv)
        edtHoTen.setText(student!!.hoTen)
        edtPhone.setText(student!!.phone)
        edtAddress.setText(student!!.address)

        btnUpdate.setOnClickListener {
            val s = student ?: return@setOnClickListener
            s.hoTen = edtHoTen.text.toString().trim()
            s.phone = edtPhone.text.toString().trim()
            s.address = edtAddress.text.toString().trim()

            if (s.hoTen.isEmpty()) {
                Toast.makeText(this, "Họ tên không được trống", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val resultIntent = Intent()
            resultIntent.putExtra(MainActivity.EXTRA_STUDENT, s)
            resultIntent.putExtra(MainActivity.EXTRA_POSITION, position)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        btnBack.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}
