package com.ed.app_gastos.ui.gastos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.ed.app_gastos.databinding.FragmentGastosBinding

class GastosFragment : Fragment() {

    private var _binding: FragmentGastosBinding? = null
    private lateinit var rvGastos : RecyclerView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val gastosViewModel =
            ViewModelProvider(this).get(GastosViewModel::class.java)

        _binding = FragmentGastosBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        gastosViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        initComponet()
        return root
    }

    private fun initComponet() {
        rvGastos = binding.rvGastos
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}