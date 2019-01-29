package br.ufal.edge.dialoglauncher

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


enum class IngenicoIntentActions(val action: String) {
    PAYMENT("ingenico.intent.action.OPEN_PAYMENT_DIALOG"),
    TRANSACTION("ingenico.intent.action.OPEN_TRANSACTION_DIALOG"),
    CANCELING("ingenico.intent.action.OPEN_CANCELING_DIALOG"),
    SELLER("ingenico.intent.action.OPEN_SELLER_DIALOG"),
    REPRINT("ingenico.intent.action.OPEN_REPRINT_DIALOG")
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        launch_button.setOnClickListener {
            val value = value_edit_text.text.toString()

            if (value.isNotEmpty() && !value.equals("0")) {
                val sendIntent = Intent()
                sendIntent.type = "text/plain"
                sendIntent.action = IngenicoIntentActions.PAYMENT.action
                sendIntent.putExtra(Intent.EXTRA_TEXT, value_edit_text.text.toString())
                println(radio_advanced_credit_opt.isChecked)
                sendIntent.putExtra("ADVANCED_CREDIT_OPTIONS", radio_advanced_credit_opt.isChecked)
                startActivity(sendIntent)

            } else {
                Toast.makeText(this, "O valor não pode ser vazio ou 0", Toast.LENGTH_SHORT).show()
            }
        }

        launch_transaction_dialog_button.setOnClickListener {
            launchDefaultAction(IngenicoIntentActions.TRANSACTION.action, "", tv_transaction_text.text.toString())
        }

        launch_cancel_dialog_button.setOnClickListener {
            val value = tv_cancel_transaction_code.text.toString()

            launchDefaultAction(IngenicoIntentActions.CANCELING.action, "", value)
        }

        launch_seller_dialog_button.setOnClickListener {
            launchDefaultAction(IngenicoIntentActions.SELLER.action, "", tv_seller_text.text.toString())
        }

        launch_reprint_dialog_button.setOnClickListener {
            launchDefaultAction(IngenicoIntentActions.REPRINT.action, "", tv_reprint_text.text.toString())
        }
    }

    @JvmOverloads
    fun launchDefaultAction(action: String, type: String = "", singleData: String = "") {
        val sendIntent = Intent()

        sendIntent.type = "text/plain"
        sendIntent.action = action

        if (type.isNotEmpty()) {
            sendIntent.type = type
        }

        if (singleData.isNotEmpty()) {
            sendIntent.putExtra(Intent.EXTRA_TEXT, singleData)
        }

        if (sendIntent.resolveActivity(packageManager) != null) {
            startActivity(sendIntent)
        }
    }
}
