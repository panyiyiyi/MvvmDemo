package com.even.common.base

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.even.common.BR
import com.even.common.R
import com.even.common.bean.ErrorBean
import com.even.common.bean.TitleBarBean
import com.even.common.databinding.CommonTitleBarBinding
import com.even.common.impl.OnPermissionCallBack
import com.even.common.impl.OnPermissionCallBacks
import com.even.common.utils.ActivityManagerUtils
import com.even.common.utils.ToastUtils
import com.even.common.vm.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * Create by Even on 2020/8/25
 * Activity 基础类
 */
abstract class BaseActivity<VM : BaseViewModel>(@LayoutRes private val layoutId: Int) :
    AppCompatActivity() {

    private var permissionCallBacks: OnPermissionCallBacks? = null
    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val clazz =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        mViewModel = ViewModelProvider(this).get(clazz)
        val view = if (useDefaultTitleBar()) {
            //使用默认标题
            val linearLayout = LinearLayout(this)
            val titleBar = layoutInflater.inflate(titleBarId, null)
            linearLayout.addView(
                titleBar, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )
            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.addView(
                layoutInflater.inflate(layoutId, null), LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
            )
            val titleBind = DataBindingUtil.bind<CommonTitleBarBinding>(titleBar)
            titleBind?.lifecycleOwner = this
            titleBind?.setVariable(BR.baseViewModel, mViewModel)

            linearLayout
        } else {
            //不使用默认标题
            layoutInflater.inflate(layoutId, null)
        }
        setContentView(view)
        ActivityManagerUtils.addActivity(this)
        initView(view as ViewGroup)
        initData()
    }

    //回调
    private fun observeCallBack() {
        mViewModel.titleBackListener.set(View.OnClickListener { finish() })
        mViewModel.mIsShowLoading.observe(this, Observer {
            //加载框
            showLoading()
        })
        mViewModel.mErrorData.observe(this, Observer {
            showError(it)
        })

    }

    fun setTitleBar(titleBarBean: TitleBarBean) {
        mViewModel.titleBar.postValue(titleBarBean)
    }

    /**
     * 单个权限申请
     */
    @Synchronized
    open fun requestPermission(permission: String, callBack: OnPermissionCallBack) {
        requestPermissions(mutableListOf(permission), object : OnPermissionCallBacks {
            override fun onFailResult(denies: Array<String>) {
                callBack.onResult(false)
            }

            override fun onSuccessResult() {
                callBack.onResult(true)
            }
        })

    }

    /**
     * 多权限申请
     */
    @Synchronized
    open fun requestPermissions(
        permissions: MutableList<String>,
        callBacks: OnPermissionCallBacks
    ) {
        this.permissionCallBacks = callBacks
        var isRequest = false
        permissions.forEach {
            if (ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED) {
                //有一个没有通过就发起请求
                isRequest = true
            } else {
                permissions.remove(it)
            }
        }
        if (isRequest) {
            ActivityCompat.requestPermissions(
                this,
                permissions.toTypedArray(),
                REQ_PERMISSION_RECODE
            )
        } else {
            permissionCallBacks?.onSuccessResult()
        }
    }

    /**
     * 申请悬浮窗权限
     */
    open fun requestOverlays() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                intent.data = Uri.fromParts("package", this.packageName, null)
                startActivityForResult(intent, REQ_OVERLAY_PERMISSION)
            } else {
                resultOverlaysPermission(true)
            }
        } else {
            resultOverlaysPermission(true)
        }
    }

    /**
     * 申请悬浮窗权限结果
     * @param isAgree 是否同意
     */
    open fun resultOverlaysPermission(isAgree: Boolean) {

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (REQ_PERMISSION_RECODE == requestCode) {
            val deniedLists = mutableListOf<String>()

            for (index in grantResults.indices) {
                if (PackageManager.PERMISSION_DENIED == grantResults[index]) {
                    deniedLists.add(permissions[index])
                }
            }
            if (deniedLists.size > 0) {
                permissionCallBacks?.onFailResult(deniedLists.toTypedArray())
            } else {
                permissionCallBacks?.onSuccessResult()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_OVERLAY_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    resultOverlaysPermission(true)
                } else {
                    resultOverlaysPermission(false)
                }
            } else {
                resultOverlaysPermission(true)
            }
        }
    }

    //标题ID
    open var titleBarId: Int = R.layout.common_title_bar

    //初始化布局相关
    abstract fun initView(view: ViewGroup)

    //获取数据
    open fun initData() {}

    open fun showLoading() {

    }

    open fun showError(it: ErrorBean) {
        ToastUtils.showShort(it.error ?: "")
    }

    open fun useDefaultTitleBar(): Boolean = true

    override fun onDestroy() {
        super.onDestroy()
        permissionCallBacks = null
        ActivityManagerUtils.closeActivity(this)
    }


    companion object {
        //权限请求码
        const val REQ_PERMISSION_RECODE = 0x123

        //悬浮窗
        const val REQ_OVERLAY_PERMISSION = 0x124
    }
}