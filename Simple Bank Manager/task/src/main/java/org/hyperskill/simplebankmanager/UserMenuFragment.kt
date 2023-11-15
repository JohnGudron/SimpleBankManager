package org.hyperskill.simplebankmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController

private const val ARG_PARAM1 = "username"

class UserMenuFragment : Fragment() {
    private var param1: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.userMenuWelcomeTextView).text = "Welcome $param1"

        view.findViewById<Button>(R.id.userMenuViewBalanceButton).setOnClickListener {
            view.findNavController().navigate(R.id.action_userMenuFragment_to_viewBalanceFragment)
        }

        view.findViewById<Button>(R.id.userMenuTransferFundsButton).setOnClickListener {
            view.findNavController().navigate(R.id.action_userMenuFragment_to_transferFundsFragment)
        }

        view.findViewById<Button>(R.id.userMenuExchangeCalculatorButton).setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_userMenuFragment_to_calculateExchangeFragment)
        }

        view.findViewById<Button>(R.id.userMenuPayBillsButton).setOnClickListener {
            view.findNavController().navigate(R.id.action_userMenuFragment_to_payBillsFragment)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(username: String) =
            UserMenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, username)
                }
            }
    }
}