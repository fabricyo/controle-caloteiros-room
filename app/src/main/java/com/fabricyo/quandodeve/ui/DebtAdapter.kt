package com.fabricyo.quandodeve.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fabricyo.quandodeve.data.Debt
import com.fabricyo.quandodeve.databinding.ItemDebtBinding

class DebtAdapter : ListAdapter<Debt, DebtAdapter.ViewHolder>(DiffCallback()) {

    var listenerEdit: (Debt) -> Unit = {}
    var listenerDetail: (Debt) -> Unit = {}
    var listenerAddDebt: (Debt) -> Unit = {}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDebtBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemDebtBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Debt){
            binding.tvName.text = item.nome
            binding.tvDebt.text = "R$ ${item.valorDebt}"
            binding.tvLastUpdate.text = item.ultimaMod
            binding.tbLastValor.text = "R$ ${item.valorUltimaMod}"
            binding.mcvContent.background.setTint(Color.parseColor(item.color))
            binding.mcvContent.setOnClickListener { listenerDetail(item) }
            binding.mcvContent.setOnLongClickListener {
                listenerEdit(item)
                true
            }
            binding.btnAddDebt.setOnClickListener{listenerAddDebt(item)}
        }
    }
} class DiffCallback : DiffUtil.ItemCallback<Debt>() {
    override fun areItemsTheSame(oldItem: Debt, newItem: Debt) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Debt, newItem: Debt) = oldItem.id == newItem.id
}