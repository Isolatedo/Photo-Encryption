package imageEncryption;
import java.util.Map;

/**
 * EncryptionMain
 *
 * @author xuetianbao
 * @date 2024/06/29
 */
public class EncryptionMain {
    public static void main(String[] args) {
        TaskProcessor processor = TaskProcessor.getInstance();
        ImageMessage message = new ImageMessage();

        OriginalImage originalImage = message.getOriginalImage(R.originalPath);

        Map<Integer, int[]> data = originalImage.getData();
        System.out.println(data.toString());
        System.out.println("data大小是:=======>"+data.size());
        int i = 0;
        for (Map.Entry<Integer, int[]> entry : data.entrySet()) {

            String filePath = R.picsPath+(++i)+".png";
            WrittenImage writtenImage = message.getWrittenImage(filePath);
            System.out.println(filePath);
            writtenImage.setOrderNum(entry.getKey());
            writtenImage.setPixelNums(entry.getValue().length);
            ImageEncryptionTask task = new ImageEncryptionTask();
            task.setOriginalImage(originalImage);
            task.setWrittenImage(writtenImage);
            processor.submitTask(task::execute);
            System.out.println(i+".png=====>已提交");
        }
        // 关闭线程池
        processor.shutdown();

    }
}
