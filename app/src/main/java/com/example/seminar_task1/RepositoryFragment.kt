package com.example.seminar_task1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seminar_task1.databinding.FragmentRepositoryBinding

class RepositoryFragment : Fragment() {
    private var _binding : FragmentRepositoryBinding? = null
    private val binding get() = _binding ?: error("Binding이 초기화 되지 않았습니다.")
    private lateinit var repositoryAdapter : RepositoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRepositoryBinding.inflate(layoutInflater, container, false) //container, false는 무슨 의미인가
        return binding.root //정확히 어디를 가리키는 걸까?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        binding.rvRepository.addItemDecoration(ItemDecoration(50,"#FFBB86FC", 10))
    }

    private fun initAdapter(){
        repositoryAdapter = RepositoryAdapter()
        binding.rvRepository.adapter = repositoryAdapter
        repositoryAdapter.repoList.addAll(
            listOf(
                RepositoryData("Android Assignment Repository", "안드로이드 파트 과제가 아주 많이 있습니다~~~"),
                RepositoryData("SOPT Repository","세미나 자료"),
                RepositoryData("EWHA CYBER Repository","웹보안 강의자료"),
                RepositoryData("ㅋㅌ스터디 Repository", "코딩테스트 강의자료"),
                RepositoryData("아키텍처 스터디 Repository", "MVVM 아키텍처 자료"),
                RepositoryData("git 스터디 Repository", "깃 강의 자료"),
            )
        )
        repositoryAdapter.notifyDataSetChanged()
    }


    //바인딩 객체 참조 해제
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}