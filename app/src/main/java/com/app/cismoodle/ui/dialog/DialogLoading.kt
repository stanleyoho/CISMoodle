package com.app.cismoodle.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import com.app.cismoodle.R

class DialogLoading constructor(context: Context) : Dialog(context, R.style.loadingDialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_loading)

        setCanceledOnTouchOutside(false)

        setFullScreenDialog()
    }

    private fun setFullScreenDialog(){
        try {
            val metrics = DisplayMetrics()
            window!!.windowManager.defaultDisplay.getMetrics(metrics)

            val win = window
            win.decorView.setPadding(0, 0, 0, 0)
            val lp = win.attributes
            lp.width = metrics.widthPixels
            lp.height = WindowManager.LayoutParams.MATCH_PARENT
            //            lp.height = metrics.heightPixels - (int)ViewUtils.convertDpToPixel(24,dialog.getContext());
            win.attributes = lp

            win.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or
                    WindowManager.LayoutParams.SOFT_INPUT_MASK_STATE or
                    WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}