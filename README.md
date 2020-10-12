# `json-to-csv`

I just needed to convert some arrays of `JSON` objects to `CSV` for some work tasks.

Or maybe... Just maybe... I needed an excuse to try [GraalVM Native Image](https://www.graalvm.org/reference-manual/native-image/), [Gradle](https://gradle.org/) and [picocoli](http://picocli.info/) together.

I started with Scala and then ended up mixin Java in to leverage the [`picocli-codegen`](https://github.com/remkop/picocli/tree/master/picocli-codegen) annotation processor.