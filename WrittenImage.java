package imageEncryption;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * WrittenImage
 *
 * @author xuetianbao
 * @date 2024/06/29
 */
public class WrittenImage {
    //原始图片
    private BufferedImage source;
    //处理后的图片
    private BufferedImage target;
    //处理后的图片输出路径
    private File file;
    //图片处理的起始像素位置 最大值 source.get
    private Integer startIndex;

    //处理像素的位置随机种子
    private Integer seed;

    //处理的像素矩阵在加密图片的相对序号 最大值 1920*1080/1000
    private Integer orderNum;

    //要处理的像素数量
    private Integer pixelNums;

    public WrittenImage(BufferedImage source, BufferedImage target, File file, Integer startIndex, Integer seed, Integer orderNum, Integer pixelNums) {
        this.source = source;
        this.target = target;
        this.file = file;
        this.startIndex = startIndex;
        this.seed = seed;
        this.orderNum = orderNum;
        this.pixelNums = pixelNums;
    }

    public WrittenImage() {
    }

    public WrittenImage(BufferedImage source, int startIndex, int seed) {

    }

    public WrittenImage(BufferedImage source, int startIndex, int seed, File file) {

        this.source = source;
        this.startIndex = startIndex;
        this.seed = seed;
        this.file = file;

    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public BufferedImage getSource() {
        return source;
    }

    public void setSource(BufferedImage source) {
        this.source = source;
    }

    public BufferedImage getTarget() {
        return target;
    }

    public void setTarget(BufferedImage target) {
        this.target = target;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getSeed() {
        return seed;
    }

    public void setSeed(Integer seed) {
        this.seed = seed;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getPixelNums() {
        return pixelNums;
    }

    public void setPixelNums(Integer pixelNums) {
        this.pixelNums = pixelNums;
    }
}
