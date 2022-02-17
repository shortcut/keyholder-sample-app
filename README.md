# Native Keyholder sample code 

This is a sample code demonstrating how to keep secured values inside of C++ NDK library under the protection of signing certificate.

## Features:

* Implementation of C++ NDK library inside of Kotlin App
* How to make use of signing certificate to protect secret values.
* How to call native library methods inside of a Kotlin class
* How to invoke Java framework methods from inside of NDK library
* How to implement C++ classes wrapping Java objects

Configured with:

- C++
- Kotlin
- Gradle
- NDK
- Makefile

## Context of usage:

Suppose, you come across a development task where you need to keep a build time secret value inside of your app code. And you do not want to hardcode it in Kotlin or Java code since a hacker can retrieve it by reverse-engineering the app code.
AND Of course, you do not want to pay to App Shielding solutions for protecting your data :)

## Approach

Reverse engineering Kotlin/Java code is very easy compared to C++ code. If we want to keep secret values relatively more secure.
we can keep them inside of the NDK C++ code layer and Kotlin app code can get them by calling the native function exposed by the NDK library.

How to keep values securely inside of C++ code:

Keeping the secret values inside the C++ NDK library is not enough because anyone can load the library (System.load library("keyholder-lib")
and call the native method to retrieve so-called secret values. An important part of this solution is to keep secret values securely inside the C++ code.
This can be done by making sure, the caller code is signed with a known signing certificate.
If the signing certificate of caller code matches with the expected certificate inside the NDK layer only then it return the secret value to caller code.

## Further improvements 

- Secret values inside of C++ code can be kept as XOR values. Before returning to app layer they can be decrypted to original values.
- To help the development phase, we can have variant implementation of keyholder library depending on Debug or Release builds. 




