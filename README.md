[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=CharonFramework_charon-configs&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=CharonFramework_charon-configs) [![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=CharonFramework_charon-configs&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=CharonFramework_charon-configs) [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=CharonFramework_charon-configs&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=CharonFramework_charon-configs) [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=CharonFramework_charon-configs&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=CharonFramework_charon-configs)

# Charon Configs
Charon Configs is a work in progress library part of the Charon from. This library extends the Spigot YAML Configuration API.

## Gradle
```groovy
repositories {
    ... 
    maven { url 'https://jitpack.io' }
}

dependencies {
    ...
    implementation 'com.github.CharonFramework:charon-configs:master-SNAPSHOT'
}
```

## Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
        <groupId>com.github.CharonFramework</groupId>
        <artifactId>charon-configs</artifactId>
        <version>master-SNAPSHOT</version>
    </dependency>
</dependencies>
```

## Roadmap
- [x] Implement validators on all getList methods
- [x] Make new methods that use built-in validators like getMaterial, isMaterial, getEntity, isEntity, etc.
- [x] Add a ConfigManager to keep configs loaded in memory and allow for easy reloading
- [ ] Add some sort of caching system so we don't constantly re-validate values

## License
Charon Configs is licensed under the GPLv3 license. See [LICENSE](LICENSE) for more information.
The reason for this is that Charon Configs (and the Charon Framework) relies on the Spigot API, which is licensed under the GPLv3 license.
Although I would have liked to use a more permissive license, the GPLv3 license requires me to use the same license for any library that uses the Spigot API.
