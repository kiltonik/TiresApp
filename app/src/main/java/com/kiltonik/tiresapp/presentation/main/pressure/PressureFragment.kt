package com.kiltonik.tiresapp.presentation.main.pressure

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.kiltonik.tiresapp.databinding.FragmentPressureBinding
import com.kiltonik.tiresapp.databinding.FragmentRegisterBinding
import com.kiltonik.tiresapp.presentation.base.BaseFragment
import com.kiltonik.tiresapp.presentation.main.MainActivity
import kotlin.math.abs
import kotlin.random.Random

class PressureFragment : BaseFragment<
        PressureViewState,
        PressureAction,
        PressureViewModel,
        FragmentPressureBinding
        >(), AdapterView.OnItemSelectedListener {

    private var weatherConditions = arrayOf("Солнце", "Снег", "Дождь")

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?) =
            FragmentPressureBinding.inflate(
            inflater,
            container,
            false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.firstTire.currentPressure.text = "1"
        binding.firstTire.recommendedPressure.text = "2"

        binding.secondTire.currentPressure.text = "1"
        binding.secondTire.recommendedPressure.text = "2"

        binding.thirdTire.currentPressure.text = "1"
        binding.thirdTire.recommendedPressure.text = "2"

        binding.fourthTire.currentPressure.text = "1"
        binding.fourthTire.recommendedPressure.text = "2"

        binding.weatherConditions.adapter = context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_dropdown_item_1line, weatherConditions)
        }

        binding.weatherConditions.onItemSelectedListener = this
        binding.temperature.doAfterTextChanged {

            if(binding.temperature.text.isNotEmpty())
                changeText(binding.firstTire.recommendedPressure.text.toString().toDouble() +
                        binding.temperature.text.toString().toDouble() / 50)
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        setPressureData()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun changeText(i : Double){
        binding.firstTire.recommendedPressure.text = i.toString()
        binding.secondTire.recommendedPressure.text = i.toString()
        binding.thirdTire.recommendedPressure.text = i.toString()
        binding.fourthTire.recommendedPressure.text = i.toString()

        if(abs(1 - i) > 0.3){
            context?.resources?.let {
                binding.firstTire.currentPressure.setTextColor(it.getColor(
                    android.R.color.holo_red_dark)) }

            context?.resources?.let {
                binding.secondTire.currentPressure.setTextColor(it.getColor(
                    android.R.color.holo_red_dark)) }

            context?.resources?.let {
                binding.thirdTire.currentPressure.setTextColor(it.getColor(
                    android.R.color.holo_red_dark)) }

            context?.resources?.let {
                binding.fourthTire.currentPressure.setTextColor(it.getColor(
                    android.R.color.holo_red_dark)) }
        }
        else{
            context?.resources?.let {
                binding.firstTire.currentPressure.setTextColor(it.getColor(
                    android.R.color.black)) }

            context?.resources?.let {
                binding.secondTire.currentPressure.setTextColor(it.getColor(
                    android.R.color.black)) }

            context?.resources?.let {
                binding.thirdTire.currentPressure.setTextColor(it.getColor(
                    android.R.color.black)) }

            context?.resources?.let {
                binding.fourthTire.currentPressure.setTextColor(it.getColor(
                    android.R.color.black)) }

        }
    }

    private fun setPressureData() = changeText(Random.nextDouble(0.3, 2.0))


    override fun render(viewState: PressureViewState) {
        TODO("Not yet implemented")
    }
}