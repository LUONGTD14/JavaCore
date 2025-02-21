Google Git Sign in android/platform/cts/refs/heads/android14-release/./tests/media/src/android/mediav2/cts/CodecInfoTest.java blob:9259e3d 4 cec27a2194b27f5e5767e056a69d3617[file][log][blame]
/*
 * Copyright (C) 2022 The Android Open Source Project
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
package android.mediav2.cts;

import static android.media.MediaCodecInfo.CodecCapabilities.COLOR_Format32bitABGR2101010;
import static android.media.MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface;
import static android.media.MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Flexible;
import static android.media.MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420PackedPlanar;
import static android.media.MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420PackedSemiPlanar;
import static android.media.MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Planar;
import static android.media.MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar;
import static android.media.MediaCodecInfo.CodecCapabilities.COLOR_FormatYUVP010;
import static android.media.MediaCodecInfo.CodecCapabilities.FEATURE_HdrEditing;
import static android.mediav2.common.cts.CodecTestBase.BOARD_SDK_IS_AT_LEAST_T;
import static android.mediav2.common.cts.CodecTestBase.FIRST_SDK_IS_AT_LEAST_T;
import static android.mediav2.common.cts.CodecTestBase.IS_AT_LEAST_T;
import static android.mediav2.common.cts.CodecTestBase.IS_HDR_CAPTURE_SUPPORTED;
import static android.mediav2.common.cts.CodecTestBase.PROFILE_HDR10_MAP;
import static android.mediav2.common.cts.CodecTestBase.PROFILE_HDR10_PLUS_MAP;
import static android.mediav2.common.cts.CodecTestBase.VNDK_IS_AT_LEAST_T;
import static android.mediav2.common.cts.CodecTestBase.canDisplaySupportHDRContent;
import static android.mediav2.common.cts.CodecTestBase.isVendorCodec;
import static android.mediav2.common.cts.CodecTestBase.selectCodecs;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import android.media.MediaCodecInfo;
import android.media.MediaCodecInfo.CodecProfileLevel;
import android.media.MediaCodecList;
import android.mediav2.common.cts.CodecTestBase;
import android.os.Build;
import androidx.test.filters.SmallTest;
import com.android.compatibility.common.util.ApiTest;
import com.android.compatibility.common.util.CddTest;
import com.android.compatibility.common.util.MediaUtils;
import com.android.compatibility.common.util.NonMainlineTest;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Check if information advertised by components are in accordance with its
 * peripherals. The
 * scope of this test is to only check if the information advertised is ok.
 * Their functionality
 * however is not verified here.
 */
@SmallTest
@RunWith(Parameterized.class)
public class CodecInfoTest {
    private static final String LOG_TAG = CodecInfoTest.class.getSimpleName();
    public String mMediaType;
    public String mCodecName;
    public MediaCodecInfo mCodecInfo;

    public CodecInfoTest(String mediaType, String codecName, MediaCodecInfo codecInfo) {
        mMediaType = mediaType;
        mCodecName = codecName;
        mCodecInfo = codecInfo;
    }

