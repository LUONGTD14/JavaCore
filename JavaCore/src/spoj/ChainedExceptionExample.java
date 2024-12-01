package spoj;
public class ChainedExceptionExample {
	    public static void main(String[] args) {
	        String originalPrice = "200"; // Giá gốc hợp lệ
	        String discountPercent = "0"; // Giảm giá không hợp lệ vì chia cho 0 sẽ xảy ra

	        try {
	            calculateDiscountedPrice(originalPrice, discountPercent);
	        } catch (DiscountCalculationException e) {
	            System.out.println("Lỗi khi tính giá sau khi giảm: " + e.getMessage());
	            System.out.println("Nguyên nhân gốc: " + e.getCause());
	        }
	    }

	    public static void calculateDiscountedPrice(String price, String discount) throws DiscountCalculationException {
	        try {
	            // Chuyển đổi chuỗi sang số
	            double originalPrice = Double.parseDouble(price); // Có thể ném NumberFormatException
	            double discountPercent = Double.parseDouble(discount); // Có thể ném NumberFormatException

	            // Kiểm tra lỗi chia cho 0 khi phần trăm giảm giá là 0
	            if (discountPercent == 0) {
	                throw new ArithmeticException("Phần trăm giảm giá không thể là 0"); // Ném ArithmeticException
	            }

	            // Tính giá sau khi giảm
	            double discountedPrice = originalPrice - (originalPrice * discountPercent / 100);
	            System.out.println("Giá sau khi giảm: " + discountedPrice);

	        } catch (NumberFormatException e) {
	            // Ném DiscountCalculationException kèm nguyên nhân là NumberFormatException
	            throw new DiscountCalculationException("Dữ liệu không hợp lệ cho giá hoặc phần trăm giảm giá", e);
	        } catch (ArithmeticException e) {
	            // Ném DiscountCalculationException kèm nguyên nhân là ArithmeticException
	            throw new DiscountCalculationException("Lỗi khi tính toán giá sau khi giảm", e);
	        }
	    }
	}

	// Định nghĩa ngoại lệ tùy chỉnh để xử lý lỗi tính toán giảm giá
	class DiscountCalculationException extends Exception {
	    public DiscountCalculationException(String message, Throwable cause) {
	        super(message, cause);
	    }
	}


