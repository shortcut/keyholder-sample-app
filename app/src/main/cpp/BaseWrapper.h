//
// Created by imran.baig@vipps.no on 02/09/2021.
//

#ifndef TESTAPP_BASEWRAPPER_H
#define TESTAPP_BASEWRAPPER_H

#include <jni.h>
#include <string>

class BaseWrapper {

protected:
    jclass cls;
    jobject wrappedObj;
    JNIEnv *env;

public:
    BaseWrapper(JNIEnv *env, const char * clsName, jobject obj);
    jobject CallObjectMethod(const char * methodName, const char * methodSignature);
};


#endif //TESTAPP_BASEWRAPPER_H
