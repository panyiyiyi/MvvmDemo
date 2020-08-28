package com.even.common.views

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.even.common.BR
import com.even.common.R
import com.even.common.bean.RemindDialogBean
import com.even.common.constant.CommonBundleKey

/**
 * Create by Even on 2020/8/28
 *
 */
class RemindDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val remindBean = arguments?.getParcelable<RemindDialogBean>(CommonBundleKey.KEY_BEAN)!!

        val mBindView = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(activity),
            R.layout.item_remind_dialog, null, false
        )
        mBindView.lifecycleOwner = this
        mBindView.setVariable(BR.remindBean, remindBean)
        val builder = AlertDialog.Builder(activity)
        builder.setView(mBindView.root)
        val create = builder.create()
        create.setCanceledOnTouchOutside(remindBean.canCancelOutSide)

        return create
    }

    companion object {
        fun newInstance(remindBean: RemindDialogBean): RemindDialogFragment {
            val fragment = RemindDialogFragment()
            val bundle = Bundle()
            bundle.putParcelable(CommonBundleKey.KEY_BEAN, remindBean)
            fragment.arguments = bundle
            return fragment
        }
    }

}