# `riff.jv`

This is a distributed task scheduling framework built in Java, inspired by the open-source project [XXL-Job](https://github.com/xuxueli/xxl-job).

## 1.`Development`

### 1.1.`Environment`

> Add `$MAVEN_REPO` environment variable.

```shell
$ vim ~/.bashrc

# ...

# Local repo
# /home/xxx/maven/repository
MAVEN_REPO=/path/to/your/maven/local/repository

# ...

export MAVEN_REPO

# :wq!

$ source ~/.bashrc
```

```xml
<!-- @see pom.xml -->
<distributionManagement>
    <repository>
        <id>dev</id>
        <name>dev</name>
        <url>file:///${env.MAVEN_REPO}</url>
    </repository>
</distributionManagement>
```

