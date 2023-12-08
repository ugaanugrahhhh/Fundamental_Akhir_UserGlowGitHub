package com.example.userglowgithub.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userglowgithub.databinding.FragmentFollowBinding
import com.example.userglowgithub.ui.adapter.FollowAdapter
import com.example.userglowgithub.ui.viewmodel.DetailViewModel

class FollowFragment : Fragment() {

    private lateinit var binding : FragmentFollowBinding
    private lateinit var detailViewModel : DetailViewModel
    private lateinit var adapter: FollowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentFollowBinding.inflate(inflater,container,false)
        binding.recyclerViewFollowing.layoutManager=LinearLayoutManager(requireActivity())
        detailViewModel = ViewModelProvider(requireActivity() )[DetailViewModel::class.java]
        adapter = FollowAdapter {  }
        binding.recyclerViewFollowing.adapter=adapter
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { it ->
            val position = it.getInt(ARG_POSITION)
            it.getString(ARG_USERNAME)
            if (position == 1){
                detailViewModel.glowFollowers.observe(requireActivity()){
                    adapter.submitList(it)

                }

            } else {
                detailViewModel.glowFollowing.observe(requireActivity()){
                    adapter.submitList(it)
                }


            }
        }
    }

    companion object {
        const val ARG_POSITION = "position_number"
        const val ARG_USERNAME = "username"
    }

}