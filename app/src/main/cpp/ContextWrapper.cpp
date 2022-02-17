//
// Created by imran.baig@vipps.no on 02/09/2021.
//

#include "ContextWrapper.h"

const int GET_SIGNATURES = 0x40;

ContextWrapper::ContextWrapper(JNIEnv *env, jobject context): BaseWrapper(env,"android/content/ContextWrapper",  context) {
}

jobject ContextWrapper::getPackageManager() {
    return CallObjectMethod("getPackageManager", "()Landroid/content/pm/PackageManager;");
}

jstring ContextWrapper::getPackageName() {
    return (jstring)CallObjectMethod( "getPackageName", "()Ljava/lang/String;");
}

PackageManager::PackageManager(JNIEnv *env, jobject pm): BaseWrapper(env, 0, pm){
}

jobject PackageManager::getPackageInfo(jstring packageName) {
    const jmethodID mid  = (*env).GetMethodID( cls, "getPackageInfo", "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    return (*env).CallObjectMethod( wrappedObj, mid, packageName, GET_SIGNATURES);
}


PackageInfo::PackageInfo(JNIEnv *env, jobject packageInfo) : BaseWrapper(env, 0, packageInfo){

}

jobjectArray PackageInfo::getSignatures() {
    jfieldID fid = (*env).GetFieldID(cls, "signatures", "[Landroid/content/pm/Signature;");
    return (jobjectArray)(*env).GetObjectField(wrappedObj, fid);
}

jobject PackageInfo::getSignature() {
    jobjectArray signatures = getSignatures();
    return  (*env).GetObjectArrayElement( signatures, 0);
}

Signature::Signature(JNIEnv *env, jobject sign) : BaseWrapper(env, 0, sign) {

}

const char * Signature::toByteArray() {
    return reinterpret_cast<const char *>(CallObjectMethod("toByteArray", "()[B"));
}

MessageDigest::MessageDigest(JNIEnv *env): BaseWrapper(env,"java/security/MessageDigest",  0) {
    wrappedObj = getInstance();
}

jobject MessageDigest::getInstance() {

    jmethodID mid = (*env).GetStaticMethodID( cls, "getInstance","(Ljava/lang/String;)Ljava/security/MessageDigest;");
    return (*env).CallStaticObjectMethod( cls, mid, (*env).NewStringUTF("SHA256"));

}

void MessageDigest::update(const char * bytes) {
    jmethodID mid = (*env).GetMethodID( cls, "update","([B)V");
    (*env).CallVoidMethod( wrappedObj, mid,  (jbyteArray) bytes);
}

jbyteArray MessageDigest::digest() {
    return static_cast<jbyteArray>(CallObjectMethod("digest","()[B"));
}