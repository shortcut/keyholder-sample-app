#include <jni.h>
#include <string>
#include "ContextWrapper.h"

jstring bytesToHex(JNIEnv *pEnv, jbyteArray array);
jstring getSignature(JNIEnv *env, _jobject *context);

const char * KNOWN_SIGNATURE = "2FE22022542E7716826ABFB3C88FF02B8301A57EABF913E0E82E87E38578B257";
const char * SECRET_VALUE = "b4812bfxft82414cg3g5cg234gc4g324gug6cugt4q3c";

/*
* getSigningFingerprintNDK function provides the signing certificate SHA256 fingerprint
**/

extern "C" JNIEXPORT jstring JNICALL
Java_no_shortcut_demo_MainActivity_getSecretKeyNDK(JNIEnv *env, jobject thiz, jobject context) {

    const char* signature = env->GetStringUTFChars(getSignature(env, context), 0);

    if(strncmp(KNOWN_SIGNATURE, signature, strlen(KNOWN_SIGNATURE)) == 0) {
        return env->NewStringUTF(SECRET_VALUE);
    }

    return env->NewStringUTF("access denied");
}

extern "C" JNIEXPORT jstring JNICALL
Java_no_shortcut_demo_MainActivity_getSigningFingerprintNDK(JNIEnv *env, jobject thiz, jobject context) {
    return getSignature(env, context);
}

jstring getSignature(JNIEnv *env, _jobject *context) {

    ContextWrapper contextWrapper(env, context);

    jobject packageManagerObj = contextWrapper.getPackageManager();
    PackageManager packageManager(env, packageManagerObj);

    jobject packageInfoObject = packageManager.getPackageInfo(contextWrapper.getPackageName());

    PackageInfo packageInfo(env, packageInfoObject);

    jobject sign = packageInfo.getSignature();

    Signature signature(env, sign);

    const char * bytes   = signature.toByteArray();

    MessageDigest messageDigest(env);
    messageDigest.update(bytes);
    jbyteArray fingerPrintByteArray = messageDigest.digest();


    jstring shaHash =  bytesToHex(env, fingerPrintByteArray);

    return shaHash;
}

jstring bytesToHex(JNIEnv *env, jbyteArray array) {
    jstring ret = NULL;
    if (array != NULL) {
        jsize len = (*env).GetArrayLength(array);
        if (len > 0) {
            char chs[len * 2 + 1];
            jboolean b = JNI_FALSE;
            jbyte *data = (*env).GetByteArrayElements(array, &b);
            int index;
            for (index = 0; index < len; index++) {
                jbyte bc = data[index];
                jbyte h = (jbyte) ((bc >> 4) & 0x0f);
                jbyte l = (jbyte) (bc & 0x0f);
                jchar ch;
                jchar cl;

                if (h > 9) {
                    ch = (jchar) ('A' + (h - 10));
                } else {
                    ch = (jchar) ('0' + h);
                }

                if (l > 9) {
                    cl = (jchar) ('A' + (l - 10));
                } else {
                    cl = (jchar) ('0' + l);
                }
                chs[index * 2] = (char) ch;
                chs[index * 2 + 1] = (char) cl;
            }
            chs[len * 2] = 0;
            (*env).ReleaseByteArrayElements( array, data, JNI_ABORT);
            ret = (*env).NewStringUTF(chs);
        }

    }
    return ret;
}

