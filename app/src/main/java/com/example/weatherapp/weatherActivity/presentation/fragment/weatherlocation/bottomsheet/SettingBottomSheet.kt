package com.example.weatherapp.weatherActivity.presentation.fragment.weatherlocation.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RadioGroup
import androidx.core.view.isVisible
import com.example.weatherapp.R
import com.example.weatherapp.core.presentation.common.SharedPrefs
import com.example.weatherapp.core.presentation.utilities.Nav
import com.example.weatherapp.databinding.LayoutSettingBottomsheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingBottomSheet(
    private val itemLanguageAction: (String) -> Unit,
    private val itemUnitAction: (String) -> Unit

) : BottomSheetDialogFragment() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        LayoutSettingBottomsheetBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRadio()
        initView()
        addListenerOnView()
    }

    private fun setupRadio() {
        if (sharedPrefs.getLang().isNotEmpty()) {
            if (sharedPrefs.getLang() == Nav.Language.english) {
                binding.english.isChecked = true
            } else {
                binding.arabic.isChecked = true
            }
        }

        if (sharedPrefs.getUnit().isNotEmpty()) {
            if (sharedPrefs.getUnit() == Nav.Unit.Celsius) {
                binding.celsiusUnit.isChecked = true
            } else {
                binding.fahrenhUnit.isChecked = true
            }
        }
    }

    private fun initView(): BottomSheetDialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    private fun addListenerOnView() {

        binding.tvChooseLanguage.setOnClickListener {
            binding.layoutLanguage.isVisible = !binding.layoutLanguage.isVisible
        }
        binding.tvChooseTemp.setOnClickListener {
            binding.layoutUnits.isVisible = !binding.layoutUnits.isVisible
        }

        binding.radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.celsiusUnit -> {
                    itemUnitAction(Nav.Unit.Celsius)
                    dismiss()
                }
                R.id.fahrenhUnit -> {
                    itemUnitAction(Nav.Unit.Fahrenheit)
                    dismiss()
                }
            }
        })

        binding.radioLanguage.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.english -> {
                    itemLanguageAction(Nav.Language.english)
                    dismiss()
                }
                R.id.arabic -> {
                    itemLanguageAction(Nav.Language.arabic)
                    dismiss()
                }
            }
        })


    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }
}