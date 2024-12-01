import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
public class Property {
    public static void main(String[] args) {
        Properties properties = new Properties();

        // Thiết lập các thuộc tính
        properties.setProperty("username", "admin");
        properties.setProperty("password", "secret");

        // Lưu thuộc tính vào tệp
        try (FileOutputStream output = new FileOutputStream("D:\\config.properties")) {
            properties.store(output, "Configuration Properties");
            System.out.println("Thuộc tính đã được lưu vào tệp.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (FileInputStream input = new FileInputStream("D:\\config.properties")) {
            properties.load(input);

            // Truy xuất các thuộc tính
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");

            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*
public class MainActivity extends Activity {

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Thiết lập thuộc tính hệ thống
        setSystemProperty("custom.property", "Hello from Java!");

        // Gọi hàm native để lấy thuộc tính
        getNativeProperty();
    }

    // Hàm để thiết lập thuộc tính hệ thống
    private void setSystemProperty(String key, String value) {
        try {
            // Sử dụng reflection để gọi setprop
            Runtime.getRuntime().exec("setprop " + key + " " + value);
            Log.d("MainActivity", "Set property: " + key + " = " + value);
        } catch (Exception e) {
            Log.e("MainActivity", "Failed to set property", e);
        }
    }

    // Khai báo hàm native
    public native void getNativeProperty();
}
#include <jni.h>
#include <cutils/properties.h>
#include <android/log.h>

#define LOG_TAG "NativeLib"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

// Hàm native để lấy giá trị thuộc tính
extern "C" JNIEXPORT void JNICALL
Java_com_example_myapp_MainActivity_getNativeProperty(JNIEnv *env, jobject /* this *//*) {
char value[PROP_VALUE_MAX];
// Lấy giá trị của thuộc tính
property_get("custom.property", value, "Default Value");

// Log giá trị lấy được
LOGI("Property Value: %s", value);
}
cmake***
cmake_minimum_required(VERSION 3.4.1)

add_library(native-lib SHARED native-lib.cpp)

find_library(log-lib log)

target_link_libraries(native-lib ${log-lib})
 */