package com.chayun.naeiyagiapp.ui.addiyagi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.chayun.naeiyagiapp.R
import com.chayun.naeiyagiapp.databinding.ActivityAddIyagiBinding
import com.chayun.naeiyagiapp.ui.ViewModelFactory
import com.chayun.naeiyagiapp.ui.iyagi.IyagiActivity
import com.chayun.naeiyagiapp.utils.reduceFileImage
import com.chayun.naeiyagiapp.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AddIyagiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddIyagiBinding
    private lateinit var factory: ViewModelFactory
    private val addIyagiViewModel: AddIyagiViewModel by viewModels { factory }
    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddIyagiBinding.inflate(layoutInflater)
        factory = ViewModelFactory.getInstance(this)
        supportActionBar?.title = getString(R.string.title_activity_add)
        setContentView(binding.root)

        binding.buttonGallery.setOnClickListener { startGallery() }
        binding.buttonAdd.setOnClickListener { uploadImage() }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun uploadImage() {
        addIyagiViewModel.getUser().observe(this@AddIyagiActivity) {
            if (getFile != null) {
                val file = reduceFileImage(getFile as File)
                val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "photo",
                    file.name,
                    requestImageFile
                )
                uploadResponse(
                    it.token,
                    imageMultipart,
                    binding.edAddDescription.text.toString().toRequestBody("text/plain".toMediaType())
                )
            } else {
                Toast.makeText(applicationContext, "Choose image from your galery", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@AddIyagiActivity)
                getFile = myFile
                binding.ivGallery.setImageURI(uri)
            }
        }
    }

    private fun uploadResponse(
        token: String,
        file: MultipartBody.Part,
        description: RequestBody
    ) {
        addIyagiViewModel.postIyagiData(token, file, description)
        addIyagiViewModel.addIyagi.observe(this@AddIyagiActivity) {
            if (!it.error) {
                val intent = Intent(this@AddIyagiActivity, IyagiActivity::class.java)
                startActivity(intent)
            }
        }
    }
}