package com.example.modul3xml

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modul3xml.databinding.FragmentListBinding
import kotlinx.coroutines.launch
import timber.log.Timber

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

        val factory = LegoViewModelFactory("Aplikasi Lego Collection")
        val viewModel = ViewModelProvider(this, factory)[LegoViewModel::class.java]

        binding.rvHighlight.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )

        // Collect StateFlow legoList
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.legoList.collect { list ->
                binding.rvHighlight.adapter = HighlightAdapter(list)
                binding.rvLego.layoutManager = LinearLayoutManager(requireContext())
                binding.rvLego.adapter = LegoAdapter(
                    listData = list,
                    onDetailClick = { lego -> viewModel.onDetailClick(lego) },
                    onWebClick = { lego -> viewModel.onWebClick(lego) }
                )
            }
        }

        // Collect StateFlow clickEvent
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.clickEvent.collect { event ->
                when (event) {
                    is LegoViewModel.ClickEvent.DetailClick -> {
                        Timber.d("Berpindah ke Detail → ID: ${event.lego.id} | Judul: ${event.lego.title} | Tema: ${event.lego.theme}")
                        val bundle = Bundle()
                        bundle.putParcelable("data_lego", event.lego)
                        findNavController().navigate(R.id.action_list_to_detail, bundle)
                        viewModel.resetClickEvent()
                    }
                    is LegoViewModel.ClickEvent.WebClick -> {
                        val intent = Intent(Intent.ACTION_VIEW, event.lego.webUrl.toUri())
                        startActivity(intent)
                        viewModel.resetClickEvent()
                    }
                    null -> { /* tidak ada event */ }
                }
            }
        }

        binding.btnLanguage.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_languageFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}