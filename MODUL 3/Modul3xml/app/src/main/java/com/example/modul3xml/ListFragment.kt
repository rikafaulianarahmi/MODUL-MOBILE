package com.example.modul3xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modul3xml.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this).get(LegoViewModel::class.java)

        val layoutManagerHorizontal = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvHighlight.layoutManager = layoutManagerHorizontal

        val adapterHighlight = HighlightAdapter(viewModel.legoList)
        binding.rvHighlight.adapter = adapterHighlight

        binding.rvLego.layoutManager = LinearLayoutManager(requireContext())
        val adapterLego = LegoAdapter(viewModel.legoList)
        binding.rvLego.adapter = adapterLego

        binding.btnLanguage.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_languageFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}