//
// Created by imran.baig@vipps.no on 02/09/2021.
//

#include "BaseWrapper.h"


BaseWrapper::BaseWrapper(JNIEnv *env, const char * clsName, jobject obj){
    this->env = env;

    if(clsName) {
        this->cls = (*env).FindClass(clsName);
    }
    else if(obj) {
        this->cls = (*env).GetObjectClass(obj);
    }

    this->wrappedObj = obj;
}

jobject BaseWrapper::CallObjectMethod(const char * methodName, const char * methodSignature){
    const jmethodID mid  = (*env).GetMethodID(cls, methodName,methodSignature);
    return (*env).CallObjectMethod(wrappedObj, mid);
}
