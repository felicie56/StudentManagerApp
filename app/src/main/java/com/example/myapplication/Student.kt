package com.example.myapplication

import java.io.Serializable

data class Student(
    var mssv: String,
    var hoTen: String,
    var phone: String,
    var address: String
) : Serializable
