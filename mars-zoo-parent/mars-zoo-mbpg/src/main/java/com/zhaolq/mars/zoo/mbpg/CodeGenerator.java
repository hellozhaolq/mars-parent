package com.zhaolq.mars.zoo.mbpg;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

/**
 *
 *
 * @author zhaolq
 * @date 2020/10/9 9:53
 */
public class CodeGenerator {

    public static void main(String[] args) {
        AutoGenerator generator = new AutoGenerator();
        generator.setGlobalConfig(com.zhaolq.tool.mbpg.MbpgUtil.getGlobalConfig());
        generator.setDataSource(com.zhaolq.tool.mbpg.MbpgUtil.getDataSourceConfig());
        generator.setPackageInfo(com.zhaolq.tool.mbpg.MbpgUtil.getPackageConfig());
        generator.setCfg(com.zhaolq.tool.mbpg.MbpgUtil.getInjectionConfig());
        generator.setTemplate(com.zhaolq.tool.mbpg.MbpgUtil.getTemplateConfig());
        generator.setStrategy(com.zhaolq.tool.mbpg.MbpgUtil.getStrategyConfig());
        generator.setTemplateEngine(new VelocityTemplateEngine());
        generator.execute();
    }


}
