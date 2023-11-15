package org.hyperskill.simplebankmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.loginButton).setOnClickListener {
            val password = view.findViewById<EditText>(R.id.loginPassword).text.toString()
            val name = view.findViewById<EditText>(R.id.loginUsername).text.toString()
            val passwordCheck = requireActivity().intent.extras?.getString("password") ?: "1234"
            val nameCheck = requireActivity().intent.extras?.getString("username") ?: "Lara"

            if (password == passwordCheck && name == nameCheck) {
                Toast.makeText(context, "logged in", Toast.LENGTH_SHORT).show()
                view.findNavController().navigate(
                    R.id.action_loginFragment_to_userMenuFragment,
                    bundleOf("username" to name)
                )
            } else {
                Toast.makeText(context, "invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}