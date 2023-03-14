# 武田コメント

## 2023/03/14 追記コメント
- Fragment内に実装漏れがあり、Bindingがメモリリークを引き起こす可能性があることに気付きました。
  - 期限が過ぎているため修正はしませんが、本当はこうするべきですね...。
  ```
    override fun onDestroy() {
        fragmentGithubSearchBinding = null
        super.onDestroy()
    }  
  ```

## 主な採択技術
### プロジェクト全体に関わるもの
- MVVM
- CleanArchitecture
- DaggerHilt
- Data binding
- Coroutine (Flow)

### 各機能に関わるもの
- Glide（画像の非同期読み込み）
- Mockk（テストモック）
- Truth（テスト時値の比較）

## リリース用署名キーについて
- 別途お渡しします。

## ボーナスissueについて
### UIブラッシュアップ
- 検索結果のリストにAuthorのアイコン画像追加
- リスト高さ変更
- フォントサイズを見やすい大きさに調整
- リポジトリ名の省略表示に対応
- 横画面表示時にリポジトリ詳細画面にスクロールバー追加

### 新機能
- 詳細をブラウザで閲覧するボタン追加

## 時間切れで出来なかったところ
- ProguardRuleを追加したが...
  - 設定が足りておらず、リリースビルドで検索すると画面遷移時にアプリが落ちてしまう
- LintツールとしてDetektを入れたが...
  - 自動化の設定まではできていない
- JsonファイルをRawに持っておいて、APIからのレスポンスが変更になっても正解データを迅速に更新できるようにしたかった
  - Jsonのパースが上手くいかず断念

# 株式会社ゆめみ Android エンジニアコードチェック課題

## 概要

本プロジェクトは株式会社ゆめみ（以下弊社）が、弊社に Android エンジニアを希望する方に出す課題のベースプロジェクトです。本課題が与えられた方は、下記の概要を詳しく読んだ上で課題を取り組んでください。

## アプリ仕様

本アプリは GitHub のリポジトリを検索するアプリです。

<img src="docs/app.gif" width="320">

### 環境

- IDE：Android Studio Arctic Fox | 2020.3.1 Patch 1
- Kotlin：1.5.31
- Java：11
- Gradle：7.0.1
- minSdk：23
- targetSdk：31

※ ライブラリの利用はオープンソースのものに限ります。

### 動作

1. 何かしらのキーワードを入力
2. GitHub API（`search/repositories`）でリポジトリを検索し、結果一覧を概要（リポジトリ名）で表示
3. 特定の結果を選択したら、該当リポジトリの詳細（リポジトリ名、オーナーアイコン、プロジェクト言語、Star 数、Watcher 数、Fork 数、Issue 数）を表示

## 課題取り組み方法

Issues を確認した上、本プロジェクトを [**Duplicate** してください](https://help.github.com/en/github/creating-cloning-and-archiving-repositories/duplicating-a-repository)（Fork しないようにしてください。必要ならプライベートリポジトリにしても大丈夫です）。今後のコミットは全てご自身のリポジトリで行ってください。

コードチェックの課題 Issue は全て [`課題`](https://github.com/yumemi-inc/android-engineer-codecheck/milestone/1) Milestone がついており、難易度に応じて Label が [`初級`](https://github.com/yumemi-inc/android-engineer-codecheck/issues?q=is%3Aopen+is%3Aissue+label%3A初級+milestone%3A課題)、[`中級`](https://github.com/yumemi-inc/android-engineer-codecheck/issues?q=is%3Aopen+is%3Aissue+label%3A中級+milestone%3A課題+) と [`ボーナス`](https://github.com/yumemi-inc/android-engineer-codecheck/issues?q=is%3Aopen+is%3Aissue+label%3Aボーナス+milestone%3A課題+) に分けられています。課題の必須／選択は下記の表とします。

|   | 初級 | 中級 | ボーナス
|--:|:--:|:--:|:--:|
| 新卒／未経験者 | 必須 | 選択 | 選択 |
| 中途／経験者 | 必須 | 必須 | 選択 |

課題 Issueをご自身のリポジトリーにコピーするGitHub Actionsをご用意しております。  
[こちらのWorkflow](./.github/workflows/copy-issues.yml)を[手動でトリガーする](https://docs.github.com/ja/actions/managing-workflow-runs/manually-running-a-workflow)ことでコピーできますのでご活用下さい。

課題が完成したら、リポジトリのアドレスを教えてください。

## 参考記事

提出された課題の評価ポイントに関しては、[こちらの記事](https://qiita.com/blendthink/items/aa70b8b3106fb4e3555f)に詳しく書かれてありますので、ぜひご覧ください。
