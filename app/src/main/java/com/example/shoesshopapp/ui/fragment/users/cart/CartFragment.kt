package com.example.shoesshopapp.ui.fragment.users.cart

import android.app.AlertDialog
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
import com.example.shoesshopapp.databinding.FragmentCartBinding
import com.example.shoesshopapp.model.data.CartItem
import com.example.shoesshopapp.model.data.relationship.CartItemWithDetails
import com.example.shoesshopapp.model.data.relationship.CartWithCartItems
import com.example.shoesshopapp.model.dataTest.CartProduct
import com.example.shoesshopapp.ui.fragment.users.cart.thankyou.ThankYouFragment
import com.example.shoesshopapp.ui.fragment.users.dashboard.DashboardFragment


class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private var cartId: Int = -1

    private val cartViewModel: CartViewModel by lazy {
        ViewModelProvider(
            this,
            CartViewModel.CartViewModelFactory(requireActivity().application)
        )[CartViewModel::class.java]
    }

    private val cartItemAdapter: CartItemAdapter by lazy {
        CartItemAdapter(
            onQuantityIncrease = { onQuantityIncrease(it) },
            onQuantityDecrease = { onQuantityDecrease(it) },
            onDeleteClick = { onDeleteClick(it) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            cartId = it.getInt("cartId")
            Log.d("Receive cartId ", cartId.toString())
        }

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnCheckOut.setOnClickListener {
            showCheckoutConfirmation()
        }

        setupRecyclerView()
        getCartWithCartItems(cartId)

    }

    private fun setupRecyclerView() {
        binding.rvCart.adapter = cartItemAdapter
        binding.rvCart.layoutManager = LinearLayoutManager(context)
    }

    private fun getCartWithCartItems(cartId: Int) {
        cartViewModel.getCartWithCartItems(cartId)
            .observe(viewLifecycleOwner) { cartWithCartItems ->
                cartItemAdapter.submitList(cartWithCartItems.cartItems)
                updateTotal(cartWithCartItems)
            }
    }

    private fun showCheckoutConfirmation() {
        AlertDialog.Builder(requireContext())
            .setTitle("Check out cart")
            .setMessage("Are you sure you want to check out ?")
            .setPositiveButton("Yes") { _, _ ->
                replaceFragment(ThankYouFragment())
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateTotal(cartWithItems: CartWithCartItems) {
        val total = cartWithItems.cartItems.sumOf {
            it.product.price * it.cartItem.quantity
        }
        binding.tvTotalValue.text = String.format("%.0f", total)
    }

    private fun onQuantityIncrease(cartItem: CartItemWithDetails) {
        val updatedCartItem = CartItem(
            cartItemId = cartItem.cartItem.cartItemId,
            cartId = cartItem.cartItem.cartId,
            productId = cartItem.cartItem.productId,
            quantity = cartItem.cartItem.quantity + 1,
            productSizeId = cartItem.productSize.productSizeId
        )
        cartViewModel.updateCartItemQuantity2(updatedCartItem)
    }

//    private fun onQuantityIncrease2(cartItem: CartItemWithDetails) {
//        cartViewModel.updateCartItemQuantity(
//            cartItem.cartItem.cartId,
//            cartItem.cartItem.quantity + 1
//        )
//    }

    private fun onQuantityDecrease(cartItem: CartItemWithDetails) {
        val updatedCartItem = CartItem(
            cartItemId = cartItem.cartItem.cartItemId,
            cartId = cartItem.cartItem.cartId,
            productId = cartItem.cartItem.productId,
            quantity = cartItem.cartItem.quantity - 1,
            productSizeId = cartItem.productSize.productSizeId
        )
        cartViewModel.updateCartItemQuantity2(updatedCartItem)
    }

    private fun onDeleteClick(cartItem: CartItemWithDetails) {
        showDeleteCartItemConfirmation(cartItem.cartItem.cartItemId)
    }

    private fun showDeleteCartItemConfirmation(cartItemId: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete cart item")
            .setMessage("Are you sure you want to delete this item ?")
            .setPositiveButton("Yes") { _, _ ->
                cartViewModel.deleteCartItemById(cartItemId)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.clUserHome, fragment)
        fragmentTransaction.commit()
    }
}