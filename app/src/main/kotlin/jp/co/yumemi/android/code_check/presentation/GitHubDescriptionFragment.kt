/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.presentation

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentGithubDescriptionBinding

@AndroidEntryPoint
class GitHubDescriptionFragment : Fragment(R.layout.fragment_github_description) {

    private val args: GitHubDescriptionFragmentArgs by navArgs()

    private var binding: FragmentGithubDescriptionBinding? = null
    private val _binding get() = binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //todo: ViewModelに値を持つ
//        Log.d("検索した日時", lastSearchDate.toString())

        binding = FragmentGithubDescriptionBinding.bind(view)

        val item = args.item

        item.ownerIconUrl?.let {
            _binding.ownerIconView.load(it)
        } ?: _binding.ownerIconView.load(R.drawable.github_mark)

        _binding.nameView.text = item.name

        _binding.buttonOpenCustomTab.setOnClickListener {
            navigateToCustomTab(item.htmlUrl)
        }

        //languageにはnullが入ることがあるので
        var language = "No Language"
        item.language?.let {
            language = "Written in $it"
        }
        _binding.languageView.text = language

        _binding.starsView.text = "${item.stargazersCount} stars"
        _binding.watchersView.text = "${item.watchersCount} watchers"
        _binding.forksView.text = "${item.forksCount} forks"
        _binding.openIssuesView.text = "${item.openIssuesCount} open issues"
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
