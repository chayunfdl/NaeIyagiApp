package com.chayun.naeiyagiapp.ui.detailiyagi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.chayun.naeiyagiapp.databinding.ActivityDetailIyagiBinding
import com.chayun.naeiyagiapp.R
import com.chayun.naeiyagiapp.data.response.ListIyagiItem

class DetailIyagiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailIyagiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailIyagiBinding.inflate(layoutInflater)
        supportActionBar?.title = getString(R.string.title_activity_detail)
        setContentView(binding.root)

        @Suppress("DEPRECATION") val data = intent.getParcelableExtra<ListIyagiItem>("DATA")
        val photo = data?.photoUrl
        val name = data?.name
        val description = data?.description

        Glide.with(this@DetailIyagiActivity)
            .load(photo)
            .into(binding.ivDetailPhoto)
        binding.tvDetailName.text = name
        binding.tvDetailDescription.text = description
    }
}