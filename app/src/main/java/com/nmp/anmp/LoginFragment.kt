package com.nmp.anmp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tilUsername = view.findViewById<TextInputLayout>(R.id.tilUsername)
        val tilPassword = view.findViewById<TextInputLayout>(R.id.tilPassword)
        val btnLogin = view.findViewById<MaterialButton>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val username = tilUsername.editText?.text.toString()
            val password = tilPassword.editText?.text.toString()

            tilUsername.error = null
            tilPassword.error = null
            if (username == "student" && password == "123") {
                findNavController().navigate(R.id.action_login_to_list)

                Toast.makeText(context, "Welcome, $username!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Username atau Password salah", Toast.LENGTH_SHORT).show()

                if (username != "student") tilUsername.error = "User tidak ditemukan"
                if (password != "123") tilPassword.error = "Password salah"
            }
        }
    }
}