/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.presentation

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R

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
