/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.presentation.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.databinding.FragmentGithubDiscriptionBinding

@AndroidEntryPoint
class GitHubDiscriptionFragment : Fragment(R.layout.fragment_github_discription) {

    private val args: GitHubDiscriptionFragmentArgs by navArgs()

    private var binding: FragmentGithubDiscriptionBinding? = null
    private val _binding get() = binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //todo: ViewModelに値を持つ
//        Log.d("検索した日時", lastSearchDate.toString())

        binding = FragmentGithubDiscriptionBinding.bind(view)

        val item = args.item

        item.ownerIconUrl?.let {
            _binding.ownerIconView.load(it)
        } ?: _binding.ownerIconView.load(R.drawable.github_mark)

        _binding.nameView.text = item.name

        //languageにはnullが入ることがあるので
        var language = "No Language"
        item.language?.let {
            language = it
        }
        _binding.languageView.text = language

        _binding.starsView.text = "${item.stargazersCount} stars"
        _binding.watchersView.text = "${item.watchersCount} watchers"
        _binding.forksView.text = "${item.forksCount} forks"
        _binding.openIssuesView.text = "${item.openIssuesCount} open issues"
    }
}
