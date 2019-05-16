package com.egg.ih.common;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author adanl
 */
public class MqGenerator {
    private static String currentPath;
    private static String driver;
    private static String url;
    private static String username;
    private static String password;
    private static final Character SEPARATOR = '/';

    static {
        Yaml yaml = new Yaml();
        InputStream inputStream = MqGenerator.class.getClassLoader().getResourceAsStream("application.yml");
        try {
            if(inputStream != null) {
                inputStream = new BufferedInputStream(inputStream);
                Map<String, Object> map = yaml.load(inputStream);
                if(map.get("current") != null) {
                    currentPath = (String) ((Map)(map.get("current"))).get("path");
                }
                if(map.get("spring")!=null && ((Map)(map.get("spring"))).get("datasource")!=null) {
                    Map map1 = ((Map)((Map)(map.get("spring"))).get("datasource"));
                    driver = (String) map1.get("driver-class-name");
                    url = (String) map1.get("url");
                    username = (String) map1.get("username");
                    password = String.valueOf( map1.get("password"));
                }


            }
        }catch (Exception e) {

        }
    }

    public static void main(String[] args) {
        AutoGenerator autoGenerator = new AutoGenerator();

        GlobalConfig config = new GlobalConfig();
        config.setAuthor("lhx");
        config.setOutputDir(currentPath + "/src/main/java");
        config.setFileOverride(false);
        config.setActiveRecord(true);
        config.setEnableCache(false);
        config.setBaseResultMap(true);
        config.setBaseColumnList(false);
        autoGenerator.setGlobalConfig(config);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);

        dataSourceConfig.setTypeConvert(new MySqlTypeConvert());
        dataSourceConfig.setDriverName(driver);
        dataSourceConfig.setUrl(url);
        dataSourceConfig.setUsername(username);
        dataSourceConfig.setPassword(password);
        autoGenerator.setDataSource(dataSourceConfig);

        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        autoGenerator.setStrategy(strategyConfig);

        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.egg.ih.db");
        packageConfig.setEntity("model");
        packageConfig.setMapper("mapper");
        autoGenerator.setPackageInfo(packageConfig);

        // 自定义配置,只生成model和mapper
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {

            }
        };
        /*injectionConfig.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                checkDir(filePath);
                String fileName = filePath.substring(filePath.lastIndexOf(SEPARATOR)+1);
                if(fileName.toLowerCase().startsWith(WORKFLOW_PREFIX)) {
                    return false;
                }else if(filePath.endsWith("Mapper.xml") || FileType.ENTITY==fileType || FileType.MAPPER==fileType) {
                    return true;
                }
                return false;
            }
        });*/
        /**
         *  设置xml生成路径
         *
         */
        List<FileOutConfig> fileOutConfigs = new ArrayList<>();
        fileOutConfigs.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return currentPath + SEPARATOR + "src" + SEPARATOR + "main" + SEPARATOR + "resources" + SEPARATOR + "mapper" + SEPARATOR + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        injectionConfig.setFileOutConfigList(fileOutConfigs);
        autoGenerator.setCfg(injectionConfig);

        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setController(null);
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);
        templateConfig.setXml(null);
        autoGenerator.setTemplate(templateConfig);

        autoGenerator.execute();



    }
}
