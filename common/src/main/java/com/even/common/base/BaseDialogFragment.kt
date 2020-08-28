package com.even.common.base

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

/**
 * Create by Even on 2020/8/26
 * 通用弹窗布局
 */
class BaseDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("hhh")
        builder.setMessage("asdfadsf")
        val string = arguments?.getString("key")
        builder.setMessage(string)
        return builder.create()
    }

    companion object {
        fun newInstance(): BaseDialogFragment {
            val fragment = BaseDialogFragment()
            val bundle = Bundle()
            bundle.putString("key", "6666")
            fragment.arguments = bundle
            return fragment
        }
    }

}