package com.topweshare.autocode.service;

import com.topweshare.autocode.common.AutoCodeContext;
import com.topweshare.autocode.entity.CodeFile;
import com.topweshare.autocode.entity.DataSourceConfig;
import com.topweshare.autocode.entity.GeneratorParam;
import com.topweshare.autocode.entity.TemplateConfig;
import com.topweshare.autocode.generator.*;
import com.topweshare.autocode.util.FileUtil;
import com.topweshare.autocode.util.VelocityUtil;
import com.topweshare.autocode.util.XmlFormat;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GeneratorService {
    @Autowired
    private TemplateConfigService templateConfigService;
    
    private static final String DOWNLOAD_FOLDER_NAME = "download";

    /**
     * 生成代码内容,map的
     * @param dataSourceConfig
     * @return 一张表对应多个模板
     */
    public List<CodeFile> generate(GeneratorParam generatorParam, DataSourceConfig dataSourceConfig) {

    	List<SQLContext> contextList = this.buildSQLContextList(generatorParam,dataSourceConfig);

        List<CodeFile> codeFileList = new ArrayList<CodeFile>();
        
        for (SQLContext sqlContext : contextList) {
            setPackageName(sqlContext, generatorParam.getPackageName());

            String packageName = sqlContext.getJavaBeanNameLF();

            for (int tcId : generatorParam.getTcIds()) {

                TemplateConfig template = templateConfigService.get(tcId);
                
                String fileName = doGenerator(sqlContext,template.getFileName());
                String content = doGenerator(sqlContext, template.getContent());

                CodeFile codeFile = new CodeFile(packageName, fileName, content);
                
                this.formatFile(codeFile);
                
                codeFileList.add(codeFile);
            }
        }

        return codeFileList;
    }
    
    private void formatFile(CodeFile file) {
    	String formated = this.doFormat(file.getTemplateName(), file.getContent());
    	file.setContent(formated);
    }
    
    private String doFormat(String fileName,String content) {
    	if(fileName.endsWith(".xml")) {
			return XmlFormat.format(content);
    	}
    	return content;
    }
    
    /**
     * 生成zip
     * @param generatorParam
     * @param dataSourceConfig
     * @param webRootPath
     * @return
     */
    public String generateZip(GeneratorParam generatorParam, DataSourceConfig dataSourceConfig,String webRootPath) {
    	List<SQLContext> contextList = this.buildSQLContextList(generatorParam,dataSourceConfig);
        String projectFolder = this.buildProjectFolder(webRootPath);
        
        for (SQLContext sqlContext : contextList) {
            setPackageName(sqlContext, generatorParam.getPackageName());
            for (int tcId : generatorParam.getTcIds()) {
                TemplateConfig template = templateConfigService.get(tcId);
                String content = doGenerator(sqlContext, template.getContent());
                String fileName = doGenerator(sqlContext,template.getFileName());
                String savePath = doGenerator(sqlContext,template.getSavePath());
                
                content = this.doFormat(fileName, content);
                
                if(StringUtils.isEmpty(fileName)) {
                	fileName = template.getName();
                }
                
                FileUtil.createFolder(projectFolder +File.separator + savePath);
                
                String filePathName = projectFolder + File.separator + 
                		savePath + File.separator + 
                		fileName;
                FileUtil.write(content,filePathName,generatorParam.getCharset());
            }
        }
        
        try {
			FileUtil.zip(projectFolder, projectFolder + ".zip");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			FileUtil.deleteDir(new File(projectFolder));
		}

        return projectFolder + ".zip";
    }
    
    /**
	 * 返回SQL上下文列表
	 * @return
	 */
	private List<SQLContext> buildSQLContextList(GeneratorParam generatorParam,DataSourceConfig dataSourceConfig) {
		
		List<String> tableNames = generatorParam.getTableNames();
		List<SQLContext> contextList = new ArrayList<SQLContext>();
        SQLService service = SQLServiceFactory.build(dataSourceConfig);
        
        TableSelector tableSelector = service.getTableSelector(dataSourceConfig);
        tableSelector.setSchTableNames(tableNames);
        
        List<TableDefinition> tableDefinitions = tableSelector.getTableDefinitions();
        
        for (TableDefinition tableDefinition : tableDefinitions) {
        	contextList.add(new SQLContext(tableDefinition));
		}
	
		return contextList;
	}
    
    private String buildProjectFolder(String webRootPath) {
    	return webRootPath + File.separator + 
    			DOWNLOAD_FOLDER_NAME + File.separator + 
    			AutoCodeContext.getInstance().getUser().getUsername() + System.currentTimeMillis();
    }

    private void setPackageName(SQLContext sqlContext, String packageName) {
        if (StringUtils.hasText(packageName)) {
            sqlContext.setPackageName(packageName);
        }
    }

    private String doGenerator(SQLContext sqlContext, String template) {
    	if(template == null) {
    		return "";
    	}
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
        VelocityContext context = new VelocityContext();

        context.put("context", sqlContext);
        context.put("table", sqlContext.getTableDefinition());
        context.put("pkColumn", sqlContext.getTableDefinition().getPkColumn());
        context.put("columns", sqlContext.getTableDefinition().getColumnDefinitions());
        context.put("dateTime",format.format(new Date()));

        return VelocityUtil.generate(context, template);
    }

}
