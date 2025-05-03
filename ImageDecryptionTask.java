package imageEncryption;



import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static imageEncryption.ImageMessage.bufferedImageToIntArray;

/**
 * ImageDecryptionTask
 *
 * @author xuetianbao
 * @date 2024/06/29
 */

public class ImageDecryptionTask implements Task {
    private BufferedImage source;
    private int[] targetPixels;

    @Override
    public void execute() {
        try {
            int[] sourcePixels = bufferedImageToIntArray(source, source.getWidth(), source.getHeight());
            int rgb1 = sourcePixels[0];
            int rgb2 = sourcePixels[1];
            int rgb3 = sourcePixels[2];
            int startIndex = rgb1 & 0x00FFFFFF;// 将前8位设置为0;
            int orderNum = rgb2 & 0x00FFFFFF;// 将前8位设置为0;

            rgb3 = rgb3 & 0x00FFFFFF;// 将前8位设置为0;
            int pixelNums = rgb3 & 0xffff; //得到低16位
            int seed = rgb3 >> 16;//得到种子

            int index = orderNum;
            int pos = startIndex;
            for (int j = 0; j < pixelNums; j++) {
                targetPixels[index++] = sourcePixels[pos];
                pos += seed;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println( "=======>插入数组发生异常");
        }
    }

    public BufferedImage getSource() {
        return source;
    }

    public void setSource(BufferedImage source) {
        this.source = source;
    }

    public int[] getTargetPixels() {
        return targetPixels;
    }

    public void setTargetPixels(int[] targetPixels) {
        this.targetPixels = targetPixels;
    }
}
