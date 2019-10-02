> `@Category` annotation is part of JUnit experiemental package
> `org.junit.experimental.categories.Category` 

### Running the category tests
There is no easy way to run category tests on Android. We can add support for Categories in the Android gradle plugin by writing custom gradle code.

Let's look at the code to execute tests with category `robolectric`

```groovy
if (project.gradle.startParameter?.taskRequests?.args[0]?.remove("--robolectric")) {
    for (Project project : subprojects) {
        project.tasks
                .withType(Test)
                .configureEach {
                    useJUnit {
                        includeCategories 'com.dexterapps.testsharding.RobolectricTests'
                        //excludeCategories if we want to exclude any test
                    }
                }
    }
}
```
We need to add this code to root `build.gradle` file to make it work for `all` modules. We can also enable it for specific module. To enable for specific module check [this code](https://github.com/pranayairan/android-unit-test-sharding/blob/master/build.gradle#L72)

Let's walk through this code line by line. 

 1. First, we check if a gradle command is executed with the `--robolectric` parameter. If yes we remove the parameter and proceed. We need to call remove because `--robolectric` is a custom parameter and gradle doesn't understand it. 
 2. If we find this parameter we will instruct JUnit to *include* all tests with Category  `com.dexterapps.testsharding.RobolectricTests` and ignore other tests. We can also use `excludeCategories` to do the reverse. 

To run Unit tests with `@Category(RobolectricTests::class)` using following command. 
```shell
./gradlew :app:testDebugUnitTest --robolectric
```
Similarly we can run other *category tests* with
```shell
./gradlew :app:testDebugUnitTest --unit
or
./gradlew :app:testDebugUnitTest --fasttest
```

### Results
**JUnit Categories** enabled us to divide and run multiple test jobs in parallel. Before `Categories` our test execution time was **5 min**. With `Categories` and parallel jobs, it takes only **3 min** (Unit Test 2 min, Robolectric 3 min) giving us **~40%** savings in test execution time. 
