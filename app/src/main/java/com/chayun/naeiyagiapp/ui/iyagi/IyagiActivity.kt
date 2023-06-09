package com.chayun.naeiyagiapp.ui.iyagi

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chayun.naeiyagiapp.R
import com.chayun.naeiyagiapp.adapter.IyagiListAdapter
import com.chayun.naeiyagiapp.adapter.LoadingStateAdapter
import com.chayun.naeiyagiapp.data.response.ListIyagiItem
import com.chayun.naeiyagiapp.database.paging.IyagiPagingViewModel
import com.chayun.naeiyagiapp.database.paging.ViewModelFactoryPaging
import com.chayun.naeiyagiapp.databinding.ActivityIyagiBinding
import com.chayun.naeiyagiapp.ui.ViewModelFactory
import com.chayun.naeiyagiapp.ui.addiyagi.AddIyagiActivity
import com.chayun.naeiyagiapp.ui.login.LoginActivity
import com.chayun.naeiyagiapp.ui.maps.MapsActivity

class IyagiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIyagiBinding
    private lateinit var factory: ViewModelFactory
    private val iyagiViewModel: IyagiViewModel by viewModels { factory }
    private lateinit var iyagi: ArrayList<ListIyagiItem>
    private val iyagiPagingViewModel: IyagiPagingViewModel by viewModels {
        ViewModelFactoryPaging(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIyagiBinding.inflate(layoutInflater)
        factory = ViewModelFactory.getInstance(this)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.iyagiList.layoutManager = layoutManager

        settingAdapter()
        setupAction()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.look_maps -> {
                startActivity(Intent(this, MapsActivity::class.java).putParcelableArrayListExtra("location",iyagi))
                return true
            }
            R.id.add_iyagi -> {
                startActivity(Intent(this, AddIyagiActivity::class.java))
                return true
            }
            R.id.action_logout-> {
                iyagiViewModel.logout()
                startActivity(Intent(this, LoginActivity::class.java))
                return true
            }
            else -> true
        }
    }

    private fun settingAdapter() {
        val adapter = IyagiListAdapter()
        binding.iyagiList.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        iyagiPagingViewModel.iyagiPaging.observe(this) {
            adapter.submitData(lifecycle, it)
        }
        iyagiViewModel.listIyagi.observe(this) {
            iyagi = it.listIyagi as ArrayList<ListIyagiItem>
        }
    }

    private fun setupAction() {
        iyagiViewModel.getUser().observe(this@IyagiActivity) {
            val token = it.token
            if(it.isLogin) {
                supportActionBar?.title = "Hai, ${it.name}!"
                iyagiViewModel.getIyagiData(token)
            } else {
                startActivity(Intent(this@IyagiActivity, LoginActivity::class.java))
            }
        }
    }
}