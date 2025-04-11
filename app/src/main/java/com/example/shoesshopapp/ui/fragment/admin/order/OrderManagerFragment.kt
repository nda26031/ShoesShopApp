package com.example.shoesshopapp.ui.fragment.admin.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentOrderManagerBinding
import com.example.shoesshopapp.model.data.Order

class OrderManagerFragment : Fragment() {

    private lateinit var binding: FragmentOrderManagerBinding

    private val orderManagerViewModel: OrderManagerViewModel by lazy {
        ViewModelProvider(
            this,
            OrderManagerViewModel.OrderManagerViewModelFactory(requireActivity().application)
        )[OrderManagerViewModel::class]
    }

    private val orderManagerAdapter: OrderManagerAdapter by lazy {
        OrderManagerAdapter(onItemClick = { onItemClick(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOrderManagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAllOrder()
        setupAdapter()
    }

    private fun onItemClick(order: Order) {
        val bundle = Bundle()
        bundle.putInt("orderId", order.orderId)
        bundle.putInt("userId", order.userId)
        findNavController().navigate(
            R.id.action_adminHomeFragment_to_orderManagerDetailFragment,
            bundle
        )
    }

    private fun getAllOrder() {
        orderManagerViewModel.getAllOrder().observe(viewLifecycleOwner) { orders ->
            orderManagerAdapter.submitList(orders)
        }
    }

    private fun setupAdapter() {
        binding.rvOrder.adapter = orderManagerAdapter
        binding.rvOrder.layoutManager = LinearLayoutManager(context)
    }
}