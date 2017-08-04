package me.loveshare;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tony on 2017/8/4.
 */
public class MyBatisGeneratorExe {

    public static void main(String[] args) {
        try {
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(MyBatisGeneratorExe.class.getResourceAsStream("xml/generatorConfig.2.xml"));
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
            System.out.println("MyBatis Generator finished successfully.");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
