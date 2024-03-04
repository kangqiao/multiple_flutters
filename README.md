
## Flutter Module 集成到Android中

### 1. 依赖Android Arhive(AAR)
```
 cd module
 flutter build aar

 在 module/build/host/outputs/repo 中创建本地存储库
 └── com
    └── zp
        └── multiple_flutters
          └── module
            └── module
              ├── flutter_release
              │   ├── 1.0
              │   │   ├── flutter_release-1.0.aar
              │   │   ├── flutter_release-1.0.aar.md5
              │   │   ├── flutter_release-1.0.aar.sha1
              │   │   ├── flutter_release-1.0.pom
              │   │   ├── flutter_release-1.0.pom.md5
              │   │   └── flutter_release-1.0.pom.sha1
              │   ├── maven-metadata.xml
              │   ├── maven-metadata.xml.md5
              │   └── maven-metadata.xml.sha1
              ├── flutter_profile
              │   ├── ...
              └── flutter_debug
                  └── ...

在app/build.gradle中引入依赖
repositories {
  maven {
    url 'some/path/my_flutter/build/host/outputs/repo'
    // This is relative to the location of the build.gradle file
    // if using a relative path.
  }
  maven {
    url 'https://storage.googleapis.com/download.flutter.io'
  }
}

dependencies {
  // ...
  debugImplementation 'com.example.flutter_module:flutter_debug:1.0'
  profileImplementation 'com.example.flutter_module:flutter_profile:1.0'
  releaseImplementation 'com.example.flutter_module:flutter_release:1.0'
}
```

### 2. 依赖模块的源码

```
在 settings.gradle中增加外部项目Flutter module
// Include the host app project.
include ':app'                                    // assumed existing content
setBinding(new Binding([gradle: this]))                                // new
evaluate(new File(                                                     // new
  settingsDir.parentFile,                                              // new
  'module/.android/include_flutter.groovy'                             // new
)) 

在 app/build.gradle中引入依赖
dependencies {
  implementation project(':flutter')
}
```

