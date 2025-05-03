package imageEncryption;



import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static imageEncryption.ImageMessage.bufferedImageToIntArray;
import static imageEncryption.ImageMessage.intArrayToBufferedImage;

/**
 * ImageEncryptionTask
 *
 * @author xuetianbao
 * @date 2024/06/29
 */

public class ImageEncryptionTask implements Task {
    private OriginalImage originalImage;
    private WrittenImage writtenImage;

    @Override
    public void execute() {
        BufferedImage target = null;
        try {
            BufferedImage source = writtenImage.getSource();
            // 复制像素数据
            int[] targetPixels = bufferedImageToIntArray(source, source.getWidth(), source.getHeight());
            //将基础信息设置到目标图片的第一个第二个第三个像素中
            /**第一个像素存目标图片开始插入的起始位置，
             * 第二个像素存子像素数组在源图片的像素偏移量，
             * 第三个像素的第二个字节存随机种子，后两个字节存数组的大小
             */
            int rgb1 = writtenImage.getStartIndex();
            int rgb2 = writtenImage.getOrderNum();

            int rgb3 = (writtenImage.getSeed() << 16);
            rgb3 = (rgb3 & 0xffff0000) | (writtenImage.getPixelNums() & 0xffff);

            targetPixels[0] = rgb1;
            targetPixels[1] = rgb2;
            targetPixels[2] = rgb3;

            //获取将要加密到目标图片的像素数组
            Map<Integer, int[]> map = originalImage.getData();
            int[] data = map.get(writtenImage.getOrderNum());

            int pos = writtenImage.getStartIndex();
            for (int datum : data) {
                targetPixels[pos] = datum;
                pos += writtenImage.getSeed();
            }
            target = intArrayToBufferedImage(targetPixels, source.getWidth(), source.getHeight(), source.getType());
            writtenImage.setTarget(target);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(writtenImage.getFile().getName() + "=======>任务发生异常");
        }
        //写入磁盘
        try {
            String name = writtenImage.getFile().getName();
            String formatName = name.substring(writtenImage.getFile().getName().lastIndexOf(".") + 1);
            File file = new File("D:\\fanshi\\done\\" + name);
            boolean write = false;
            do {write = ImageIO.write(target, formatName, file);}
            while(!write);
            System.out.println(file.getName()+"======>写入磁盘成功");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public OriginalImage getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(OriginalImage originalImage) {
        this.originalImage = originalImage;
    }

    public WrittenImage getWrittenImage() {
        return writtenImage;
    }

    public void setWrittenImage(WrittenImage writtenImage) {
        this.writtenImage = writtenImage;
    }
}
