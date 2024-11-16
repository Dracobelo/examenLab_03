package com.castro.diego.laboratoriocalificado03

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {
    val teacherList = MutableLiveData<List<TeacherResponse>>() // Lista de profesores
    val isLoading = MutableLiveData<Boolean>() // Indicador de carga
    val errorApi = MutableLiveData<String?>() // Manejo de errores

    init {
        getAllTeachers()
    }

    private fun getAllTeachers() {
        isLoading.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = getRetrofit().create(TeacherApi::class.java).getTeachers()
                if (call.isSuccessful) {
                    call.body()?.let {
                        teacherList.postValue(it.teachers)
                    }
                } else {
                    errorApi.postValue("Error en la respuesta del servidor")
                }
            } catch (e: Exception) {
                errorApi.postValue(e.message)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://private-effe28-tecsup1.apiary-mock.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
