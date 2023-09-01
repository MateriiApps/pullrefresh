# pullrefresh
Standalone pull to refresh library for Jetpack Compose multiplatform without the reliance on Material.

[![Maven Central](https://img.shields.io/maven-central/v/dev.materii.pullrefresh/pullrefresh?style=for-the-badge&label=Maven%20Central)](https://central.sonatype.com/artifact/dev.materii.pullrefresh/pullrefresh/)
[![Repo stars](https://img.shields.io/github/stars/MateriiApps/pullrefresh?style=for-the-badge&logo=github)](https://github.com/MateriiApps/pullrefresh/stargazers)
![Build status](https://img.shields.io/github/actions/workflow/status/MateriiApps/pullrefresh/build.yml?style=for-the-badge&logo=github)

## Use

### Add to project

Gradle (Kotlin): 
```kts
implementation("dev.materii.pullrefresh:pullrefresh:$pullRefreshVersion")
```

Gradle (Groovy):
```groovy
implementation 'dev.materii.pullrefresh:pullrefresh:$pullRefreshVersion'
```

Gradle (Version Catalog): 
```toml
materii-pullrefresh = { group = "dev.materii.pullrefresh", name = "pullrefresh", version.ref = "pullrefresh" }
```

### Basic setup
> See the [demo project](https://github.com/MateriiApps/pullrefresh/blob/main/demo/src/main/java/dev/materii/pullrefresh/demo/MainActivity.kt).
```kt
@Composable
fun Test() {
  var isRefreshing by remember {
      mutableStateOf(false)
  }
  var pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = { /* Refresh some data here */ })

  Scaffold(
      modifier = Modifier.pullRefresh(pullRefreshState)
  ) {
    Box(
      contentAlignment = Alignment.Center,
      modifier = Modifier
        .padding(it)
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
    ) {
      PullRefreshIndicator(
        refreshing = isRefreshing,
        state = pullRefreshState,
        modifier = Modifier.align(Alignment.TopCenter)
      )
  }
}
```

## Notice
All of the included components come directly from the official Jetpack Compose Material library, with only the bare minimum required.

```
Copyright 2022 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
