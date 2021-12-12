import java.util.Arrays;

public class ThreadHomework {

    public static void main(String[] args) throws InterruptedException {
        int size = 10000000;
        float[] arr = new float[size];
        float[] arr1 = new float[size];
        Arrays.fill(arr, 1.0f);
        Arrays.fill(arr1, 1.0f);

        firstMethod(arr);
        secondMethod(arr1);

    }

    public static void firstMethod(float[] arr) {

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("One thread time: " + (System.currentTimeMillis() - startTime) + " ms.");


    }


    public static void secondMethod(float[] arr1) throws InterruptedException {

        long startTime = System.currentTimeMillis();
        float[] leftHalf = new float[arr1.length/2];
        float[] rightHalf = new float[arr1.length/2];

        System.arraycopy(arr1, 0, leftHalf, 0, arr1.length/2);
        System.arraycopy(arr1, arr1.length/2, rightHalf, 0, arr1.length/2);

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < leftHalf.length; i++) {
                leftHalf[i] = (float) (leftHalf[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        // В этом потоке формулу все же поправил, чтобы firstMethod и secondMethod выдали один и тотже результат
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < rightHalf.length; i++) {
                int j = i+rightHalf.length;
                rightHalf[i] = (float) (rightHalf[i] * Math.sin(0.2f + j / 5) * Math.cos(0.2f + j / 5) * Math.cos(0.4f + j / 2));
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        float[] mergedArray = new float[arr1.length];
        System.arraycopy(leftHalf, 0, mergedArray, 0, arr1.length/2);
        System.arraycopy(rightHalf, 0, mergedArray, arr1.length/2, arr1.length/2);

        System.out.println("Two thread time: " + (System.currentTimeMillis() - startTime) + " ms.");

    }

}
