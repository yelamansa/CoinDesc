package kz.yelamansa.coindesk.view.converter

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_converter.*
import kz.yelamansa.coindesk.App
import kz.yelamansa.coindesk.R
import kz.yelamansa.coindesk.presenter.ConverterPresenter
import javax.inject.Inject

class ConverterFragment:Fragment(), ConverterView {

    @Inject lateinit var presenter:ConverterPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_converter, container, false)
    }

    private var currencyListener:TextWatcher? = null
    private var btcListener:TextWatcher? = null
    private var position:Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity?.application as App).appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)

        presenter.attachView(this)
        ArrayAdapter.createFromResource(
            activity,
            R.array.currencies,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            currencySpinner.adapter = adapter
        }
        currencySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                if (currencyAmountEditText.text.isNotEmpty()||btcAmountEditText.text.isNotEmpty())
                convert(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        currencyListener = object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(s?.isNotEmpty() == true) convert(0) else changeEditTextValue("", btcAmountEditText, btcListener)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }
        currencyAmountEditText.addTextChangedListener(currencyListener)

        btcListener = object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(s?.isNotEmpty() == true) convert(1) else changeEditTextValue("", currencyAmountEditText, currencyListener)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }

        btcAmountEditText.addTextChangedListener(btcListener)
    }

    private fun convert(position:Int){
        this.position = position
        try {
            presenter.convert(
                when (position) {
                    0 -> currencyAmountEditText.text.toString().toDouble()
                    1 -> btcAmountEditText.text.toString().toDouble()
                    else -> 0.00
                },
                currencySpinner.selectedItem.toString(), position
            )
        }catch (e:NumberFormatException){
            e.printStackTrace()
        }
    }

    override fun updateCalculatedRates(convertedValue: Double, position: Int) {
        when(position){
            0 -> {
                changeEditTextValue(convertedValue.toString(), btcAmountEditText, btcListener)
            }
            1 ->{
                changeEditTextValue(convertedValue.toString(), currencyAmountEditText, currencyListener)
            }
        }
    }

    private fun changeEditTextValue(value:String, editText: EditText , textWatcher: TextWatcher?){
        editText.removeTextChangedListener(textWatcher)
        editText.setText(value)
        editText.addTextChangedListener(textWatcher)
    }

    override fun showError(error: String) {
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressBar?.visibility = View.VISIBLE

    }

    override fun hideLoading() {
        progressBar?.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}