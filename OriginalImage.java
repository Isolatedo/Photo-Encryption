package imageEncryption;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * OriginalImage
 *
 * @author xuetianbao
 * @date 2024/06/29
 */
public class OriginalImage {
    private BufferedImage bufferedImage;

    private Integer partSums;

    private Map<Integer, int[]> data;

    public OriginalImage(BufferedImage bufferedImage, Integer partSums, Map<Integer, int[]> data) {
        this.bufferedImage = bufferedImage;
        this.partSums = partSums;
        this.data = data;
    }

    public OriginalImage() {
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public Integer getPartSums() {
        return partSums;
    }

    public void setPartSums(Integer partSums) {
        this.partSums = partSums;
    }

    public Map<Integer, int[]> getData() {
        return data;
    }

    public void setData(Map<Integer, int[]> data) {
        this.data = data;
    }


}
