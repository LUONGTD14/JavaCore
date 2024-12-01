public class Muxer {
//
//    private static final String TAG = "RawDataMergingHelper";
//    private static final String OUTPUT_FILE_PATH = "/sdcard/output.mp4";
//    private static final int TIMEOUT_US = 10000;
//
//    private static final int VIDEO_WIDTH = 640; // Adjust according to your YUV dimensions
//    private static final int VIDEO_HEIGHT = 480;
//    private static final int VIDEO_FRAME_RATE = 30;
//    private static final int VIDEO_BIT_RATE = 2000000; // Adjust according to your requirement
//
//    private static final int AUDIO_SAMPLE_RATE = 44100; // Adjust according to your PCM audio sample rate
//    private static final int AUDIO_CHANNEL_COUNT = 2; // Adjust according to your PCM audio channels
//
//    public void mergeRawDataToMp4(String yuvFilePath, String pcmFilePath) {
//        MediaCodec videoCodec = null;
//        MediaCodec audioCodec = null;
//        MediaMuxer mediaMuxer = null;
//        FileInputStream yuvInputStream = null;
//        FileInputStream pcmInputStream = null;
//
//        try {
//            // Set up video format
//            MediaFormat videoFormat = MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_AVC, VIDEO_WIDTH, VIDEO_HEIGHT);
//            videoFormat.setInteger(MediaFormat.KEY_FRAME_RATE, VIDEO_FRAME_RATE);
//            videoFormat.setInteger(MediaFormat.KEY_BIT_RATE, VIDEO_BIT_RATE);
//            videoFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar);
//
//            // Set up audio format
//            MediaFormat audioFormat = MediaFormat.createAudioFormat(MediaFormat.MIMETYPE_AUDIO_AAC, AUDIO_SAMPLE_RATE, AUDIO_CHANNEL_COUNT);
//
//            // Set up video encoder
//            videoCodec = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_VIDEO_AVC);
//            videoCodec.configure(videoFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
//            videoCodec.start();
//
//            // Set up audio encoder
//            audioCodec = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_AUDIO_AAC);
//            audioCodec.configure(audioFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
//            audioCodec.start();
//
//            // Set up MediaMuxer
//            mediaMuxer = new MediaMuxer(OUTPUT_FILE_PATH, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
//
//            // Set up input streams for YUV and PCM files
//            yuvInputStream = new FileInputStream(new File(yuvFilePath));
//            pcmInputStream = new FileInputStream(new File(pcmFilePath));
//
//            // Write video data
//            writeToMuxer(videoCodec, mediaMuxer, yuvInputStream, false);
//
//            // Write audio data
//            writeToMuxer(audioCodec, mediaMuxer, pcmInputStream, true);
//
//            Log.d(TAG, "Merging completed successfully.");
//
//        } catch (IOException e) {
//            Log.e(TAG, "IOException: " + e.getMessage());
//        } finally {
//            // Release resources
//            if (videoCodec != null) {
//                videoCodec.stop();
//                videoCodec.release();
//            }
//            if (audioCodec != null) {
//                audioCodec.stop();
//                audioCodec.release();
//            }
//            if (mediaMuxer != null) {
//                mediaMuxer.stop();
//                mediaMuxer.release();
//            }
//            try {
//                if (yuvInputStream != null)
//                    yuvInputStream.close();
//                if (pcmInputStream != null)
//                    pcmInputStream.close();
//            } catch (IOException e) {
//                Log.e(TAG, "IOException when closing streams: " + e.getMessage());
//            }
//        }
//    }
//
//    private void writeToMuxer(MediaCodec mediaCodec, MediaMuxer mediaMuxer, FileInputStream inputStream, boolean isAudio) throws IOException {
//        ByteBuffer[] codecInputBuffers = mediaCodec.getInputBuffers();
//        ByteBuffer[] codecOutputBuffers = mediaCodec.getOutputBuffers();
//        MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
//
//        int bytesRead;
//        while ((bytesRead = inputStream.read(buffer.array())) != -1) {
//            if (bytesRead == 0)
//                continue;
//
//            buffer.limit(bytesRead);
//            buffer.position(0);
//
//            // Encode data using MediaCodec
//            if (mediaCodec != null) {
//                int inputBufferIndex = mediaCodec.dequeueInputBuffer(TIMEOUT_US);
//                if (inputBufferIndex >= 0) {
//                    ByteBuffer inputBuffer = codecInputBuffers[inputBufferIndex];
//                    inputBuffer.clear();
//                    inputBuffer.put(buffer);
//                    mediaCodec.queueInputBuffer(inputBufferIndex, 0, bytesRead, System.nanoTime() / 1000, 0);
//                }
//            }
//
//            // Get encoded data from MediaCodec
//            int outputBufferIndex = mediaCodec.dequeueOutputBuffer(info, TIMEOUT_US);
//            while (outputBufferIndex >= 0) {
//                ByteBuffer outputBuffer = codecOutputBuffers[outputBufferIndex];
//                outputBuffer.position(info.offset);
//                outputBuffer.limit(info.offset + info.size);
//
//                // Write data to MediaMuxer
//                int trackIndex = isAudio ? 1 : 0;
//                mediaMuxer.writeSampleData(trackIndex, outputBuffer, info);
//
//                mediaCodec.releaseOutputBuffer(outputBufferIndex, false);
//                outputBufferIndex = mediaCodec.dequeueOutputBuffer(info, TIMEOUT_US);
//            }
//        }
//    }

}
