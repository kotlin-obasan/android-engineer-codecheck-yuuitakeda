/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.presentation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentGithubDescriptionBinding
import jp.co.yumemi.android.code_check.util.GlideApp

/**
 * GitHubDescriptionFragment
 * GitHubリポジトリの詳細を閲覧できる画面
 */
@AndroidEntryPoint
class GitHubDescriptionFragment : Fragment(R.layout.fragment_github_description) {

    private val args: GitHubDescriptionFragmentArgs by navArgs()

    private var fragmentGithubDescriptionBinding: FragmentGithubDescriptionBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentGithubDescriptionBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        fragmentGithubDescriptionBinding = binding

        val item = args.item

        Log.d("検索した日時", item.lastSearchDate)

        item.ownerIconUrl?.let {
            GlideApp.with(requireContext()).load(it).circleCrop().into(binding.ownerIconView)
        } ?: binding.ownerIconView.load(R.drawable.github_mark)

        binding.nameView.text = item.name

        binding.buttonOpenCustomTab.setOnClickListener {
            navigateToCustomTab(item.htmlUrl)
        }

        //languageにはnullが入ることがあるので
        var language = "No Language"
        item.language?.let {
            language = "Written in $it"
        }
        binding.languageView.text = language

        binding.starsView.text = "${item.stargazersCount} stars"
        binding.watchersView.text = "${item.watchersCount} watchers"
        binding.forksView.text = "${item.forksCount} forks"
        binding.openIssuesView.text = "${item.openIssuesCount} open issues"
    }

    // ブラウザでURLを開く
    private fun navigateToCustomTab(url: String) {
        val uri = Uri.parse(url)
        CustomTabsIntent.Builder().also { builder ->
            builder.setShowTitle(true)
            builder.build().also {
                it.launchUrl(requireContext(), uri)
            }
        }
    }
}
