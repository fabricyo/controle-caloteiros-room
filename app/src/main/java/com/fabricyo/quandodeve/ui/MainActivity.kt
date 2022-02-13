package com.fabricyo.quandodeve.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import com.fabricyo.quandodeve.App
import com.fabricyo.quandodeve.R
import com.fabricyo.quandodeve.data.Debt
import com.fabricyo.quandodeve.databinding.ActivityMainBinding
import com.fabricyo.quandodeve.databinding.InputDebtDialogBinding
import com.fabricyo.quandodeve.databinding.ItemDebtBinding
import com.fabricyo.quandodeve.util.ImageUtil
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }
    private val adapter by lazy { DebtAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.rvDebts.adapter = adapter
        getAllDebts()
        insertListeners()
    }


    private fun insertListeners() {
        binding.fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddDebtActivity::class.java)
            startActivity(intent)
        }
        adapter.listenerAddDebt = { debt ->
            showdialog(debt)
        }
        adapter.listenerEdit = { debt ->
            val intent = Intent(this@MainActivity, EditDebtActivity::class.java)
            intent.putExtra(EditDebtActivity.Extras.DEBT, debt)
            startActivity(intent)
        }
        adapter.listenerDetail = {debt ->
            val intent = Intent(this@MainActivity, DetailDebtActivity::class.java)
            intent.putExtra(DetailDebtActivity.Extras.DEBT, debt)
            startActivity(intent)
        }
    }

    private fun getAllDebts() {
        mainViewModel.getAll().observe(this) { debts ->
            adapter.submitList(debts)
        }
    }

    private fun showdialog(debt: Debt) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Adicionar débito")
        val inputBinding = InputDebtDialogBinding.inflate(layoutInflater)
        builder.setView(inputBinding.root)

        // Set up the buttons
        builder.setPositiveButton("OK") { dialog, _ ->
            if (inputBinding.tilDebtDialog.editText?.text.toString().isNotEmpty()) {
                try {
                    val valor = inputBinding.tilDebtDialog.editText?.text.toString().toInt()
                    val now = LocalDateTime.now()
                    val nowFormatted = now.format(DateTimeFormatter.ofPattern("dd/MM HH:mm"))
                    debt.ultimaMod = nowFormatted
                    debt.valorUltimaMod = valor
                    debt.valorDebt = debt.valorDebt.plus(valor)
                    mainViewModel.update(debt)
                    //Por algum motivo o observer só funciona quando quer, logo tenho que chamar notify
                    adapter.notifyItemRangeChanged(0, adapter.itemCount)
                    Snackbar.make(binding.fab, "Débito modificado", Snackbar.LENGTH_LONG).show()
                } catch (e: Exception) {
                    Snackbar.make(binding.fab, "Ocorreu um erro", Snackbar.LENGTH_LONG).show()
                } finally {
                    dialog.dismiss()
                }
            }
        }
        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.cancel() }
        val dialog: AlertDialog = builder.create()
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        dialog.show()
        inputBinding.tilDebtDialog.requestFocusFromTouch()
    }

}