    @Parameterized.Parameters(name = "{index}_{0}_{1}")
    public static Collection<Object[]> input() {
        final List<Object[]> argsList = new ArrayList<>();
        MediaCodecList codecList = new MediaCodecList(MediaCodecList.REGULAR_CODECS);
        for (MediaCodecInfo codecInfo : codecList.getCodecInfos()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && codecInfo.isAlias()) {
                continue;
            }
            if (CodecTestBase.codecPrefix != null &&
                    !codecInfo.getName().startsWith(CodecTestBase.codecPrefix)) {
                continue;
            }
            String[] types = codecInfo.getSupportedTypes();
            for (String type : types) {
                argsList.add(new Object[] { type, codecInfo.getName(), codecInfo });
            }
        }
        return argsList;
    }

    /**
     * For all the available decoders on the device, the test checks if their
     * decoding
     * capabilities are in sync with the device's display capabilities. Precisely,
     * if a video
     * decoder advertises support for a HDR profile then the device should be
     * capable of
     * displaying the same with out any tone mapping. Else, the decoder should not
     * advertise such
     * support.
     */
    @Test
    // TODO (b/228237404) Remove the following once there is a reliable way to query
    // HDR
    // display capabilities at native level, till then limit the test to vendor
    // codecs
    @NonMainlineTest
    @CddTest(requirements = "5.1.7/C-2-1")
    @ApiTest(apis = "MediaCodecInfo.CodecCapabilities#profileLevels")
    public void testHDRDisplayCapabilities() {
        Assume.assumeTrue("Test needs Android 13", IS_AT_LEAST_T);
        Assume.assumeTrue("Test needs VNDK Android 13", VNDK_IS_AT_LEAST_T);
        Assume.assumeTrue("Test needs First SDK Android 13", FIRST_SDK_IS_AT_LEAST_T);
        Assume.assumeTrue("Test is applicable for video codecs", mMediaType.startsWith("video/"));
        // TODO (b/228237404) Remove the following once there is a reliable way to query
        // HDR
        // display capabilities at native level, till then limit the test to vendor
        // codecs
        Assume.assumeTrue("Test is restricted to vendor codecs", isVendorCodec(mCodecName));
        int[] Hdr10Profiles = PROFILE_HDR10_MAP.get(mMediaType);
        int[] Hdr10PlusProfiles = PROFILE_HDR10_PLUS_MAP.get(mMediaType);
        Assume.assumeTrue("Test is applicable for codecs with HDR10/HDR10+ profiles",
                Hdr10Profiles != null || Hdr10PlusProfiles != null);
        MediaCodecInfo.CodecCapabilities caps = mCodecInfo.getCapabilitiesForType(mMediaType);
        for (CodecProfileLevel pl : caps.profileLevels) {
            boolean isHdr10Profile = Hdr10Profiles != null &&
                    IntStream.of(Hdr10Profiles).anyMatch(x -> x == pl.profile);
            boolean isHdr10PlusProfile = Hdr10PlusProfiles != null &&
                    IntStream.of(Hdr10PlusProfiles).anyMatch(x -> x == pl.profile);
            // TODO (b/228237404) Once there is a way to query support for HDR10/HDR10+
            // display at
            // native level, separate the following to independent checks for HDR10 and
            // HDR10+
            if (isHdr10Profile || isHdr10PlusProfile) {
                assertTrue(mCodecInfo.getName() + " Advertises support for HDR10/HDR10+ profile " +
                        pl.profile + " without any HDR display", canDisplaySupportHDRContent());
            }
        }
    }

    /**
     * Checks if the video codecs available on the device advertise support for
     * mandatory color
     * formats. The test only checks if the decoder/encoder is advertising the
     * required color
     * format. It doesn't verify if it actually supports by decoding/encoding.
     */
    @CddTest(requirements = { "5.1.7/C-1-2", "5.1.7/C-1-3", "5.1.7/C-4-1", "5.12/C-6-5",
            "5.12/C-7-1", "5.12/C-7-3" })
    @Test
    public void testColorFormatSupport() {
        Assume.assumeTrue("Test is applicable for video codecs", mMediaType.startsWith("video/"));
        MediaCodecInfo.CodecCapabilities caps = mCodecInfo.getCapabilitiesForType(mMediaType);
        assertFalse(mCodecInfo.getName() + " does not support COLOR_FormatYUV420Flexible",
                IntStream.of(caps.colorFormats)
                        .noneMatch(x -> x == COLOR_FormatYUV420Flexible));
        assertFalse(mCodecInfo.getName()
                + " does not support at least one of planar or semi planar yuv 420 888",
                IntStream.of(caps.colorFormats)
                        .noneMatch(x -> x == COLOR_FormatYUV420PackedPlanar)
                        && IntStream.of(caps.colorFormats)
                                .noneMatch(x -> x == COLOR_FormatYUV420Planar)
                        && IntStream.of(caps.colorFormats)
                                .noneMatch(x -> x == COLOR_FormatYUV420PackedSemiPlanar)
                        && IntStream.of(caps.colorFormats)
                                .noneMatch(x -> x == COLOR_FormatYUV420SemiPlanar));
        boolean canHandleHdr = CodecTestBase.doesCodecSupportHDRProfile(mCodecName, mMediaType);
        if (mCodecInfo.isEncoder()) {
            if (IS_HDR_CAPTURE_SUPPORTED && canHandleHdr) {
                assertFalse(mCodecInfo.getName()
                        + " supports HDR profile but does not support COLOR_FormatYUVP010",
                        IntStream.of(caps.colorFormats).noneMatch(x -> x == COLOR_FormatYUVP010));
            }
            // Encoders that support FEATURE_HdrEditing, must support ABGR2101010 color
            // format
            // and at least one HDR profile
            boolean hdrEditingSupported = caps.isFeatureSupported(FEATURE_HdrEditing);
            if (hdrEditingSupported) {
                boolean abgr2101010Supported = IntStream.of(caps.colorFormats)
                        .anyMatch(x -> x == COLOR_Format32bitABGR2101010);
                assertTrue(mCodecName + " supports FEATURE_HdrEditing, but does not support"
                        + " COLOR_FormatABGR2101010 color formats.", abgr2101010Supported);
                assertTrue(mCodecName + " supports FEATURE_HdrEditing, but does not support"
                        + " any HDR profiles.", canHandleHdr);
            }
        } else {
            if (FIRST_SDK_IS_AT_LEAST_T && VNDK_IS_AT_LEAST_T && BOARD_SDK_IS_AT_LEAST_T
                    && canDisplaySupportHDRContent() && canHandleHdr) {
                if (MediaUtils.isTv()) {
                    // Some TV devices support HDR10 display with VO instead of GPU. In this
                    // case, skip checking P010 on TV devices.
                    Assume.assumeFalse(mCodecInfo.getName()
                            + " supports HDR profile but does not support "
                            + "COLOR_FormatYUVP010. Skip checking on TV device",
                            IntStream.of(caps.colorFormats)
                                    .noneMatch(x -> x == COLOR_FormatYUVP010));
                } else {
                    assertFalse(mCodecInfo.getName()
                            + " supports HDR profile but does not support "
                            + "COLOR_FormatYUVP010",
                            IntStream.of(caps.colorFormats)
                                    .noneMatch(x -> x == COLOR_FormatYUVP010));
                }
            }
        }
        // COLOR_FormatSurface support is an existing requirement, but we did not
        // test for it before T. We can not retroactively apply the higher standard to
        // devices that are already certified, so only test on devices luanching with T
        // or later.
        if (FIRST_SDK_IS_AT_LEAST_T && VNDK_IS_AT_LEAST_T) {
            assertFalse(mCodecInfo.getName() + " does not support COLOR_FormatSurface",
                    IntStream.of(caps.colorFormats)
                            .noneMatch(x -> x == COLOR_FormatSurface));
        }
    }

    /**
     * For all the available encoders on the device, the test checks if their
     * encoding
     * capabilities are in sync with the device's decoding capabilities.
     */
    @CddTest(requirements = "5/C-0-3")
    @Test
    public void testDecoderAvailability() {
        Assume.assumeTrue("Test is applicable only for encoders", mCodecInfo.isEncoder());
        Assume.assumeTrue("Test is applicable for video/audio codecs",
                mMediaType.startsWith("video/") || mMediaType.startsWith("audio/"));
        if (selectCodecs(mMediaType, null, null, true).size() > 0) {
            assertTrue("Device advertises support for encoding " + mMediaType +
                    ", but not decoding it",
                    selectCodecs(mMediaType, null, null, false).size() > 0);
        }
    }
}

Powered by Gitiles|Privacy|
Terms
txt
json