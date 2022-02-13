package com.fabricyo.quandodeve.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.fabricyo.quandodeve.App
import com.fabricyo.quandodeve.R
import com.fabricyo.quandodeve.data.Debt
import com.fabricyo.quandodeve.databinding.ActivityEditDebtBinding
import com.fabricyo.quandodeve.databinding.ActivityMainBinding
import com.fabricyo.quandodeve.util.ImageUtil
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EditDebtActivity : AppCompatActivity() {
    object Extras {
        const val DEBT = "EXTRA_DEBT"
    }

    private val binding by lazy { ActivityEditDebtBinding.inflate(layoutInflater) }
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    private lateinit var debt: Debt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadDebtFromExtra()
        setListeners()
    }

    private fun setListeners() {
        binding.btnClose.setOnClickListener { finish() }
        //  https://www.datetimeformatter.com/how-to-format-date-time-in-kotlin/
        val now = LocalDateTime.now()
        val nowFormatted = now.format(DateTimeFormatter.ofPattern("dd/MM HH:mm"))

        binding.btnEdit.setOnClickListener {
            this.debt.nome = binding.tilName.editText?.text.toString()
            this.debt.valorUltimaMod =
                debt.valorDebt.plus(binding.tilDebt.editText?.text.toString().toInt())
            this.debt.valorDebt = binding.tilDebt.editText?.text.toString().toInt()
            this.debt.color = binding.tilColor.editText?.text.toString()
            this.debt.ultimaMod = nowFormatted
            mainViewModel.update(this.debt)
            val intent = Intent(this@EditDebtActivity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnDelete.setOnClickListener {
            mainViewModel.deleteById(debt.id)
            val intent = Intent(this@EditDebtActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadDebtFromExtra() {
        intent?.extras?.getParcelable<Debt>(Extras.DEBT)?.let { debt ->
            this.debt = debt
            binding.tilName.editText!!.setText(debt.nome)
            binding.tilDebt.editText!!.setText(debt.valorDebt.toString())
            setColorSelector()
        }
    }

    private fun setColorSelector(){
        val colors = ImageUtil.colors()
        val colorAdapter = ColorArrayAdapter(this, R.layout.item_colors, colors)
        binding.actvColor.setAdapter(colorAdapter)
        colorAdapter.listenerSet = { cor ->
            binding.actvColor.setText(cor, false)
            binding.actvColor.dismissDropDown()
        }
//        val colorsAdapter = ArrayAdapter(this, R.layout.dropdown_colors, colors)
//        binding.actvColor.setAdapter(colorsAdapter)
        binding.actvColor.setText(debt.color, false)

    }
}