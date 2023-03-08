package jp.co.yumemi.android.code_check.data.dto

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class RepositoryDescriptionData(
    val ownerIconUrl: String?,
    val name: String,
    val language: String?,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
    val htmlUrl: String,
) : Parcelable