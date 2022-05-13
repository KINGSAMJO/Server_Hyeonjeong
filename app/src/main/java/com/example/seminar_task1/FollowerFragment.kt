package com.example.seminar_task1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seminar_task1.adapter.FollowerAdapter
import com.example.seminar_task1.databinding.FragmentFollowerBinding
import com.example.seminar_task1.model.FollowerData
import com.example.seminar_task1.util.ItemDecoration

class FollowerFragment : Fragment() {
    private var _binding : FragmentFollowerBinding? = null
    private val binding get() = _binding ?: error("FollowerFragment Binding 오류")
    private lateinit var followerAdapter : FollowerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFollowerBinding.inflate(layoutInflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        binding.rvFollower.addItemDecoration(ItemDecoration(10,"#FFBB86FC",10))
    }

    private fun initAdapter(){
        followerAdapter = FollowerAdapter()
        binding.rvFollower.adapter = followerAdapter
        followerAdapter.followerList.addAll(
            listOf(
                FollowerData("이강민", "안팟장님"),
                FollowerData("박현정","SAMJO SERVER"),
                FollowerData("한승현","KING IOS"),
                FollowerData("황연진", "SAMJO WEB"),
                FollowerData("이영주", "KING DESIGN"),
                FollowerData("김효림", "안드 루피"),
                FollowerData("문다빈", "안미녀"),
                FollowerData("최유리", "유림이"),
                FollowerData("문명주", "알고리즘신"),
                FollowerData("김수빈", "차기안팟장")

            )
        )
        followerAdapter.notifyDataSetChanged()

    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}