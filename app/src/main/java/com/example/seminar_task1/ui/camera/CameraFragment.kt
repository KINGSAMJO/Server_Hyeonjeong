package com.example.seminar_task1.ui.camera

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.seminar_task1.databinding.FragmentCameraBinding
import android.Manifest
import com.bumptech.glide.Glide

class CameraFragment : Fragment() {
    private var _binding : FragmentCameraBinding? = null
    var photoUri : Uri? = null
    private val binding get() = _binding ?: error("CameraFragment Binding 오류")

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                navigateGallery()
            } else {
                Toast.makeText(requireContext(),"갤러리 접근 실패",Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCameraBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeProfileImage()

    }

    private fun changeProfileImage() {
        //버튼 클릭시 갤러리 접근하여 이미지 uri 전달
        binding.btCamera.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    navigateGallery()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    showInContextUI()
                }
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }
    }

    private fun navigateGallery() {
        val photoIntent = Intent(Intent.ACTION_PICK) //open the album
        photoIntent.type = "image/*"
        openGallery.launch(photoIntent)
    }


    private val openGallery : ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == RESULT_OK && result.data != null){
            photoUri = result.data?.data
            Glide.with(this)
                .load(photoUri)
                .into(binding.ivCamera)
        }else if(result.resultCode == RESULT_CANCELED){
            Toast.makeText(requireContext(),"사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun showInContextUI() {
        AlertDialog.Builder(requireContext())
            .setTitle("권한 동의 필요")
            .setMessage("프로필 사진 수정을 위해 갤러리 접근 권한이 필요합니다.")
            .setPositiveButton("동의") { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            .setNegativeButton("거부") { _, _ ->
                Toast.makeText(requireContext(), "갤러리 접근 권한이 없습니다.", Toast.LENGTH_SHORT).show()
            }
            .create()
            .show()
    }


}