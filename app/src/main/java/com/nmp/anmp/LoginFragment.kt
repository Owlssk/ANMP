package com.nmp.anmp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nmp.anmp.databinding.FragmentLoginBinding
import com.nmp.anmp.model.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            findNavController().navigate(R.id.action_login_to_list)
            return
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.tilUsername.editText?.text.toString()
            val password = binding.tilPassword.editText?.text.toString()

            binding.tilUsername.error = null
            binding.tilPassword.error = null

            lifecycleScope.launch(Dispatchers.IO) {
                val db = AppDatabase(requireContext())
                val user = db.userDao().login(username, password)

                withContext(Dispatchers.Main) {
                    if (user != null) {
                        with(sharedPref.edit()) {
                            putBoolean("isLoggedIn", true)
                            putString("username", user.username)
                            apply()
                        }
                        Toast.makeText(context, "Welcome, ${user.username}!", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_login_to_list)
                    } else {
                        Toast.makeText(context, "Username atau Password salah", Toast.LENGTH_SHORT).show()
                        binding.tilUsername.error = "User tidak ditemukan atau Password salah"
                    }
                }
            }
        }
    }
}