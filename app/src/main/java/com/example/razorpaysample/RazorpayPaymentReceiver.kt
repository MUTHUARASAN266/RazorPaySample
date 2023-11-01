package com.example.razorpaysample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class RazorpayPaymentReceiver : BroadcastReceiver() {
    val TAG = "RazorpayPaymentReceiver"
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "com.razorpay.payment") {
            val status = intent.getStringExtra("status")
            val response = intent.getStringExtra("response")
            val orderId = intent.getStringExtra("orderId")
            val paymentId = intent.getStringExtra("paymentId")

            if (status == "completed") {
                // Payment was successful
                Log.e(TAG, "Payment for Order $paymentId successful!")

            } else if (response == "payment cancelled"){
                // Payment was successful
                Log.e(TAG, "Payment for Order $paymentId was canceled.")

            }else {
                // Payment failed or was canceled
                Log.e(TAG, "Payment for Order $paymentId failed.")

            }
        }
    }
}