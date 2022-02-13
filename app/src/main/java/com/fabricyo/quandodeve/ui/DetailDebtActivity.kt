package com.fabricyo.quandodeve.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fabricyo.quandodeve.data.Debt
import com.fabricyo.quandodeve.databinding.ActivityDetailDebtBinding
import com.fabricyo.quandodeve.util.ImageUtil

class DetailDebtActivity : AppCompatActivity() {
    object Extras {
        const val DEBT = "EXTRA_DEBT"
    }
    private val binding by lazy {ActivityDetailDebtBinding.inflate(layoutInflater)}
    private lateinit var debt: Debt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadDebtFromExtra()
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnClose.setOnClickListener { finish() }
        binding.btnEdit.setOnClickListener {
            val intent = Intent(this@DetailDebtActivity, EditDebtActivity::class.java)
            intent.putExtra(EditDebtActivity.Extras.DEBT, debt)
            startActivity(intent)
        }
        binding.btnShare.setOnClickListener {
            ImageUtil.share(this@DetailDebtActivity, binding.mcvContent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadDebtFromExtra() {
        intent?.extras?.getParcelable<Debt>(Extras.DEBT)?.let { debt ->
            this.debt = debt
            binding.tvName.text = debt.nome
            binding.tvDebt.text = "R$ ${debt.valorDebt}"
            binding.tvLastUpdate.text = debt.ultimaMod
            binding.tbLastValor.text = "R$ ${debt.valorUltimaMod}"
            binding.mcvContent.background.setTint(Color.parseColor(debt.color))
        }
    }
}