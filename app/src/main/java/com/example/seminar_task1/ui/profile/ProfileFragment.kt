package com.example.seminar_task1.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seminar_task1.R
import com.example.seminar_task1.ui.setting.SettingActivity
import com.example.seminar_task1.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding ?: error("ProfileFragment binding 오류")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater,container, false)
        binding.btnFollower.isSelected = true
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initTransaction()
        isSetting()
    }

    private fun initTransaction(){
        val followerFragment = FollowerFragment()
        val repositoryFragment = RepositoryFragment()

        childFragmentManager.beginTransaction().add(R.id.fcv_profile,followerFragment).commit()
        binding.btnFollower.setOnClickListener{
            binding.btnFollower.isSelected = true
            binding.btnRepository.isSelected = false
            childFragmentManager.beginTransaction().replace(R.id.fcv_profile,followerFragment).commit()
        }
        binding.btnRepository.setOnClickListener {
            binding.btnFollower.isSelected = false
            binding.btnRepository.isSelected = true
            childFragmentManager.beginTransaction().replace(R.id.fcv_profile,repositoryFragment).commit()
        }

    }

    private fun isSetting(){
        binding.btnSetting.setOnClickListener{
            val intent = Intent(requireContext(), SettingActivity::class.java)
            startActivity(intent)
        }
    }

}