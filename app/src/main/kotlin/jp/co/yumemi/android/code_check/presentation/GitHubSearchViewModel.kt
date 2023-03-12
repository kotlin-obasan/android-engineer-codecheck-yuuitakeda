/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.data.Resource
import jp.co.yumemi.android.code_check.data.dto.GitHubSearchResponse
import jp.co.yumemi.android.code_check.data.repository.GitHubSearchRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

/**
 * GitHubSearchViewModel
 * GitHubSearchFragmentに描画する情報を保持するViewModel
 */
@HiltViewModel
class GitHubSearchViewModel @Inject constructor(
    private val gitHubSearchRepository: GitHubSearchRepository
): ViewModel() {

    val lastSearchDate = MutableLiveData<String>()

    val gitHubRepositories = MutableLiveData<Resource<GitHubSearchResponse>>()

    // 入力された文字列でGitHubAPI内のリポジトリを検索する
    fun searchRepositories(keyword: String) {

        // 新しいコルーチンをUIスレッド上に作成する
        viewModelScope.launch {
            gitHubSearchRepository.search(keyword).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.d("test", "${result.data.items.size} 件見つかりました")
                        gitHubRepositories.value = result
                        val currentTime = getCurrentTime()
                        lastSearchDate.value = currentTime
                    }
                    is Resource.DataError -> {
                        gitHubRepositories.value = result
                    }
                }
            }
        }
    }

    fun getCurrentTime(): String {
        // 現在時刻の取得
        val currentTime = LocalDateTime.now()

        // フォーマットの指定
        val dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")

        // 文字列の生成
        return currentTime.format(dtf)
    }
}