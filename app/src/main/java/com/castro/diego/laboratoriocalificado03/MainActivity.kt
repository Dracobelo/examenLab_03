package com.castro.diego.laboratoriocalificado03

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.castro.diego.laboratoriocalificado03.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val adapter by lazy { TeacherAdapter(emptyList()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración del RecyclerView
        binding.rvTeachers.layoutManager = LinearLayoutManager(this)
        binding.rvTeachers.adapter = adapter

        // Configura las acciones
        adapter.onItemClick = { teacher ->
            callTeacher(teacher.phone)
        }

        adapter.onItemLongClick = { teacher ->
            sendEmailToTeacher(teacher.email)
        }

        // Observadores del ViewModel
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.teacherList.observe(this) { teachers ->
            adapter.updateList(teachers)
        }

        viewModel.errorApi.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, "Error: $it", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun callTeacher(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phone")
        }
        startActivity(intent)
    }

    private fun sendEmailToTeacher(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
        }
        startActivity(Intent.createChooser(intent, "Enviar correo"))
    }
}
