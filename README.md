# RiiiLib  
製作者のりいが使うことだけを考えた、よくわからんライブラリ  
## 今含まれている機能  
- 独自のRegistrate  
- Jarに含まれたファイルをインスタンスフォルダに展開  
- JNIを利用したネイティブライブラリの読み込み  
- ライセンス的に再配布可能なサードパーティー製ライブラリの同梱(Registrate/MixinSquared/FancyTabSections/Flywheel/Ponder/AnvilLib)
## 今後追加したい機能  
- EpicCoreAPIの移植(MIT)  
- JVM引数を変更し再起動するConfig
- Java Agentを使用する機能の簡略化(WIP)
- Kotlinランタイムの起動
- KubeJSとの連携  
- Veil/Irisを使用したシェーダーユーティリティ  
- サーバーサイドだけで実装可能なDynamicRegistry(WIP)
- Thread管理を安全にするライブラリ  
- データパックで定義できる機能の追加・拡張  
- Vulkan・OpenCLの使用簡略化(できたらいいな)
- Flywheel/Veilの汎用ライブラリ
- その他、私が使いたい機能の追加  
- ~~ぱおーん~~  
## やりたくないこと  
- **@Redirect、@Overwrite等の"競合しやすい"Mixinの適用**  
- 他の人から依頼された機能の実装  