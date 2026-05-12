package com.example.modul3xml

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.modul3xml.databinding.FragmentLanguageBinding
import java.util.Locale

class LanguageFragment : Fragment(R.layout.fragment_language) {
    private var _binding: FragmentLanguageBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLanguageBinding.bind(view)

        binding.btnIndo.setOnClickListener { setLocale("in") }

        binding.btnEnglish.setOnClickListener { setLocale("en") }
    }

    private fun setLocale(langCode: String) {
        val locale = Locale(langCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)

        requireActivity().resources.updateConfiguration(config, requireActivity().resources.displayMetrics)

        requireActivity().recreate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}