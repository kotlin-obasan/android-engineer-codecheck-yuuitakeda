package jp.co.yumemi.android.code_check.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import jp.co.yumemi.android.code_check.data.Resource
import jp.co.yumemi.android.code_check.data.dto.GitHubRepositoryInfo
import jp.co.yumemi.android.code_check.data.dto.GitHubSearchResponse
import jp.co.yumemi.android.code_check.data.dto.Owner
import jp.co.yumemi.android.code_check.data.repository.GitHubSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class GitHubSearchViewModelTest {

    private lateinit var testData: GitHubSearchResponse

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: GitHubSearchViewModel

    @MockK
    lateinit var  gitHubSearchRepository: GitHubSearchRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        gitHubSearchRepository = mockk()
        viewModel = GitHubSearchViewModel(gitHubSearchRepository)
        Dispatchers.setMain(Dispatchers.Unconfined)

// todo: APIレスポンス側の変更に対応するためjsonをrawで持ちたかった(上手く出来ませんでした)
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
                GitHubRepositoryInfo("kotlin-obasan/android-engineer-codecheck-yuuitakeda",
                Owner("https://avatars.githubusercontent.com/u/40195087?v=4"),
                    "Kotlin",
                    0L,
                    0L,
                    0L,
                    12,
                    "https://github.com/kotlin-obasan/android-engineer-codecheck-yuuitakeda"
                )
            )
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GitHub検索_正常系`() {
        val keyword = "yuuitakeda"
        val response = testData

        //モック
        coEvery { gitHubSearchRepository.search(keyword) } returns flow {
            emit(Resource.Success(testData))
        }

        //実際にコール
        viewModel.gitHubRepositories.observeForever{}
        viewModel.searchRepositories(keyword)

        //比較する
        when (val responseSuccess = viewModel.gitHubRepositories.value) {
            is Resource.Success ->  {
                responseSuccess.data?.let {
                    assertThat(it).isEqualTo(response)
                }
            }
        }
    }

    @Test
    fun `GitHub検索_異常系`() {
        val keyword = ""
        val response = HttpException(Response.error<ResponseBody>(500 ,ResponseBody.create("plain/text".toMediaType(),"some content")))

        //Mockz
        coEvery { gitHubSearchRepository.search(keyword) } returns flow {
            emit(Resource.DataError(response))
        }

        //実際にコールする
        viewModel.gitHubRepositories.observeForever{}
        viewModel.searchRepositories(keyword)

        //比較する
        when (val responseError = viewModel.gitHubRepositories.value) {
            is Resource.DataError ->  {
                assertThat(responseError.error).isEqualTo(response)
            }
        }
    }

    @Test
    fun `GitHub検索_異常系_256文字以上`() {
        val keyword = "NYRcjaLCXGwNcVgFJeCiKeisnumfrHUcyaCmHsQgPUEGxswVUYFREGxnrsuDrZYbeGzLpGhaLuruGWBARDyWsmBcgxLPcMjQXNDNbumDCBtyEmdBamymjsrmJXaexyRCmZaQHphfYeiwmgQgZcizaKrJpmAHfcHrgPYpTtnPwHACpLbdUccRDLbDLMzyAgUaxuUhKpwWGyMMHnyVRaahhSCMxiSsjHzwAtfLZgZsLuTwfPwabNTsrLhBDhzZFnhXeSDiCYfRkEuMUCPzWCBFxxfFceDzRAuSsfmQsZtDsFsRYkimHaXFzEAEjLYjwVWNymdjgByZhMNrGpPJHEazbufFRirtZxeQRaJQGzdmsGXXTDEfTZamiHMFwAgBmSYnTGWeARctKcuuFhfNUwhMCPgCiNbZZcdCcWjhnwZPWjkagXPPAUKjmCFwZtzmFRdHUDYdkMdMKPJspbyFxdWbBcgjizSfweSsRnWxJmCgCQPWNLSEFEYb\n"
        val response = HttpException(Response.error<ResponseBody>(500 ,ResponseBody.create("plain/text".toMediaType(),"some content")))

        //Mockz
        coEvery { gitHubSearchRepository.search(keyword) } returns flow {
            emit(Resource.DataError(response))
        }

        //実際にコールする
        viewModel.gitHubRepositories.observeForever{}
        viewModel.searchRepositories(keyword)

        //比較する
        when (val responseError = viewModel.gitHubRepositories.value) {
            is Resource.DataError ->  {
                assertThat(responseError.error).isEqualTo(response)
            }
        }
    }

    companion object {
        private const val PATH_DUMMY_DATA: String = "dummy_data_repository.json"
    }
}