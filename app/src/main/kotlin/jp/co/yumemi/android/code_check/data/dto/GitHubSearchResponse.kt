package jp.co.yumemi.android.code_check.data.dto

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * GitHubSearchResponse
 * 検索APIのレスポンスをまとめている
 */
@Keep
@JsonClass(generateAdapter = false) //難読化回避用にgenerateAdapter = falseに設定
data class GitHubSearchResponse(
    val items: List<GitHubRepositoryInfo>
)

@Keep
@JsonClass(generateAdapter = false)
data class GitHubRepositoryInfo(
    @Json(name = "full_name") val name: String,
    @Json(name = "owner") val owner: Owner,
    @Json(name = "language") val language: String?,
    @Json(name = "stargazers_count") val stargazersCount: Long,
    @Json(name = "watchers_count") val watchersCount: Long,
    @Json(name = "forks_count") val forksCount: Long,
    @Json(name = "open_issues_count") val openIssuesCount: Long,
    @Json(name = "html_url") val htmlUrl: String,
)

@Keep
@JsonClass(generateAdapter = false)
data class Owner(
    @Json(name = "avatar_url") val ownerIconUrl: String,
)