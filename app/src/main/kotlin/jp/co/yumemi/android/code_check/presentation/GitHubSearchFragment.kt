/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.data.Resource
import jp.co.yumemi.android.code_check.data.dto.GitHubRepositoryInfo
import jp.co.yumemi.android.code_check.data.dto.RepositoryDescriptionData
import jp.co.yumemi.android.code_check.databinding.FragmentGithubSearchBinding
import jp.co.yumemi.android.code_check.extension.hideKeyboard
import jp.co.yumemi.android.code_check.presentation.GitHubSearchViewModel
import jp.co.yumemi.android.code_check.presentation.TopActivity
import jp.co.yumemi.android.code_check.util.GlideApp

/**
 * GitHubSearchFragment
 * GitHubリポジトリを検索しに行く画面
 */
@AndroidEntryPoint
class GitHubSearchFragment: Fragment(R.layout.fragment_github_search) {

    private var fragmentGithubSearchBinding: FragmentGithubSearchBinding? = null
    private val viewModel by viewModels<GitHubSearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentGithubSearchBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        fragmentGithubSearchBinding = binding

        val layoutManager = LinearLayoutManager(requireContext())

        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), layoutManager.orientation)

        val adapter = CustomAdapter(object : CustomAdapter.OnItemClickListener {
            override fun itemClick(item: GitHubRepositoryInfo) {
                gotoGitHubDiscriptionFragment(item)
            }
        })

        binding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    editText.text.toString().let {
                        hideKeyboard()
                        (requireActivity() as TopActivity).showProgressDialog()

                        // GitHubリポジトリ検索
                        viewModel.searchRepositories(it)

                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        binding.recyclerView.also {
            it.layoutManager = layoutManager
            it.addItemDecoration(dividerItemDecoration)
            it.adapter = adapter
        }

        viewModel.gitHubRepositories.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    (requireActivity() as TopActivity).showProgressDialog()
                }
                is Resource.Success -> {
                    (requireActivity() as TopActivity).hideProgressDialog()
                    it.data.let { response ->
                        Log.d("response", response.toString())
                        adapter.submitList(response.items)
                    }
                }
                is Resource.DataError -> {
                    (requireActivity() as TopActivity).hideProgressDialog()
                    showErrorDialog()
                }
            }
        }
    }

    private fun translateRepositoryInfo(item: GitHubRepositoryInfo) : RepositoryDescriptionData? {
        Log.d("GitHubRepositoryInfo" , item.toString())
        viewModel.lastSearchDate.value?.let {
            return RepositoryDescriptionData(
                item.owner.ownerIconUrl,
                item.name,
                item.language,
                item.stargazersCount,
                item.watchersCount,
                item.forksCount,
                item.openIssuesCount,
                item.htmlUrl,
                it
            )
        } ?: return null
    }

    fun gotoGitHubDiscriptionFragment(item: GitHubRepositoryInfo) {
        translateRepositoryInfo(item)?.let {
            val action = GitHubSearchFragmentDirections
                .actionGitHubSearchFragmentToGitHubDiscriptionFragment(it)
            Log.d("GitHubRepositoryInfo", item.toString())
            findNavController().navigate(action)
        } ?: showErrorDialog() //日付取得失敗
    }

    private fun showErrorDialog() {
        val action = GitHubSearchFragmentDirections
            .actionGitHubSearchFragmentToCommonErrorDialogFragment()
        findNavController().navigate(action)
    }
}

val diff_util = object: DiffUtil.ItemCallback<GitHubRepositoryInfo>() {
    override fun areItemsTheSame(oldItem: GitHubRepositoryInfo,
                                 newItem: GitHubRepositoryInfo): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: GitHubRepositoryInfo,
                                    newItem: GitHubRepositoryInfo): Boolean {
        return oldItem == newItem
    }
}

class CustomAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<GitHubRepositoryInfo, CustomAdapter.ViewHolder>(diff_util) {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)

    interface OnItemClickListener {
    	fun itemClick(item: GitHubRepositoryInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    	val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_github_list_item, parent, false)
    	return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    	val item = getItem(position)
        (holder.itemView.findViewById<View>(R.id.repositoryNameView) as TextView).text =
            item.name
        val iconView = holder.itemView.findViewById<ImageView>(R.id.ownerIconView)
        GlideApp.with(holder.itemView.context).load(item.owner.ownerIconUrl).circleCrop().into(iconView)

    	holder.itemView.setOnClickListener {
     		itemClickListener.itemClick(item)
    	}
    }
}
