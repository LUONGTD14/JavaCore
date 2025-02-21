Google Git Sign in android/platform/cts/refs/heads/android14-release/./tests/tests/media/misc/src/android/media/misc/cts/ResourceManagerStubActivity.java blob:44 b52753a6321e706b1d3bbd1e68555fe99f5fc7[file][log][blame]
/*
 * Copyright 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.media.misc.cts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import junit.framework.Assert;

public class ResourceManagerStubActivity extends Activity {
    private static final String TAG = "ResourceManagerStubActivity";
    private final Object mFinishEvent = new Object();
    private int[] mRequestCodes = { 0, 1 };
    private boolean[] mResults = { false, false };
    private int mNumResults = 0;
    private int mType1 = ResourceManagerTestActivityBase.TYPE_NONSECURE;
    private int mType2 = ResourceManagerTestActivityBase.TYPE_NONSECURE;
    private boolean mWaitForReclaim = true;
    private static final String ERROR_INSUFFICIENT_RESOURCES = "* Please check if the omx component is returning OMX_ErrorInsufficientResources "
            +
            "properly when the codec failure is due to insufficient resource.\n";
    private static final String ERROR_SUPPORTS_MULTIPLE_SECURE_CODECS = "* Please check if this platform supports multiple concurrent secure codec "
            +
            "instances. If not, please add below setting in /etc/media_codecs.xml in order " +
            "to pass the test:\n" +
            "    <Settings>\n" +
            "       <Setting name=\"supports-multiple-secure-codecs\" value=\"false\" />\n" +
            "    </Settings>\n";
    private static final String ERROR_SUPPORTS_SECURE_WITH_NON_SECURE_CODEC = "* Please check if this platform supports co-exist of secure and non-secure codec. "
            +
            "If not, please add below setting in /etc/media_codecs.xml in order to pass the " +
            "test:\n" +
            "    <Settings>\n" +
            "       <Setting name=\"supports-secure-with-non-secure-codec\" value=\"false\" />\n" +
            "    </Settings>\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "Activity " + requestCode + " finished with resultCode " + resultCode);
        mResults[requestCode] = (resultCode == RESULT_OK);
        if (++mNumResults == mResults.length) {
            synchronized (mFinishEvent) {
                mFinishEvent.notify();
            }
        }
    }

    public void testReclaimResource(int type1, int type2, boolean highResolutionForActivity1,
            boolean highResolutionForActivity2) throws InterruptedException {
        mType1 = type1;
        mType2 = type2;
        if (type1 != ResourceManagerTestActivityBase.TYPE_MIX && type1 != type2) {
            // in this case, activity2 may not need to reclaim codec from activity1.
            mWaitForReclaim = false;
        } else {
            mWaitForReclaim = true;
        }
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Context context = getApplicationContext();
                    Intent intent1 = new Intent(context, ResourceManagerTestActivity1.class);
                    intent1.putExtra("test-type", mType1);
                    intent1.putExtra("wait-for-reclaim", mWaitForReclaim);
                    intent1.putExtra("high-resolution", highResolutionForActivity1);
                    startActivityForResult(intent1, mRequestCodes[0]);
                    Thread.sleep(5000); // wait for process to launch and allocate all codecs.
                    Intent intent2 = new Intent(context, ResourceManagerTestActivity2.class);
                    intent2.putExtra("test-type", mType2);
                    intent2.putExtra("high-resolution", highResolutionForActivity2);
                    startActivityForResult(intent2, mRequestCodes[1]);
                    synchronized (mFinishEvent) {
                        mFinishEvent.wait();
                    }
                } catch (Exception e) {
                    Log.d(TAG, "testReclaimResource got exception " + e.toString());
                }
            }
        };
        thread.start();
        thread.join(20000 /* millis */);
        System.gc();
        Thread.sleep(5000); // give the gc a chance to release test activities.
        boolean result = true;
        for (int i = 0; i < mResults.length; ++i) {
            if (!mResults[i]) {
                Log.e(TAG, "Result from activity " + i + " is a fail.");
                result = false;
                break;
            }
        }
        if (!result) {
            String failMessage = "The potential reasons for the failure:\n";
            StringBuilder reasons = new StringBuilder();
            reasons.append(ERROR_INSUFFICIENT_RESOURCES);
            if (mType1 != mType2) {
                reasons.append(ERROR_SUPPORTS_SECURE_WITH_NON_SECURE_CODEC);
            }
            if (mType1 == ResourceManagerTestActivityBase.TYPE_MIX
                    && mType2 == ResourceManagerTestActivityBase.TYPE_SECURE) {
                reasons.append(ERROR_SUPPORTS_MULTIPLE_SECURE_CODECS);
            }
            Assert.assertTrue(failMessage + reasons.toString(), result);
        }
    }

    public void testVideoCodecReclaim(boolean highResolution, String mimeType)
            throws InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Context context = getApplicationContext();
                    // Start the transcoding activity first.
                    Log.d(TAG, "Starting ResourceManagerCodecActivity");
                    Intent decoders = new Intent(context, ResourceManagerCodecActivity.class);
                    decoders.putExtra("high-resolution", highResolution);
                    decoders.putExtra("mime", mimeType);
                    startActivityForResult(decoders, mRequestCodes[0]);
                    // wait for ResourceManagerCodecActivity to launch and allocate all codecs.
                    Thread.sleep(5000);
                    Log.d(TAG, "Starting ResourceManagerRecorderActivity");
                    // Start the Camera Recording next.
                    Intent recorder = new Intent(context, ResourceManagerRecorderActivity.class);
                    recorder.putExtra("high-resolution", highResolution);
                    recorder.putExtra("mime", mimeType);
                    startActivityForResult(recorder, mRequestCodes[1]);
                    synchronized (mFinishEvent) {
                        Log.d(TAG, "Waiting for both actvities to complete");
                        mFinishEvent.wait();
                        Log.d(TAG, "Both actvities completed");
                    }
                } catch (Exception e) {
                    Log.d(TAG, "testVideoCodecReclaim got exception " + e.toString());
                }
            }
        };
        thread.start();
        Log.i(TAG, "Started and waiting for Activities");
        thread.join();
        Log.i(TAG, "Activities completed");
        System.gc();
        // give the gc a chance to release test activities.
        Thread.sleep(5000);
        boolean result = true;
        for (int i = 0; i < mResults.length; ++i) {
            if (!mResults[i]) {
                Log.e(TAG, "Result from activity " + i + " is a fail.");
                result = false;
                break;
            }
        }
        if (!result) {
            String failMessage = "The potential reasons for the failure:\n";
            StringBuilder reasons = new StringBuilder();
            reasons.append(ERROR_INSUFFICIENT_RESOURCES);
            Assert.assertTrue(failMessage + reasons.toString(), result);
        }
    }
}

Powered by Gitiles|Privacy|
Terms
txt
json