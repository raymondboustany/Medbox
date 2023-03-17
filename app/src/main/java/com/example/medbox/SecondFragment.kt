package com.example.medbox

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

import androidx.navigation.fragment.findNavController
import com.example.medbox.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        //false is passed for the attachToRoot parameter to indicate that the inflated layout should not be attached to the parent view because the FragmentTransaction will handle that.
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment) // actions defined in nav_graph.xml
        }

        val spinner2 = binding.timesadaySpinner
        val numbers2 = arrayOf("1", "2", "3", "4")
        val adapter2 = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, numbers2)
        spinner2.adapter = adapter2
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}