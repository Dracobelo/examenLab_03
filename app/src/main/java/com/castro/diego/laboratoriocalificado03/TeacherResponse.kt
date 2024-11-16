package com.castro.diego.laboratoriocalificado03

data class TeacherResponse(
    val name: String,
    val last_name: String,
    val phone: String,
    val email: String,
    val imageUrl: String
){
    fun getTeacherNameImage(): String = email.substringBefore("@")

    fun getTeacherImage(): String =
        "https://raw.githubusercontent.com/victorskatepro/ContactList/master/app/src/main/res/drawable/${getTeacherNameImage()}.jpeg"


}
