package com.example.githubuserdicodingbfaa.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserdicodingbfaa.R
import com.example.githubuserdicodingbfaa.adapter.FollowerAdapter
import com.example.githubuserdicodingbfaa.adapter.FollowingAdapter
import com.example.githubuserdicodingbfaa.databinding.FragmentFollowersBinding
import com.example.githubuserdicodingbfaa.databinding.FragmentFollowingBinding
import com.example.githubuserdicodingbfaa.model.ResponseFollowersItem
import com.example.githubuserdicodingbfaa.model.ResponseFollowingItem
import com.example.githubuserdicodingbfaa.utils.Helper
import com.example.githubuserdicodingbfaa.view.DetailActivity
import com.example.githubuserdicodingbfaa.viewmodel.FollowersViewModel
import com.example.githubuserdicodingbfaa.viewmodel.FollowingViewModel

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var followingViewModel: FollowingViewModel
    private val helper = Helper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment, and initialize the binding
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = activity?.intent?.getStringExtra(DetailActivity.EXTRA_USERNAME)
        followingViewModel.getFollowing(username.toString())

        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            helper.showLoading(it, binding.progressBar4)
        }
        followingViewModel.listFollowingUsers.observe(viewLifecycleOwner) { listFollower ->
            setDataToFragment(listFollower)
        }
    }

    private fun setDataToFragment(listFollowing: List<ResponseFollowingItem>) {
        val listUser = ArrayList<ResponseFollowingItem>()
        with(binding) {
            for (user in listFollowing) {
                listUser.clear()
                listUser.addAll(listFollowing)
            }
            rvFollowing.layoutManager = LinearLayoutManager(context)
            val adapter = FollowingAdapter(listFollowing)
            rvFollowing.adapter = adapter
            adapter.setOnItemClickCallback(object : FollowingAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ResponseFollowingItem) {
                    Intent (context, DetailActivity::class.java).also {
                        it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                        startActivity(it)
                    }
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}