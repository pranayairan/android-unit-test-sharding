
# Parallel Unit Tests in Android

This repository demonstrates how we can use **JUnit Categories** to label unit tests in android and run them in parallel. You can read more about it on this blog [https://prandroid.dev/Parallel-unit-tests-in-android/](https://prandroid.dev/Parallel-unit-tests-in-android/)

##  JUnit Categories
JUnit 4.12 introduced a nifty feature **Categories**. Categories provide us with a mechanism to label and group tests and run these tests either by including or excluding the categories. 

JUnit categories are simple but there is no direct support for it in Gradle or Gradle Android. We need to write a custom Gradle code to make it work. Let's look at the code. 

### Marker Interface 
To represent the categories or label tests, we need to create marker interfaces. This simple interfaces will be will use to classify our tests and run them in parallel. 

```kotlin
interface RobolectricTests

interface UnitTests

interface FastTests

interface SlowTests
``` 
### Category Example
Once we have marker interfaces, it is trivial to add them as categories. To categorize a test annotate it with `@category` annotation and add interface name. Let's look at some code. 

```kotlin
 @Test
 @Category(UnitTests::class)
 fun testConvertFahrenheitToCelsius() {
     val actual = ConverterUtil.convertCelsiusToFahrenheit(100F)
     val expected = 212f
     
     assertEquals("Conversion from celsius to fahrenheit failed",
         expected.toDouble(), actual.toDouble(), 0.001)
 }
```
We can add many categories to single method or add categories at class level. 

```kotlin
 @Category(FastTests::class)  
 class ConverterUtilTest {
    @Test  
    @Category(FastTests::class,UnitTests::class)  
    fun testConvertCelsiusToFahrenheit() {
    }
 }
```

### Running the category tests
There is no easy way to run category tests on Android. We can add support for Categories in Android gradle plugin by writing custom gradle code.

Let's look at the code to execute tests with category `robolectric`

```groovy
if (project.gradle.startParameter?.taskRequests?.args[0]?.remove("--robolectric")) {
    subprojects
            .find { it.name == "app" }
            .tasks
            .withType(Test)
            .configureEach {
                useJUnit {
                    includeCategories 'com.dexterapps.testsharding.RobolectricTests'
                    //excludeCategories if we want to exclude any test
                }
            }
}
```
We need to add this code to root `build.gradle` file to make it work for `app` module. For a multi-module project, we need to add support of all modules one by one with module names. 

This gradle code checks if any `Test` task is executed with `--robolectric` parameter. If we find this parameter we will *include* all tests with Category  `com.dexterapps.testsharding.RobolectricTests` for JUnit tasks and ignore others test. We can also use `excludeCategories` to do the reverse. 

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
