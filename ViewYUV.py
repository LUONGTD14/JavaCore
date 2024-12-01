import numpy as np
import cv2

def display_yuv_from_file(file_path, width, height):
    # Đọc dữ liệu từ file
    with open(file_path, 'rb') as f:
        yuv_data = f.read()

    # Tính toán kích thước từng phần Y, U, V
    frame_size = width * height
    chroma_size = frame_size // 4  # YUV420 có 1/4 kích thước cho U và V

    # Tách dữ liệu Y, U, V
    y = np.frombuffer(yuv_data[0:frame_size], dtype=np.uint8).reshape((height, width))
    u = np.frombuffer(yuv_data[frame_size:frame_size + chroma_size], dtype=np.uint8).reshape((height // 2, width // 2))
    v = np.frombuffer(yuv_data[frame_size + chroma_size:], dtype=np.uint8).reshape((height // 2, width // 2))

    # Phóng to U, V để khớp với kích thước Y
    u = cv2.resize(u, (width, height), interpolation=cv2.INTER_LINEAR)
    v = cv2.resize(v, (width, height), interpolation=cv2.INTER_LINEAR)

    # Kết hợp Y, U, V thành một frame YUV420p
    yuv420p_frame = cv2.merge([y, u, v])

    # Chuyển YUV420p sang RGB để hiển thị
    rgb_frame = cv2.cvtColor(yuv420p_frame, cv2.COLOR_YUV2BGR)

    # Hiển thị frame
    cv2.imshow("YUV Frame", rgb_frame)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

# Ví dụ gọi hàm
# Đọc frame_0.yuv với kích thước width, height của frame
display_yuv_from_file('D:\\frame_7.yuv', 480, 360)
