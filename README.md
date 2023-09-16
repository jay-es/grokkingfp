```shell
# コンテナ起動
docker compose up -d
# コンテナの bash 実行
docker exec -it scala-sbt bash
# REPL の Scala バージョン指定（初回のみ）
cat <<END > build.sbt
scalaVersion := "3.3.1"
END
# Scala の REPL 起動
sbt console
# ファイル実行
:load /work/src/ch01.scala
```

.gitignore は https://github.com/scalameta/metals/blob/main/.gitignore をコピペ  
.scalafmt.conf は Metals 拡張機能インストール後、format しようとしたらダイアログ出て生成された

本家サンプルコード  
https://github.com/miciek/grokkingfp-examples
