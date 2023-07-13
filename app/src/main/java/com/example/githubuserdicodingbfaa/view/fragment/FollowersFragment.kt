package com.example.githubuserdicodingbfaa.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserdicodingbfaa.adapter.FollowerAdapter
import com.example.githubuserdicodingbfaa.databinding.FragmentFollowersBinding
import com.example.githubuserdicodingbfaa.model.response.ResponseFollowersItem
import com.example.githubuserdicodingbfaa.utils.Helper
import com.example.githubuserdicodingbfaa.view.home.DetailActivity
import com.example.githubuserdicodingbfaa.viewmodel.FollowersViewModel

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var followersViewModel: FollowersViewModel
    private val helper = Helper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment, and initialize the binding
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = activity?.intent?.getStringExtra(DetailActivity.EXTRA_USERNAME)
        followersViewModel.getFollower(username.toString())

        followersViewModel.isLoading.observe(viewLifecycleOwner) {
            helper.showLoading(it, binding.progressBar3)
        }
        followersViewModel.listFollowersUsers.observe(viewLifecycleOwner) { listFollower ->
            setDataToFragment(listFollower)
        }
    }

    private fun setDataToFragment(listFollower: List<ResponseFollowersItem>) {
        val listUser = ArrayList<ResponseFollowersItem>()
        with(binding) {
            for (user in listFollower) {
                listUser.clear()
                listUser.addAll(listFollower)
            }
            rvFollowers.layoutManager = LinearLayoutManager(context)
            val adapter = FollowerAdapter(listFollower)
            rvFollowers.adapter = adapter
            adapter.setOnItemClickCallback(object : FollowerAdapter.OnItemClickCallback {
                override fun onItemClicked(data: ResponseFollowersItem) {
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