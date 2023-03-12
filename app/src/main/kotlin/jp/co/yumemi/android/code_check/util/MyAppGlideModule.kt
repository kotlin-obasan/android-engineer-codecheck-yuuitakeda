package jp.co.yumemi.android.code_check.util

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

/**
 * MyAppGlideModule
 * これを作らないとAppGlideを利用できない。
 * AppGlideはアイコンの丸抜き表示に使用。
 */
@GlideModule
class MyAppGlideModule : AppGlideModule(){

}