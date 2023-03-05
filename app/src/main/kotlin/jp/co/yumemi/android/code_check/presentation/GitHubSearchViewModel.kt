/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.presentation

import android.content.Context
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
    @ApplicationContext
    private val context: Context,
    private val gitHubSearchRepository: GitHubSearchRepository
): ViewModel() {

    // API呼び出しが完了したかどうか見るFLG
    val isCompletedAPICall = MutableLiveData<Boolean>()

    // 入力された文字列でGitHubAPI内のリポジトリを検索する
    fun searchRepositories(keyword: String) {

        isCompletedAPICall.value = false

        // 新しいコルーチンをUIスレッド上に作成する
        viewModelScope.launch {
            val result = gitHubSearchRepository.search(keyword).collectLatest { result ->
                when(result) {
                    is Resource.Success -> {

                    }
                    is Resource.DataError -> {
                    }
                }

                isCompletedAPICall.value = true

            }
        }
    }

    fun searchResults(inputText: String): List<GitHubRepositoryInfo> = runBlocking {
        val client = HttpClient(Android)

        return@runBlocking GlobalScope.async {
            val response: HttpResponse = client?.get("https://api.github.com/search/repositories") {
                header("Accept", "application/vnd.github.v3+json")
                parameter("q", inputText)
            }

            val jsonBody = JSONObject(response.receive<String>())

            val jsonItems = jsonBody.optJSONArray("items")!!

            val gitHubRepositoryInfos = mutableListOf<GitHubRepositoryInfo>()

            // 検索結果をパースして描画用のリストに変換
//            for (i in 0 until jsonItems.length()) {
//                val jsonItem = jsonItems.optJSONObject(i)!!
//                val name = jsonItem.optString("full_name")
//                val ownerIconUrl = jsonItem.optJSONObject("owner")!!.optString("avatar_url")
//                val language = jsonItem.optString("language")
//                val stargazersCount = jsonItem.optLong("stargazers_count")
//                val watchersCount = jsonItem.optLong("watchers_count")
//                val forksCount = jsonItem.optLong("forks_conut")
//                val openIssuesCount = jsonItem.optLong("open_issues_count")
//
//                gitHubRepositoryInfos.add(
//                    GitHubRepositoryInfo(
//                        name = name,
//                        owner = ownerIconUrl,
//                        language = context.getString(R.string.written_language, language),
//                        stargazersCount = stargazersCount,
//                        watchersCount = watchersCount,
//                        forksCount = forksCount,
//                        openIssuesCount = openIssuesCount
//                    )
//                )
//            }

            lastSearchDate = Date()

            return@async gitHubRepositoryInfos.toList()
        }.await()
    }
}