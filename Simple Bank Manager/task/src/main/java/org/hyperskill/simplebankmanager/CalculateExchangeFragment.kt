package org.hyperskill.simplebankmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class CalculateExchangeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calculate_exchange, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val intent = requireActivity().intent.getSerializableExtra("exchangeMap")
        val defaultMap = if (intent != null) intent as Map<String, Map<String, Double>> else mapOf(
            "EUR" to mapOf(
                "GBP" to 0.5,
                "USD" to 2.0
            ),
            "GBP" to mapOf(
                "EUR" to 2.0,
                "USD" to 4.0
            ),
            "USD" to mapOf(
                "EUR" to 0.5,
                "GBP" to 0.25
            )
        )

        val spinnerFrom = view.findViewById<Spinner>(R.id.calculateExchangeFromSpinner)
        val spinnerTo = view.findViewById<Spinner>(R.id.calculateExchangeToSpinner)

        spinnerFrom.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                pos: Int,
                id: Long
            ) {
                if (spinnerFrom.selectedItem.toString() == spinnerTo.selectedItem.toString()) {
                    Toast.makeText(context, "Cannot convert to same currency", Toast.LENGTH_SHORT)
                        .show()
                    spinnerTo.setSelection(spinnerTo.selectedItemPosition % 2 + 1)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        spinnerTo.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                pos: Int,
                id: Long
            ) {
                if (spinnerFrom.selectedItem.toString() == spinnerTo.selectedItem.toString()) {
                    Toast.makeText(context, "Cannot convert to same currency", Toast.LENGTH_SHORT)
                        .show()
                    spinnerTo.setSelection(spinnerTo.selectedItemPosition % 2 + 1)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        view.findViewById<Button>(R.id.calculateExchangeButton).setOnClickListener {

            val from = spinnerFrom.selectedItem.toString()
            val to = spinnerTo.selectedItem.toString()
            val course = defaultMap[from]!![to]

            if (view.findViewById<EditText>(R.id.calculateExchangeAmountEditText).text.isEmpty()) {
                Toast.makeText(context, "Enter amount", Toast.LENGTH_SHORT).show()
            } else {
                val input =
                    view.findViewById<EditText>(R.id.calculateExchangeAmountEditText).text.toString()
                        .toDouble()
                view.findViewById<TextView>(R.id.calculateExchangeDisplayTextView).text =
                    "${"%.2f".format(input)} $from = ${"%.2f".format(input * course!!)} $to"
            }
        }
    }
}