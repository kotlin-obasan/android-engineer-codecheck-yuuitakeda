package jp.co.yumemi.android.code_check.data.source

import jp.co.yumemi.android.code_check.data.Resource
import jp.co.yumemi.android.code_check.data.dto.GitHubRepositories

interface RemoteDataSource {

    // GitHubリポジトリを検索
    suspend fun searchRepoositories(): Resource<GitHubRepositories>
}
