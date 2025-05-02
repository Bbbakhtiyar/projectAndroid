package co.baha.fitnesstracker

import co.baha.fitnesstracker.model.Calc

interface OnListClickListener {
    fun OnClick(id: Int, type: String)
    fun OnLongClick(position: Int, calc: Calc)
}