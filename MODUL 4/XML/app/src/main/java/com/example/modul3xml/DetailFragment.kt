package com.example.modul3xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.modul3xml.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val legoDiterima = arguments?.getParcelable<Lego>("data_lego")

        if (legoDiterima != null) {
            binding.ivDetail.setImageResource(legoDiterima.imageRes)

            binding.tvTitleDetail.text = legoDiterima.title

            val teksDetail = "Tahun Rilis: ${legoDiterima.year}\n" +
                    "Tema: ${legoDiterima.theme}\n" +
                    "Jumlah Kepingan: ${legoDiterima.pieces}\n\n" +
                    "Deskripsi:\n${legoDiterima.description}"

            binding.tvDescDetail.text = teksDetail
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}