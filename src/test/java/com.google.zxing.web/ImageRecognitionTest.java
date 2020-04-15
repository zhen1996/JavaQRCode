package com.google.zxing.web;


import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class ImageRecognitionTest {
    private ImageRecognition imageRecognition = new ImageRecognition();
    private static final String TEST_IMAGE_FOLDER = "test_images";

    @Test
    public void testRecognition() throws URISyntaxException, IOException {
        File dir = new File(ImageRecognitionTest.class.getClassLoader().getResource(TEST_IMAGE_FOLDER).toURI().getPath());
        File[] subImageFiles = dir.listFiles();
        Assert.assertTrue( "测试图片文件数量大于0", subImageFiles.length > 0);
        boolean allSuccess = true;
        for (File file : subImageFiles) {
            BufferedImage img = ImageIO.read(file);
            if (imageRecognition.processImage(img, 0).size() <= 0) {
                System.out.println(String.format("%s 识别失败", file.getName()));
                allSuccess = false;
            }
        }
        Assert.assertTrue("图片集合识别有失败例子", allSuccess);
    }

}
