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
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.presentation.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.databinding.FragmentGithubDiscriptionBinding

class GitHubDiscriptionFragment : Fragment(R.layout.fragment_github_discription) {

    private val args: GitHubDiscriptionFragmentArgs by navArgs()

    private var binding: FragmentGithubDiscriptionBinding? = null
    private val _binding get() = binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("検索した日時", lastSearchDate.toString())

        binding = FragmentGithubDiscriptionBinding.bind(view)

        var item = args.repositoryInfo

        _binding.ownerIconView.load(item.ownerIconUrl)
        _binding.nameView.text = item.name
        _binding.languageView.text = item.language
        _binding.starsView.text = "${item.stargazersCount} stars"
        _binding.watchersView.text = "${item.watchersCount} watchers"
        _binding.forksView.text = "${item.forksCount} forks"
        _binding.openIssuesView.text = "${item.openIssuesCount} open issues"
    }
}
