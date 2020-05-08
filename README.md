# Cotton Resources

V2 Branch **UNSTABLE**
======================

This is an expirimental branch where many things in cotton resources may change. Please don't use this branch unless you are insane.

## Importing

Include the Cotton maven in your `build.gradle`:

```groovy
repositories {
  ...
  maven {
      name = "Cotton"
      url = "https://server.bbkr.space:8081/artifactory/libs-release/"
  }
}
```

Include Cotton Resources in your `build.gradle`:

```groovy
dependencies {
  /* this will expose the dependency to anyone building against your mod. You might
     want to set this to modImplementation for production */
  modApi  "io.github.cottonmc:cotton-resources:${cotton_resources_version}"

  /* this will package cotton-resources in your jar. skip if you don't want this */
  include "io.github.cottonmc:cotton-resources:${cotton_resources_version}"
}
```

Set the version in `gradle.properties`:
```properties
cotton_resources_version=1.5.1+1.15.2
```

Tell Loader about your dependency in `fabric.mod.json`:
```
  "depends": {
    "fabric": "*",
    ...

    "cotton-resources": ">=1.5.1",
  }
```
