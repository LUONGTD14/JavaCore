import java.io.FileOutputStream;
import java.io.IOException;
public class TestYUVCreate {
    static public byte[] generateYUV420Frame(int width, int height, int frameIndex) {
        int frameSize = width * height;
        int chromaSize = frameSize / 4; // YUV420 có chroma subsampling

        // Tạo buffer YUV cho toàn bộ frame
        byte[] yuvData = new byte[frameSize + 2 * chromaSize];

        // Chia frame thành 4 phần
        int sectionHeight = height / 4;

        // Các giá trị Y, U, V cho từng màu
        byte[][] colors = {
                {76, 85, (byte) 255},   // Xanh (Y, U, V)
                {(byte) 255, 85, 85},   // Đỏ
                {(byte) 128, (byte) 170, (byte) 166}, // Tím
                {(byte) 210, 16, (byte) 146}   // Vàng
        };

        // Xác định thứ tự màu cho frame hiện tại
        int[] colorOrder = {(frameIndex % 4), (frameIndex + 1) % 4, (frameIndex + 2) % 4, (frameIndex + 3) % 4};

        for (int i = 0; i < 4; i++) {
            int startY = i * sectionHeight * width;
            int colorIndex = colorOrder[i];

            // Lấp đầy giá trị Y
            byte yValue = colors[colorIndex][0];
            for (int j = 0; j < sectionHeight * width; j++) {
                yuvData[startY + j] = yValue;
            }

            // Lấp đầy giá trị U và V
            byte uValue = colors[colorIndex][1];
            byte vValue = colors[colorIndex][2];
            int startUV = frameSize + (i * sectionHeight / 2 * width / 2);

            for (int j = 0; j < sectionHeight / 2 * width / 2; j++) {
                yuvData[startUV + j] = uValue;
                yuvData[startUV + chromaSize + j] = vValue;
            }
        }

        return yuvData;
    }
    static public void saveYUVDataToFile(byte[] yuvData, int frameIndex) {
        String fileName = "D:\\frame_" + frameIndex + ".yuv"; // Tên file
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(yuvData); // Ghi dữ liệu vào file
            System.out.println("Frame " + frameIndex + " saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static public byte[] generateYUVWith16x16Blocks(int width, int height, int frameIndex) {
        int frameSize = width * height;
        int chromaSize = frameSize / 4; // YUV420 có subsampling chroma

        // Tạo buffer YUV cho toàn bộ frame
        byte[] yuvData = new byte[frameSize + 2 * chromaSize];

        // Các giá trị Y, U, V cho từng màu sắc
        byte[][] colors = {
                {76, 85, (byte) 255},   // Xanh (Y, U, V)
                {(byte) 255, 85, 85},   // Đỏ
                {(byte) 128, (byte) 170, (byte) 166}, // Tím
                {(byte) 210, 16, (byte) 146}   // Vàng
        };

        // Kích thước ô
        int blockWidth = 16;
        int blockHeight = 16;

        // Tính toán số lượng ô theo chiều ngang và chiều dọc
        int numBlocksX = (width + blockWidth - 1) / blockWidth;
        int numBlocksY = (height + blockHeight - 1) / blockHeight;

        // Xác định thứ tự màu cho cột và hàng (theo frameIndex)
        int[] rowColors = {
                (frameIndex % 4),
                (frameIndex + 1) % 4,
                (frameIndex + 2) % 4,
                (frameIndex + 3) % 4
        };

        int[] colColors = {
                (frameIndex % 4),
                (frameIndex + 1) % 4,
                (frameIndex + 2) % 4,
                (frameIndex + 3) % 4
        };

        // Tạo các ô 16x16 và gán màu
        for (int blockRow = 0; blockRow < numBlocksY; blockRow++) {
            for (int blockCol = 0; blockCol < numBlocksX; blockCol++) {
                // Tính toán vị trí bắt đầu của từng block trong mảng Y
                int blockStartY = (blockRow * blockHeight) * width + (blockCol * blockWidth);

                // Xác định chỉ số màu trong block
                int colorIndex = (blockRow % 4 == 0 ? rowColors[0] :
                        blockRow % 4 == 1 ? rowColors[1] :
                                blockRow % 4 == 2 ? rowColors[2] :
                                        rowColors[3]);

                // Lấp đầy giá trị Y
                byte yValue = colors[colColors[blockCol % 4]][0];
                for (int i = 0; i < blockHeight * blockWidth; i++) {
                    int index = blockStartY + i;
                    if (index < frameSize) {
                        yuvData[index] = yValue;
                    }
                }

                // Lấp đầy giá trị U và V
                byte uValue = colors[colColors[blockCol % 4]][1];
                byte vValue = colors[colColors[blockCol % 4]][2];
                int startUV = frameSize + (blockRow * blockHeight / 2 * width / 2) + (blockCol * blockWidth / 2);

                // Lấp đầy phần chroma (U và V)
                for (int i = 0; i < blockHeight / 2 * blockWidth / 2; i++) {
                    if (startUV + i < frameSize + chromaSize) {
                        yuvData[startUV + i] = uValue;
                        yuvData[startUV + chromaSize + i] = vValue;
                    }
                }
            }
        }

        return yuvData;
    }



    public static void main(String[] args) {
        byte[] frame1 = generateYUV420Frame(480, 360, 2);
        byte[] frame7 = generateYUVWith16x16Blocks(480, 360, 7);
        saveYUVDataToFile(frame7, 7);
    }
}
