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
import jp.co.yumemi.android.code_check.presentation.GitHubSearchViewModel

@AndroidEntryPoint
class GitHubSearchFragment: Fragment(R.layout.fragment_github_search) {

    private val viewModel by viewModels<GitHubSearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val _binding = FragmentGithubSearchBinding.bind(view)

        val _layoutManager = LinearLayoutManager(requireContext())

        val _dividerItemDecoration =
            DividerItemDecoration(requireContext(), _layoutManager.orientation)

        val _adapter = CustomAdapter(object : CustomAdapter.OnItemClickListener {
            override fun itemClick(item: GitHubRepositoryInfo) {
                gotoGitHubDiscriptionFragment(item)
            }
        })

        _binding.searchInputText
            .setOnEditorActionListener { editText, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    editText.text.toString().let {
                        // GitHubリポジトリ検索
                        viewModel.searchRepositories(it)
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

        _binding.recyclerView.also {
            it.layoutManager = _layoutManager
            it.addItemDecoration(_dividerItemDecoration)
            it.adapter = _adapter
        }

        viewModel.gitHubRepositories.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    it.data?.let { response ->
                        Log.d("response", response.toString())
                        _adapter.submitList(response.items)
                    }
                }
                is Resource.DataError -> {
                    //todo: エラーダイアログ表示
                    showErrorDialog()
                }

            }
        }
    }

    private fun translateRepositoryInfo(item: GitHubRepositoryInfo) : RepositoryDescriptionData {
        Log.d("GitHubRepositoryInfo" , item.toString())
        return RepositoryDescriptionData(
            item.owner.ownerIconUrl,
            item.name,
            item.language,
            item.stargazersCount,
            item.watchersCount,
            item.forksCount,
            item.openIssuesCount
        )
    }

    fun gotoGitHubDiscriptionFragment(item: GitHubRepositoryInfo) {
        val _action = GitHubSearchFragmentDirections
            .actionGitHubSearchFragmentToGitHubDiscriptionFragment(translateRepositoryInfo(item))
        Log.d("GitHubRepositoryInfo", item.toString())
        findNavController().navigate(_action)
    }

    private fun showErrorDialog() {
        val _action = GitHubSearchFragmentDirections
            .actionGitHubSearchFragmentToCommonErrorDialogFragment()
        findNavController().navigate(_action)
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
    	val _view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_github_list_item, parent, false)
    	return ViewHolder(_view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    	val _item = getItem(position)
        (holder.itemView.findViewById<View>(R.id.repositoryNameView) as TextView).text =
            _item.name

    	holder.itemView.setOnClickListener {
     		itemClickListener.itemClick(_item)
    	}
    }
}
