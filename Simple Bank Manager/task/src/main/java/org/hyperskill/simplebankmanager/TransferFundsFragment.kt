package org.hyperskill.simplebankmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController

class TransferFundsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transfer_funds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nameView = view.findViewById<EditText>(R.id.transferFundsAccountEditText)
        val amountView = view.findViewById<EditText>(R.id.transferFundsAmountEditText)

        val regexName = "[sc]a\\d\\d\\d\\d".toRegex()

        view.findViewById<Button>(R.id.transferFundsButton).setOnClickListener {
            val nameValidation = nameView.text.toString().matches(regexName).also { if (!it) nameView.error = "Invalid account number"}
            val amountValidation = (amountView.text.isNotEmpty() && amountView.text.toString().toDouble() > 0).also { if (!it) amountView.error = "Invalid amount"}

            if (nameValidation && amountValidation) {
                if (amountView.text.toString().toDouble() <= Balance.balance) {
                    Toast.makeText(context, "Transferred ${"%.2f".format(amountView.text.toString().toDouble())}\$ to account ${nameView.text}", Toast.LENGTH_SHORT).show()
                    Balance.balance-=amountView.text.toString().toDouble()
                    view.findNavController().navigate(R.id.userMenuFragment)
                } else {
                    Toast.makeText(context, "Not enough funds to transfer ${"%.2f".format(amountView.text.toString().toDouble())}\$", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}