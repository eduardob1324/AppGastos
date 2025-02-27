package com.ed.app_gastos.ui.ahorros

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.ed.app_gastos.R
import com.ed.app_gastos.databinding.FragmentAhorrosBinding

class AhorrosFragment : Fragment() {

    private var _binding: FragmentAhorrosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val ahorrosViewModel =
            ViewModelProvider(this).get(AhorrosViewModel::class.java)

        _binding = FragmentAhorrosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        ahorrosViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        initComponent()

        return root
    }

    private fun initComponent() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}