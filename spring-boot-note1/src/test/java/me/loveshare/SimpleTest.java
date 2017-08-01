package me.loveshare;

import me.loveshare.note1.data.utils.SplicingUtils;
import org.junit.Test;

/**
 * Created by Tony on 2017/7/31.
 */
public class SimpleTest {

    @Test
    public void testReadLine2Map() {
        String filePathAndName = "D:\\A0-TEST\\text\\test.txt";
        String mapName = "eduDegreeMap";
        SplicingUtils.readLine2Map(filePathAndName, mapName, false);
    }

    @Test
    public void testReadLine2DBenum() {
        String filePathAndName = "D:\\A0-TEST\\text\\test.txt";
        SplicingUtils.readLine2DBenum(filePathAndName);
    }

    @Test
    public void testReadLine2StringArray1() {
        String filePathAndName = "D:\\A0-TEST\\text\\test.txt";
        SplicingUtils.readLine2StringArray(filePathAndName);
    }

    @Test
    public void testReadLine2StringArray2() {
        String filePathAndName = "D:\\A0-TEST\\text\\test.txt";
        SplicingUtils.readLine2StringArray(filePathAndName, "   ");
    }

    @Test
    public void testReadLine2StringArray3() {
        String filePathAndName = "D:\\A0-TEST\\text\\test.txt";
        SplicingUtils.readLine2StringArray1(filePathAndName);
    }
}
