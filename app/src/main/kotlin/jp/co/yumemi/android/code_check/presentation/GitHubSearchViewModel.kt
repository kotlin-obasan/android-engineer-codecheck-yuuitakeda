/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.Resource
import jp.co.yumemi.android.code_check.data.dto.GitHubRepositoryInfo
import jp.co.yumemi.android.code_check.data.dto.GitHubSearchResponse
import jp.co.yumemi.android.code_check.data.repository.GitHubSearchRepository
import jp.co.yumemi.android.code_check.presentation.TopActivity.Companion.lastSearchDate
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import org.json.JSONObject
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

    // API呼び出しが完了したかどうか見るFLG
    val isCompletedAPICall = MutableLiveData<Boolean>()

    val gitHubRepositories = MutableLiveData<Resource<GitHubSearchResponse>>()

    // 入力された文字列でGitHubAPI内のリポジトリを検索する
    fun searchRepositories(keyword: String) {

        isCompletedAPICall.value = false

        // 新しいコルーチンをUIスレッド上に作成する
        viewModelScope.launch {
            gitHubSearchRepository.search(keyword).collectLatest { result ->
                when(result) {
                    is Resource.Success -> {
                        Log.d("test", "${result.data?.items?.size} 件見つかりました")
                        gitHubRepositories.value = result
                    }
                    is Resource.DataError -> {
                        // エラーハンドリングを行う
                    }
                }

                isCompletedAPICall.value = true

            }
        }
    }
}