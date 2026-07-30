# Fix `IllegalAccessError` in Kapt

The project is failing to build due to a `java.lang.IllegalAccessError` during the Kapt stub generation task. This occurs because the Kotlin Annotation Processing Tool (Kapt) attempts to access internal APIs in the `jdk.compiler` module which are encapsulated in JDK 16 and later (the project uses JDK 17 as required by AGP 8.x).

## Proposed Changes

### Build Configuration

#### [MODIFY] [gradle.properties](file:///C:/Users/Daniel/Desktop/Proyectos/Babytube/gradle.properties)

Add JVM arguments to `org.gradle.jvmargs` to grant Kapt access to the necessary `jdk.compiler` internal packages. This is a standard workaround for Kapt when running on newer JDKs.

The following flags will be added:
- `--add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED`
- `--add-exports=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED`
- `--add-exports=jdk.compiler/com.sun.tools.javac.comp=ALL-UNNAMED`
- `--add-exports=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED`
- `--add-exports=jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED`
- `--add-exports=jdk.compiler/com.sun.tools.javac.model=ALL-UNNAMED`
- `--add-exports=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED`
- `--add-exports=jdk.compiler/com.sun.tools.javac.processing=ALL-UNNAMED`
- `--add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED`
- `--add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED`
- `--add-opens=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED`
- `--add-opens=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED`
- `--add-opens=jdk.compiler/com.sun.tools.javac.comp=ALL-UNNAMED`
- `--add-opens=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED`
- `--add-opens=jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED`
- `--add-opens=jdk.compiler/com.sun.tools.javac.model=ALL-UNNAMED`
- `--add-opens=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED`
- `--add-opens=jdk.compiler/com.sun.tools.javac.processing=ALL-UNNAMED`
- `--add-opens=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED`
- `--add-opens=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED`

## Verification Plan

### Automated Tests
- Run `./gradlew :app:kaptGenerateStubsDebugKotlin` to ensure the task now completes successfully.
- Run a full build `./gradlew assembleDebug` to verify the entire project builds.

## Long-term Recommendation
> [!TIP]
> Consider migrating from Kapt to **KSP (Kotlin Symbol Processing)**. KSP is the modern replacement for Kapt, offering better performance and avoiding these JDK internal access issues. Hilt (used in this project) supports KSP.
