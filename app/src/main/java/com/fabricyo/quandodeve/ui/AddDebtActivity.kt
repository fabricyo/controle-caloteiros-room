package com.fabricyo.quandodeve.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.fabricyo.quandodeve.App
import com.fabricyo.quandodeve.data.Debt
import com.fabricyo.quandodeve.databinding.ActivityAddDebtBinding
import com.fabricyo.quandodeve.util.ImageUtil
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddDebtActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddDebtBinding.inflate(layoutInflater) }
    private val mainViewModel: MainViewModel by viewModels{
        MainViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        insertListeners()
    }

    private fun insertListeners() {
        binding.btnClose.setOnClickListener { finish() }
        val colors = ImageUtil.colors()

        //  https://www.datetimeformatter.com/how-to-format-date-time-in-kotlin/
        val now = LocalDateTime.now()
        val nowFormatted = now.format(DateTimeFormatter.ofPattern("dd/MM HH:mm"))

        binding.btnConfirm.setOnClickListener {
            val debt = Debt(
                nome = binding.tilName.editText?.text.toString(),
                valorDebt = binding.tilDebt.editText?.text.toString().toInt(),
                valorUltimaMod = binding.tilDebt.editText?.text.toString().toInt(),
                color = colors[(0..colors.size).random()],
                ultimaMod = nowFormatted
            )
            mainViewModel.insert(debt)
            finish()
        }
    }
}
