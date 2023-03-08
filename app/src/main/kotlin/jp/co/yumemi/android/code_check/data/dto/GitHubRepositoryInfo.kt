package jp.co.yumemi.android.code_check.data.dto

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class GitHubRepositoryInfo(
    @SerialName("full_name") val name: String,
    @SerialName("owner") val owner: Owner,
    @SerialName("language") val language: String,
    @SerialName("stargazers_count") val stargazersCount: Long,
    @SerialName("watchers_count") val watchersCount: Long,
    @SerialName("forks_conut") val forksCount: Long,
    @SerialName("open_issues_count") val openIssuesCount: Long,
)

@Serializable
data class Owner(
    @SerialName("avatar_url") val ownerIconUrl: String,
)