package com.example.shoesshopapp.ui.fragment.users.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoesshopapp.R
import com.example.shoesshopapp.databinding.FragmentOrderBinding
import com.example.shoesshopapp.model.dataTest.Order
import com.example.shoesshopapp.ui.fragment.users.order.oderDetail.OrderDetailFragment

class OrderFragment : Fragment() {

    private lateinit var binding: FragmentOrderBinding

    private val orderAdapter: OrderAdapter by lazy {
        OrderAdapter()
    }

    private val orderList = listOf(
        Order(
            1, "nda@gmail.com", "ODC-123456", 1200000.0, R.drawable.icon_order,
            "2023-10-01", "Pending"
        ),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding.rvOrder.adapter = orderAdapter
        binding.rvOrder.layoutManager = LinearLayoutManager(context)
        orderAdapter.submitList(orderList)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clUserHome, fragment)
        fragmentTransaction.commit()
    }
}