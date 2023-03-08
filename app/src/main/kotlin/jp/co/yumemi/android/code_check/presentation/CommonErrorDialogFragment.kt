package jp.co.yumemi.android.code_check.presentation

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.DialogCommonErrorBinding

class CommonErrorDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflate = requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val binding = DialogCommonErrorBinding.inflate(inflate, null)

        binding.buttonClose.setOnClickListener {
            findNavController().navigateUp()
        }

        this.isCancelable = false
        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()
    }

    override fun onResume() {
        super.onResume()

        // onResumeじゃないとサイズ変更きかない（背景透明にするやつはここじゃなくてもいい）
        dialog?.window?.let {
            val metrics = resources.displayMetrics
            val dialogWidth = (metrics.widthPixels * 0.7).toInt()
            val lp = it.attributes
            lp?.width = dialogWidth
            it.attributes = lp
            // 角丸にするために背景透明を動的セット
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    //画面が閉じられたら消す
    override fun onPause() {
        super.onPause()
        findNavController().navigateUp()
    }
}
