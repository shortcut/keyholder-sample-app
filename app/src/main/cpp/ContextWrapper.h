//
// Created by imran.baig@vipps.no on 02/09/2021.
//

#ifndef TESTAPP_CONTEXTWRAPPER_H
#define TESTAPP_CONTEXTWRAPPER_H

#include <jni.h>
#include <string>
#include "BaseWrapper.h"


class ContextWrapper : public BaseWrapper {

public:
    ContextWrapper(JNIEnv *env, jobject context);
    jobject getPackageManager();
    jstring getPackageName();
};

class PackageManager : public BaseWrapper {
public:

    PackageManager(JNIEnv *env, jobject packageManager);
    jobject getPackageInfo(jstring packageName);
};

class PackageInfo : public BaseWrapper {

public:

    PackageInfo(JNIEnv *env, jobject packageInfo);
    jobjectArray getSignatures();
    jobject getSignature();

};

class Signature : public BaseWrapper {

public:

    Signature(JNIEnv *env, jobject packageInfo);
    const char * toByteArray();

};

class MessageDigest: public BaseWrapper {

public:
    MessageDigest(JNIEnv *env);
    jobject getInstance();
    void update(const char * );
    jbyteArray digest();
};



#endif //TESTAPP_CONTEXTWRAPPER_H
