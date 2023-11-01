package com.example.razorpaysample

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.razorpaysample.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.razorpay.Checkout
import com.razorpay.PayloadHelper
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject
import java.util.Random

class MainActivity : AppCompatActivity(), PaymentResultWithDataListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var razorpayPaymentReceiver: RazorpayPaymentReceiver
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Checkout.preload(applicationContext)
        razorpayPaymentReceiver=RazorpayPaymentReceiver()

        binding.buttonPay.setOnClickListener {
            validation()
        }

    }

    private fun validation() {
        if (binding.textInputEditText.text!!.isEmpty()) {
            Snackbar.make(this, binding.root, "please enter amount", 1000).show()
        } else {
            //paymentData()
            startPayment()
           // newPayment()
        }
    }

   /* private fun newPayment() {
        val checkout = Checkout()

        // Set your Razorpay API key
        checkout.setKeyID("rzp_test_fhFhMqVxTMGvBA")

        val options = JSONObject()
        options.put("name", "Muthu")
        options.put("description", "Payment for Order")
        options.put("currency", "INR") // You can change this to your preferred currency
        val payment: String =
            binding.textInputEditText.text.toString()  // amount value is double  // Amount in paise (e.g., 10000 paise = ₹100)
        var total = payment.toDouble()
        total *= 100
        options.put("amount", total)
        try {
            checkout.open(this, options)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }*/

    private fun paymentData() {

        val payloadHelper = PayloadHelper("INR", 100, "order_XXXXXXXXX")
        payloadHelper.name = "Gaurav Kumae"
        payloadHelper.description = "Description"
        payloadHelper.prefillEmail = "gaurav.kumar@example.com"
        payloadHelper.prefillContact = "9000090000"
        payloadHelper.prefillCardNum = "4111111111111111"
        payloadHelper.prefillCardCvv = "111"
        payloadHelper.prefillCardExp = "11/24"
        payloadHelper.prefillMethod = "card"
        payloadHelper.prefillName = "MerchantName"
        payloadHelper.sendSmsHash = true
        payloadHelper.retryMaxCount = 4
        payloadHelper.retryEnabled = true
        payloadHelper.color = "#000000"
        payloadHelper.allowRotation = true
        payloadHelper.rememberCustomer = true
        payloadHelper.timeout = 10
        payloadHelper.redirect = true
        payloadHelper.recurring = "1"
        payloadHelper.subscriptionCardChange = true
        payloadHelper.customerId = "cust_XXXXXXXXXX"
        payloadHelper.callbackUrl = "https://accepts-posts.request"
        payloadHelper.subscriptionId = "sub_XXXXXXXXXX"
        payloadHelper.modalConfirmClose = true
        payloadHelper.backDropColor = "#ffffff"
        payloadHelper.hideTopBar = true
        payloadHelper.notes = JSONObject("{\"remarks\":\"Discount to cusomter\"}")
        payloadHelper.readOnlyEmail = true
        payloadHelper.readOnlyContact = true
        payloadHelper.readOnlyName = true
        payloadHelper.image = "https://www.razorpay.com"
        // these values are set mandatorily during object initialization. Those values can be overridden like this
        payloadHelper.amount = 100
        payloadHelper.currency = "INR"
        payloadHelper.orderId = "order_XXXXXXXXXXXXXX"
    }

    private fun startPayment() {
        /*
        *  You need to pass the current activity to let Razorpay create CheckoutActivity
        * */
        val activity: Activity = this
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_9ZrP5XdjSYY7B9")
      /*  val client = Checkout("YOUR_MERCHANT_ID", "YOUR_KEY_ID")
        val razorpay = RazorpayClient("<api_key>", "<api_secret>")*/
        //        checkout.setImage(R.drawable.ic_launcher_foreground)

        try {
            val options = JSONObject()
            options.put("name", "Mutharasan")
            options.put("description", "Service Charges")
            //You can omit the image option to fetch the image from the dashboard
            options.put(
                "image",
                "https://images.pexels.com/photos/4450359/pexels-photo-4450359.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
            )
            options.put("theme.color", "#3399cc");
            val orderId=generateRandomOrderId()
            //options.put("order_id","order_xxxxxxxxxxx" );
            options.put("currency", "INR");
            options.put("order_id", "order_Mk5shu5EQyDk3G")
            // options.put("order_id", "order_DBJOWzybf0sJbb");  // order_id default value 5
            val payment: String =
                binding.textInputEditText.text.toString()  // amount value is double
            var total = payment.toDouble()
            total *= 100
            options.put("amount", total)//pass amount in currency subunits

            val retryObj = JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            val prefill = JSONObject()
            prefill.put("email", "km.arasankpk266@gmail.com")
            prefill.put("contact", " ")

            options.put("prefill", prefill)
            checkout.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }



    private fun generateRandomOrderId(): String {
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val random = Random()
        val orderId = StringBuilder()

        for (i in 0 until 10) {
            val randomIndex = random.nextInt(characters.length)
            orderId.append(characters[randomIndex])
        }

        return orderId.toString()
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?, paymentData: PaymentData?) {
        Log.e(TAG, "onPaymentSuccess:-> $razorpayPaymentId -> $paymentData ")
        binding.textInputEditText.text?.clear()
        Snackbar.make(this,binding.root,"Payment Success",1000).show()

    }

    override fun onPaymentError(errorCode: Int, response: String?, paymentData: PaymentData?) {
        Log.e(TAG, "onPaymentError:-> $errorCode -> $paymentData ->$response")
        val jsonObject=JSONObject(response)
        val reason=jsonObject.getJSONObject("error").getString("reason").replace("_"," ")
        Snackbar.make(this,binding.root,reason,1200).show()
        Log.e(TAG, "onPaymentError: $response")
        Log.e(TAG, "onPaymentError: $reason")
        val intent = Intent("com.razorpay.payment")
        intent.putExtra("response",response)
        intent.putExtra("paymentId",paymentData?.paymentId)
        sendBroadcast(intent)
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume: ")
        val intentFilter = IntentFilter("com.razorpay.payment")
        razorpayPaymentReceiver=RazorpayPaymentReceiver()
        registerReceiver(razorpayPaymentReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy: ")
        razorpayPaymentReceiver=RazorpayPaymentReceiver()
        unregisterReceiver(razorpayPaymentReceiver)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Check if the result is from Razorpay
        if (requestCode == Checkout.RZP_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val paymentId = data?.getStringExtra("razorpay_payment_id")
                val signature = data?.getStringExtra("razorpay_signature")

                // Payment was successful, handle it accordingly
                if (paymentId != null && signature != null) {
                    // Payment successful, you can now confirm it with your server
                    Log.e(TAG, "onActivityResult: PAYMENT_SUCCESS")

                }
            } else if (resultCode == Checkout.PAYMENT_CANCELED) {
                Log.e(TAG, "onActivityResult: PAYMENT_CANCELED")
                // Payment was canceled by the user
            } else {
                // Payment failed, handle the error
                Log.e(TAG, "onActivityResult: NETWORK_ERROR")

            }
        }
    }

    override fun onStop() {
        super.onStop()
        Log.e(TAG, "onStop: ")
    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG, "onPause: ")
    }

    override fun onStart() {
        super.onStart()
        Log.e(TAG, "onStart: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e(TAG, "onRestart: ")
    }

}