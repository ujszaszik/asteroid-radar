package com.udacity.asteroidradar.ui.details

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.AsteroidDetailViewBinding
import kotlinx.android.synthetic.main.asteroid_detail_view.view.*

class AsteroidDetailView(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    private var binding: AsteroidDetailViewBinding
    private var attributes: TypedArray

    init {
        val inflater = LayoutInflater.from(context)
        binding = AsteroidDetailViewBinding.inflate(inflater, this, true)

        attributes = context.obtainStyledAttributes(attrs, R.styleable.AsteroidDetailView)
        setAttributeText()
        setAttributeValue()
        setHelpImage()
        attributes.recycle()
    }

    private fun setAttributeText() {
        binding.attributeText.text =
            attributes.getString(R.styleable.AsteroidDetailView_attributeName)
    }

    private fun setAttributeValue() {
        binding.attributeValueText.text =
            attributes.getString(R.styleable.AsteroidDetailView_attributeValue)
    }

    private fun setHelpImage() {
        val showHelpImage =
            attributes.getBoolean(R.styleable.AsteroidDetailView_showHelpImage, false)
        if (showHelpImage) {
            binding.helpImageView.setImageResource(R.drawable.ic_help_circle)
            binding.helpImageView.setOnClickListener { showDialog() }
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(context)
            .setMessage(context.getString(R.string.astronomical_unit_explanation))
            .setPositiveButton(
                context.getString(R.string.OK)
            ) { dialogInterface, _ -> dialogInterface.dismiss() }
            .show()
    }

}

@BindingAdapter("attributeValue")
fun AsteroidDetailView.attributeValue(attributeText: Any) {
    attributeValueText.text = attributeText.toString()
}