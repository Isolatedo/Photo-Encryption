package imageEncryption;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static imageEncryption.ImageMessage.intArrayToBufferedImage;

/**
 * DecryptionMain
 *
 * @author xuetianbao
 * @date 2024/06/29
 */
public class DecryptionMain {
    public static void main(String[] args) throws InterruptedException {
        TaskProcessor processor = TaskProcessor.getInstance();

        int[] targetPixels = new int[1920 * 1080];
        for (int i = 0; i < R.PIC_NUM; i++) {
            String sourcePath = R.DonePath + (i + 1) + ".png";
            BufferedImage source = new ImageMessage().getAllReadyWrittenImage(sourcePath);
            ImageDecryptionTask task = new ImageDecryptionTask();
            task.setSource(source);
            task.setTargetPixels(targetPixels);
            processor.submitTask(task::execute);
            System.out.println((i + 1) + ".png"+"处理完成");
        }
        processor.shutdown();
        System.out.println();
        BufferedImage targetImage = intArrayToBufferedImage(targetPixels, 1920, 1080, BufferedImage.TYPE_3BYTE_BGR);

        //写入磁盘
        try {
            String formatName = "png";
            File file = new File(R.finalPicPath + UUID.randomUUID() + "." + formatName);
            boolean write = ImageIO.write(targetImage, formatName, file);
            System.out.println(file.getName()+"=======>"+write);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
