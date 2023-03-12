/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.presentation

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R

/**
 * TopActivity
 * このアプリの軸となるActivity
 * プログレスダイアログの表示・非表示を切り替えできる
 */
@AndroidEntryPoint
class TopActivity : AppCompatActivity(R.layout.activity_top) {

    private val progressDialog = ProgressDialog()

    fun showProgressDialog() {
        progressDialog.show(supportFragmentManager, "TAG")
    }

    fun hideProgressDialog() {
        progressDialog.dismiss()
    }
}
