package com.example.shoesshopapp.ui.fragment.admin.order.orderManagerDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentOrderManagerDetailBinding
import com.example.shoesshopapp.model.data.Order
import com.example.shoesshopapp.model.data.OrderStatus
import com.example.shoesshopapp.model.data.relationship.OrderWithOrderItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderManagerDetailFragment : Fragment() {

    private lateinit var binding: FragmentOrderManagerDetailBinding
    private var orderId: Int = -1
    private var userId: Int = -1

    private val orderManagerDetailViewModel: OrderManagerDetailViewModel by lazy {
        ViewModelProvider(
            this,
            OrderManagerDetailViewModel.OrderManagerDetailViewModelFactory(requireActivity().application)
        )[OrderManagerDetailViewModel::class]
    }

    private val orderManagerDetailAdapter: OrderManagerDetailAdapter by lazy {
        OrderManagerDetailAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderManagerDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            orderId = it.getInt("orderId")
            userId = it.getInt("userId")
        }

        getOrderWithOrderItem()
        getUser()
        setupOrderItemAdapter()

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getOrderWithOrderItem() {
        orderManagerDetailViewModel.getOrderWithOrderItem(orderId)
            .observe(viewLifecycleOwner) { orderWithOrderItem ->

                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val formattedDate = dateFormat.format(Date(orderWithOrderItem.order.orderDate))

                val orderStatus = OrderStatus.entries.toTypedArray()

                binding.tvOrderCodeValue.text = orderWithOrderItem.order.orderCode
                binding.tvTimeCreateValue.text = formattedDate
                binding
                binding.tvTotalValue.text =
                    String.format("%.0f", orderWithOrderItem.order.totalCost)

                orderManagerDetailAdapter.submitList(orderWithOrderItem.orderItems)

                val orderStatusAdapter = ArrayAdapter(
                    this.requireContext(),
                    android.R.layout.simple_spinner_item,
                    orderStatus.map { orderStatus -> orderStatus.toString() }
                )

                orderStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerStatus.adapter = orderStatusAdapter

                val currentStatusPosition =
                    orderStatus.indexOf(orderWithOrderItem.order.orderStatus)
                binding.spinnerStatus.setSelection(currentStatusPosition)

                var isInitialSelection = true

                binding.spinnerStatus.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            if (isInitialSelection) {
                                isInitialSelection = false
                                return
                            }

                            val selectedStatus = orderStatus[position]
                            if (selectedStatus != orderWithOrderItem.order.orderStatus) {
                                val updatedOrder = orderWithOrderItem.order.copy(
                                    orderStatus = selectedStatus
                                )

                                // Update the order in the database
                                orderManagerDetailViewModel.updateOrder(updatedOrder)

                                Toast.makeText(
                                    requireContext(),
                                    "Order status updated to $selectedStatus",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                        }

                    }
            }
    }

    private fun getUser() {
        orderManagerDetailViewModel.getUserById(userId).observe(viewLifecycleOwner) { user ->
            binding.tvUsernameValue.text = user.fullName
            binding.tvUserAddressValue.text = user.address
            binding.tvUserPhoneValue.text = user.phone
        }
    }

    private fun setupOrderItemAdapter() {
        binding.rvOrderDetail.adapter = orderManagerDetailAdapter
        binding.rvOrderDetail.layoutManager = LinearLayoutManager(context)
    }
}