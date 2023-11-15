package org.hyperskill.simplebankmanager

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class PayBillsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pay_bills, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent = requireActivity().intent.getSerializableExtra("billInfo")
        val billInfoMap =
            if (intent != null) intent as Map<String, Triple<String, String, Double>> else mapOf(
                "ELEC" to Triple("Electricity", "ELEC", 45.0),
                "GAS" to Triple("Gas", "GAS", 20.0),
                "WTR" to Triple("Water", "WTR", 25.5)
            )

        view.findViewById<Button>(R.id.payBillsShowBillInfoButton).setOnClickListener {
            val input = view.findViewById<EditText>(R.id.payBillsCodeInputEditText).text.toString()
            val title = if (input in billInfoMap.keys) "Bill info" else "Error"
            val message = if (input !in billInfoMap.keys) "Wrong code" else {
                "Name: ${billInfoMap[input]!!.first}\n" +
                        "BillCode: ${billInfoMap[input]!!.second}\n" +
                        "Amount: ${"%.2f".format(billInfoMap[input]!!.third)}\$"
            }

            val dialog = AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
            if (input in billInfoMap.keys) {
                dialog
                    .setPositiveButton("Confirm") { _, _ ->
                        if (Balance.balance >= billInfoMap[input]!!.third) {
                            Toast.makeText(
                                context,
                                "Payment for bill ${billInfoMap[input]!!.first}, was successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            Balance.balance -= billInfoMap[input]!!.third
                        } else {
                            AlertDialog.Builder(context)
                                .setTitle("Error")
                                .setMessage("Not enough funds")
                                .setPositiveButton(android.R.string.ok, null)
                                .show()
                        }
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            } else {
                dialog.setPositiveButton(android.R.string.ok, null).show()
            }

        }

    }

}