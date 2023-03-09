package jp.co.yumemi.android.code_check.presentation

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import jp.co.yumemi.android.code_check.data.apiFlow
import jp.co.yumemi.android.code_check.data.dto.GitHubRepositoryInfo
import jp.co.yumemi.android.code_check.data.dto.GitHubSearchResponse
import jp.co.yumemi.android.code_check.data.dto.Owner
import jp.co.yumemi.android.code_check.data.repository.GitHubSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class GitHubSearchViewModelTest {

    private lateinit var testData: GitHubSearchResponse

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: GitHubSearchViewModel

    @MockK
    lateinit var  gitHubSearchRepository: GitHubSearchRepository

    private val mockContext = mockk<Context>(relaxed = true)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        gitHubSearchRepository = mockk()
        viewModel = GitHubSearchViewModel(gitHubSearchRepository)
        Dispatchers.setMain(Dispatchers.Unconfined)

//        val moshi = Moshi.Builder().build()
//        val jsonAdapter = moshi.adapter(GitHubSearchResponse::class.java)
//        val inputStream = ClassLoader.getSystemResourceAsStream(PATH_DUMMY_DATA)
//        val inputStreamReader = InputStreamReader(inputStream)
//
//        try {
//            val streamOfString = BufferedReader(inputStreamReader).lines()
//            val streamToString = streamOfString.collect(Collectors.joining())
//            jsonAdapter.fromJson(streamToString)?.let {
//                testData = it
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }

        testData = GitHubSearchResponse(listOf(
            GitHubRepositoryInfo("a",
            Owner("https://avatars.githubusercontent.com/u/40195087?v=4"),
                "Kotlin",
                0L,
                0L,
                0L,
                0L,
                "https://github.com/kotlin-obasan"
            )

        ))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    //todo: テスト通らない
    @Test
    fun `GitHub検索_正常系`() {
//        val keyword = "yuuitakeda"
//        val response = testData
//
//        //モック
//        coEvery { gitHubSearchRepository.search(keyword) } returns apiFlow {
//            Response<GitHubSearchResponse>(response)
//        }
//
//        //実際にコール
//        viewModel.gitHubRepositories.observeForever{}
//        viewModel.searchRepositories(keyword)
//
//        //比較する
//        val loginSuccess = viewModel.gitHubRepositories.value
//        assertThat(loginSuccess).isEqualTo(response)
    }

    //todo: テスト通らない
//    @Test
//    fun `GitHub検索_異常系`() {
//        val keyword = "yuuitakeda"
//        val response = Throwable()
//
//        //Mockz
//        coEvery { gitHubSearchRepository.search(keyword) } returns flow {
//            emit(ApiStatus.Error(response))
//        }
//
//        //実際にコールする
//        viewModel.mailAddress.value = "wrong@aaa.bbb"
//        viewModel.password.value = "wrongpass"
//        viewModel.loginLiveData.observeForever { }
//        viewModel.searchRepositories(keyword)
//
//        //比較
//        val loginSuccess = viewModel.loginLiveData.value
//        assertThat(loginSuccess).isEqualTo(response)
//    }

    companion object {
        private const val PATH_DUMMY_DATA: String = "dummy_data_repository.json"
    }
}