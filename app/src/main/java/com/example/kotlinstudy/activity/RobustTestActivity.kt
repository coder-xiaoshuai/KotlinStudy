package com.example.kotlinstudy.activity

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import com.example.common.utils.ToastUtils
import com.example.common_ui.base.BaseActivity
import com.example.kotlinstudy.R
import com.example.kotlinstudy.robust.PatchManipulateImp
import com.meituan.robust.Patch
import com.meituan.robust.PatchExecutor
import com.meituan.robust.RobustCallBack
import com.meituan.robust.patch.RobustModify
import com.meituan.robust.patch.annotaion.Modify
import kotlinx.android.synthetic.main.activity_robust_test.*

class RobustTestActivity : BaseActivity() {

//    @Modify
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn_robust_test.setOnClickListener {
            Toast.makeText(this@RobustTestActivity, "错误是撒发大水发121312", Toast.LENGTH_SHORT).show()
        }

        btn_load_patch.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1)
            } else {
                //有权限
                //加载补丁包
                PatchExecutor(applicationContext, PatchManipulateImp(), object : RobustCallBack {
                    override fun onPatchApplied(result: Boolean, patch: Patch?) {
                        Log.i("robust", "onPatchApplied$result")
                    }

                    override fun onPatchListFetched(result: Boolean, isNet: Boolean, patches: MutableList<Patch>?) {
                        Log.i("robust", "onPatchListFetched$result")
                    }

                    override fun onPatchFetched(result: Boolean, isNet: Boolean, patch: Patch?) {
                        Log.i("robust", "onPatchFetched$result")
                    }

                    override fun logNotify(log: String?, where: String?) {
                        Log.i("robust", "logNotify$log")
                    }

                    override fun exceptionNotify(throwable: Throwable?, where: String?) {
                        Log.i("robust", "exceptionNotify${throwable.toString()}")
                    }
                }).start()
            }

        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_robust_test
    }
}