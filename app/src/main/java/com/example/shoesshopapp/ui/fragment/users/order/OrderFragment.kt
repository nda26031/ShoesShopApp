package com.example.shoesshopapp.ui.fragment.users.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentOrderBinding
import com.example.shoesshopapp.model.data.Order

class OrderFragment : Fragment() {

    private lateinit var binding: FragmentOrderBinding
    private var userId: Int = -1

    private val orderAdapter: OrderAdapter by lazy {
        OrderAdapter(onItemClick = { onItemClick(it) })
    }

    private val orderViewModel: OrderViewModel by lazy {
        ViewModelProvider(
            this,
            OrderViewModel.OrderViewModelFactory(requireActivity().application)
        )[OrderViewModel::class]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            userId = it.getInt("userId")
        }
        setUpRecyclerView()
        getOrderByUserId()
    }

    private fun setUpRecyclerView() {
        binding.rvOrder.adapter = orderAdapter
        binding.rvOrder.layoutManager = LinearLayoutManager(context)
    }

    private fun getOrderByUserId() {
        orderViewModel.getOrderByUserId(userId).observe(viewLifecycleOwner) {
            orderAdapter.submitList(it)

        }
    }

    private fun onItemClick(order: Order) {
        val bundle = Bundle()
        bundle.putInt("orderId", order.orderId)
        bundle.putInt("userId", userId)
        findNavController().navigate(R.id.action_userHomeFragment_to_orderDetailFragment, bundle)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clUserHome, fragment)
        fragmentTransaction.commit()
    }
}