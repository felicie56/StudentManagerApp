package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class MainActivity : AppCompatActivity() {

    private lateinit var edtMssv: EditText
    private lateinit var edtHoTen: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var lvStudents: ListView

    private val students = ArrayList<Student>()
    private lateinit var adapter: StudentAdapter

    // vị trí item đang được chọn
    private var selectedIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ánh xạ view
        edtMssv = findViewById(R.id.edtMssv)
        edtHoTen = findViewById(R.id.edtHoTen)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        lvStudents = findViewById(R.id.lvStudents)

        // adapter custom
        adapter = StudentAdapter(this, students)
        lvStudents.adapter = adapter

        // Add
        btnAdd.setOnClickListener {
            addStudent()
        }

        // Update
        btnUpdate.setOnClickListener {
            updateStudent()
        }

        // ❌ KHÔNG cần setOnItemClickListener nữa
        // vì mình xử lý click item ngay trong getView() của adapter
    }

    private fun addStudent() {
        val mssv = edtMssv.text.toString().trim()
        val hoTen = edtHoTen.text.toString().trim()

        if (mssv.isEmpty() || hoTen.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập MSSV và Họ tên", Toast.LENGTH_SHORT).show()
            return
        }

        if (students.any { it.mssv == mssv }) {
            Toast.makeText(this, "MSSV đã tồn tại", Toast.LENGTH_SHORT).show()
            return
        }

        students.add(Student(mssv, hoTen))
        adapter.notifyDataSetChanged()

        edtMssv.text.clear()
        edtHoTen.text.clear()
        selectedIndex = -1
    }

    private fun updateStudent() {
        if (selectedIndex == -1) {
            Toast.makeText(this, "Hãy chọn sinh viên cần sửa", Toast.LENGTH_SHORT).show()
            return
        }

        val hoTenMoi = edtHoTen.text.toString().trim()
        if (hoTenMoi.isEmpty()) {
            Toast.makeText(this, "Họ tên không được để trống", Toast.LENGTH_SHORT).show()
            return
        }

        students[selectedIndex].hoTen = hoTenMoi
        adapter.notifyDataSetChanged()
        Toast.makeText(this, "Đã cập nhật sinh viên", Toast.LENGTH_SHORT).show()
    }

    data class Student(
        val mssv: String,
        var hoTen: String
    )

    inner class StudentAdapter(
        private val context: Context,
        private val data: MutableList<Student>
    ) : BaseAdapter() {

        override fun getCount(): Int = data.size

        override fun getItem(position: Int): Any = data[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View
            val holder: ViewHolder

            if (convertView == null) {
                view = LayoutInflater.from(context)
                    .inflate(R.layout.item_student, parent, false)
                holder = ViewHolder(
                    view.findViewById(R.id.tvHoTen),
                    view.findViewById(R.id.tvMssv),
                    view.findViewById(R.id.btnDelete)
                )
                view.tag = holder
            } else {
                view = convertView
                holder = convertView.tag as ViewHolder
            }

            val student = data[position]
            holder.tvHoTen.text = student.hoTen
            holder.tvMssv.text = student.mssv

            // 👉 Click vào CẢ ITEM: load dữ liệu lên EditText để Update
            view.setOnClickListener {
                edtMssv.setText(student.mssv)
                edtHoTen.setText(student.hoTen)
                selectedIndex = position
            }

            // 👉 Click vào nút Delete: xóa phần tử
            holder.btnDelete.setOnClickListener {
                data.removeAt(position)
                notifyDataSetChanged()

                if (position == selectedIndex) {
                    selectedIndex = -1
                    edtMssv.text.clear()
                    edtHoTen.text.clear()
                } else if (position < selectedIndex) {
                    selectedIndex -= 1
                }

                Toast.makeText(context, "Đã xóa sinh viên", Toast.LENGTH_SHORT).show()
            }

            return view
        }

        // bạn đã sửa thành inner rồi thì cứ để vậy:
        private inner class ViewHolder(
            val tvHoTen: TextView,
            val tvMssv: TextView,
            val btnDelete: ImageButton
        )
    }
}
