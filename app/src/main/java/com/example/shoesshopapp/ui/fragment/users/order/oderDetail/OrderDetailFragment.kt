package com.example.shoesshopapp.ui.fragment.users.order.oderDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentOrderDetailBinding
import com.example.shoesshopapp.ui.fragment.users.order.OrderFragment
import com.example.shoesshopapp.ui.fragment.users.order.OrderViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderDetailFragment : Fragment() {

    private lateinit var binding: FragmentOrderDetailBinding
    private var orderId: Int = -1
    private var userId: Int = -1

    private val orderDetailViewModel: OrderDetailViewModel by lazy {
        ViewModelProvider(
            this,
            OrderDetailViewModel.OrderDetailViewModelFactory(requireActivity().application)
        )[OrderDetailViewModel::class]
    }

    private val orderDetailAdapter: OrderDetailAdapter by lazy {
        OrderDetailAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentOrderDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            orderId = it.getInt("orderId")
            userId = it.getInt("userId")
            Log.d("OrderDetailFragment", "userId: $userId")
        }

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        getOrderWithOrderItem()
        getUser()
        setupOrderItemAdapter()
    }

    private fun getOrderWithOrderItem() {
        orderDetailViewModel.getOrderWithOrderItem(orderId)
            .observe(viewLifecycleOwner) { orderWithOrderItem ->

                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val formattedDate = dateFormat.format(Date(orderWithOrderItem.order.orderDate))

                binding.tvOrderCodeValue.text = orderWithOrderItem.order.orderCode
                binding.tvTimeCreateValue.text = formattedDate
                binding
                binding.tvTotalValue.text =
                    String.format("%.0f", orderWithOrderItem.order.totalCost)
                binding.tvOrderCodeValue.text = orderWithOrderItem.order.orderStatus.toString()
                orderDetailAdapter.submitList(orderWithOrderItem.orderItems)
            }
    }

    private fun getUser() {
        orderDetailViewModel.getUserById(userId).observe(viewLifecycleOwner) { user ->
            binding.tvUsernameValue.text = user.fullName
            binding.tvUserAddressValue.text = user.address
            binding.tvUserPhoneValue.text = user.phone
        }
    }

    private fun setupOrderItemAdapter() {
        binding.rvOrderDetail.adapter = orderDetailAdapter
        binding.rvOrderDetail.layoutManager = LinearLayoutManager(context)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clUserHome, fragment)
        fragmentTransaction.commit()
    }
}