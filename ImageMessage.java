package imageEncryption;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author Trimb
 */ // 图片信息类
public class ImageMessage {

    public static OriginalImage getOriginalImage(String originalPath) {
        File file = new File(originalPath);

        //读图片
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //拿到图片的像素数组
        int[] pixels = bufferedImageToIntArray(bufferedImage, bufferedImage.getWidth(), bufferedImage.getHeight());
        // 像素数组总大小
        int totalSize = pixels.length;
        // 分成n份的平均大小
        int averageSize = totalSize / R.PIC_NUM;
        // 创建一个数组来存储n份随机大小
        int[] randomSizes = new int[R.PIC_NUM];
        int minSize = averageSize - (averageSize / 10); // 设置波动范围，例如平均值的10%
        int maxSize = averageSize + (averageSize / 10);
        Random random = new Random();
        int sum = 0;
        // 先将波动的随机数存起来
        for (int i = 0; i < R.PIC_NUM; i++) {
            randomSizes[i] = random.nextInt(maxSize - minSize + 1) + minSize;
            sum += randomSizes[i]; // 计算生成的随机数总数
        }
        // 计算随机数总数与像素数组大小的差值
        int gap = sum - totalSize;

        // 如果差值 > 0，说明4份中有些像素多了，将多的减去
        if (gap > 0) {
            int i = 0;
            while (gap > 0) {
                randomSizes[i % randomSizes.length]--;
                gap--;
                i++;
            }
        } else if (gap < 0) {
            int i = 0;
            // 如果差值 < 0，说明4份中有些像素少了，加到与总数一样
            while (gap < 0) {
                randomSizes[i % randomSizes.length]++;
                gap++;
                i++;
            }
        }

        // 构造一个 map 用于存每一份的序号和像素数组
        Map<Integer, int[]> map = new HashMap<>();
        int index = 0;
        for (int i = 0; i < R.PIC_NUM; i++) {
            // 每一份像素数组
            int[] pixel = new int[randomSizes[i]];
            // 将 pixels 中的像素复制到每一份中
            System.arraycopy(pixels, index, pixel, 0, randomSizes[i]);
            // 存入 map 中，使用子数组的第一个元素的位置作为键
            map.put(index, pixel);
            index += randomSizes[i];
        }
        //构建OriginalImage，赋值并返回结果
        OriginalImage originalImage = new OriginalImage();
        originalImage.setBufferedImage(bufferedImage);
        originalImage.setPartSums(R.PIC_NUM);
        originalImage.setData(map);

        return originalImage;
    }

    /**
     * BufferedImage转 int[]数组
     */
    public static int[] bufferedImageToIntArray(BufferedImage image, int width, int height) {
        int[] data = new int[width * height];
        try {
            image.getRGB(0, 0, width, height, data, 0, width);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * int[]数组转BufferedImage
     */
    public static BufferedImage intArrayToBufferedImage(int[] data, int width, int height, int imageType) {
        BufferedImage image = new BufferedImage(width, height, imageType);
        try {
            image.setRGB(0, 0, width, height, data, 0, width);
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public WrittenImage getWrittenImage(String path) {

        File file = new File(path);
        try {
            BufferedImage source = ImageIO.read(file);
            if (source != null) {
                Random random = new Random();
                int startIndex = random.nextInt(3000) + 4; // 从第4个像素到倒数第1000个元素
                int seed = random.nextInt(255) + 1; // 1-255的随机值
                return new WrittenImage(source, startIndex, seed, file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BufferedImage getAllReadyWrittenImage(String sourcePath) {
        File file = new File(sourcePath);
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}