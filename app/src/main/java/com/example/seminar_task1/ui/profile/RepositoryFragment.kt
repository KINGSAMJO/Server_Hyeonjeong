package com.example.seminar_task1.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.seminar_task1.R
import com.example.seminar_task1.databinding.FragmentRepositoryBinding
import com.example.seminar_task1.data.model.RepositoryData
import com.example.seminar_task1.ui.base.BaseFragment
import com.example.seminar_task1.ui.detail.DetailActivity
import com.example.seminar_task1.ui.profile.adapter.RepositoryAdapter
import com.example.seminar_task1.ui.viewmodel.RepositoryViewModel
import com.example.seminar_task1.util.ItemDecoration

class RepositoryFragment(private val userLogin: String) :
    BaseFragment<FragmentRepositoryBinding>(R.layout.fragment_repository) {
    private val repositoryViewModel by viewModels<RepositoryViewModel>()
    private lateinit var repositoryAdapter: RepositoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.repositoryViewModel = repositoryViewModel
        return binding.root //정확히 어디를 가리키는 걸까?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        binding.rvRepository.addItemDecoration(ItemDecoration(10, "#FFBB86FC", 10))
    }

    private fun initAdapter() {
        repositoryViewModel.repositoryNetwork(userLogin)

        repositoryAdapter = RepositoryAdapter {
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.apply {
                intent.putExtra("name", it.name)
                intent.putExtra("description", it.description)
            }
            startActivity(intent)
        }
        binding.rvRepository.adapter = repositoryAdapter

        repositoryViewModel.repositoryData.observe(viewLifecycleOwner) {
            val repositories = it
            for (i in repositories) {
                repositoryAdapter.repoList.add(i)
            }
        }
        repositoryAdapter.notifyDataSetChanged()
    }


    //바인딩 객체 참조 해제
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}