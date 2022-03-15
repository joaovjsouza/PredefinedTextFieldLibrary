package com.goodluck.predefined_text_field

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.goodluck.predefined_text_field.databinding.ResPredefinedTextFieldBinding
import com.google.android.material.textfield.TextInputLayout

class PredefinedTextField @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs) {

    private val binding =
        ResPredefinedTextFieldBinding.inflate(LayoutInflater.from(context), this, true)

    private var hintText: String = ""
    private var helperText: String = ""
    private var errorText: String = ""
    var text: String = ""
        set(value) {
            field = value
            binding.editText.setText(field)
        }
        get() = binding.editText.text.toString()

    init {
        setLayout(attrs)
    }

    private fun setLayout(attrs: AttributeSet?) {
        attrs?.let {
            val attributes = context.obtainStyledAttributes(it, R.styleable.PredefinedTextField)

            val hintTextAttrs = attributes.getString(R.styleable.PredefinedTextField_hint_text)
            val helperTextAttrs = attributes.getString(R.styleable.PredefinedTextField_helper_text)
            val errorTextAttrs = attributes.getString(R.styleable.PredefinedTextField_error_text)
            val predefinedStyleAttrs =
                attributes.getInteger(R.styleable.PredefinedTextField_predefined_style, -1000)

            if (!hintTextAttrs.isNullOrEmpty()) hintText = hintTextAttrs
            if (!helperTextAttrs.isNullOrEmpty()) helperText = helperTextAttrs
            if (!errorTextAttrs.isNullOrEmpty()) errorText = errorTextAttrs

            if (predefinedStyleAttrs != -1000) {
                when (predefinedStyleAttrs) {
                    0 -> setupPersonPredefinedStyle()
                    1 -> setupEmailPredefinedStyle()
                    2 -> setupPasswordPredefinedStyle()
                    3 -> setupSearchPredefinedStyle()
                    4 -> setupImageLinkPredefinedStyle()
                }
            }

            attributes.recycle()
        }
    }

    private fun getIcon(id: Int): Drawable? = context.getDrawable(id)
    private fun getString(id: Int): String = context.getString(id)

    private fun hintTextChooser(value: String): String {
        if (hintText.isEmpty()) {
            return value
        }
        return hintText
    }

    fun showHelperText() {
        binding.layout.helperText = helperText
    }

    fun showHelperText(value: String) {
        binding.layout.helperText = value
    }

    fun showErrorText() {
        binding.layout.error = errorText
    }

    fun showErrorText(value: String) {
        binding.layout.error = value
    }

    fun hideHelperText() {
        binding.layout.helperText = ""
    }


    fun hideErrorText() {
        binding.layout.error = ""
    }

    fun endIconSetOnClickListener(value: () -> Unit) =
        binding.layout.setEndIconOnClickListener { value() }

    private fun setupPersonPredefinedStyle() {
        binding.layout.run {
            startIconDrawable = getIcon(R.drawable.ic_round_person_24)
            endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
            hint = hintTextChooser(getString(R.string.person_hint))
        }
    }

    private fun setupEmailPredefinedStyle() {
        binding.layout.run {
            startIconDrawable = getIcon(R.drawable.ic_round_email_24)
            endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
            hint = hintTextChooser(getString(R.string.email_hint))
        }
    }

    private fun setupPasswordPredefinedStyle() {
        binding.layout.run {
            startIconDrawable = getIcon(R.drawable.ic_round_lock_24)
            endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
            hint = hintTextChooser(getString(R.string.password_hint))
        }
        binding.editText.transformationMethod = PasswordTransformationMethod()
    }

    private fun setupSearchPredefinedStyle() {
        binding.layout.run {
            startIconDrawable = getIcon(R.drawable.ic_round_search_24)
            hint = hintTextChooser(getString(R.string.search_hint))
        }
    }

    private fun setupImageLinkPredefinedStyle() {
        binding.layout.run {
            startIconDrawable = getIcon(R.drawable.ic_round_image_search_24)
            endIconMode = TextInputLayout.END_ICON_CUSTOM
            endIconDrawable = getIcon(R.drawable.ic_round_refresh_24)
            hint = hintTextChooser(getString(R.string.image_link_hint))
        }
    }
}