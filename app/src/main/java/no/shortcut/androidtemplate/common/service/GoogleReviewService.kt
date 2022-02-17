package no.shortcut.androidtemplate.common.service

import android.app.Activity
import android.content.Context
import com.google.android.play.core.review.ReviewManagerFactory

class GoogleReviewService(
    private val context: Context
) : ReviewService {
    override fun requestReview(activity: Activity, onCompleteCallback: ((Boolean) -> Unit)?) {
        ReviewManagerFactory.create(context).also { manager ->
            manager.requestReviewFlow().addOnCompleteListener { request ->
                if (request.isSuccessful) {
                    manager.launchReviewFlow(activity, request.result)
                        .addOnCompleteListener { result ->
                            onCompleteCallback?.let { callback ->
                                callback(result.isSuccessful)
                            }
                        }
                } else {
                    onCompleteCallback?.let { callback ->
                        callback(false)
                    }
                }
            }
        }
    }
}
