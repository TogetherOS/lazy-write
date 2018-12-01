package io.github.pleuvoir.lazy.payload;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import freemarker.template.TemplateException;
import io.github.pleuvoir.lazy.kit.LazyKit;
import io.github.pleuvoir.sql.script.DBScriptRunner;
import io.github.pleuvoir.sql.tookit.DataModel;

public class DefaultGenerator implements Generator {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DBScriptRunner dBScriptRunner;

	@Override
	public void generateSingleTabelePO(String tableName) throws FileNotFoundException, IOException, TemplateException {
		DataModel dataModel = dBScriptRunner.excuteSingleTableQuery(tableName).asDataModel();
		logger.info("生成单表实体文件元数据：{}", dataModel.toJSON());
		// 实体名称
		String entityName = LazyKit.toCapitalizeCamelCase(tableName).concat("PO");
		// 待写入的文件位置
		String file = LazyKit.javaAbsoluteFilePath(entityName);
		logger.info("生成单表实体【{}】，文件【{}】", entityName, file);
		// 根据 freemark 生成文件
		dataModel.addData("entityName", entityName).addData("tableName", tableName).write("entity.ftl", file);
	}

	@Override
	public void generateVO(String sql, String voName) throws FileNotFoundException, IOException, TemplateException {
		DataModel dataModel = dBScriptRunner.excute(sql).asDataModel();
		logger.info("根据sql生成 VO 元数据：{}", dataModel.toJSON());
		// 待写入的文件位置
		String file = LazyKit.javaAbsoluteFilePath(voName);
		logger.info("根据sql生成 VO【{}】，文件【{}】", voName, file);
		// 根据 freemark 生成文件
		dataModel.addData("entityName", voName).write("vo.ftl", file);
	}

}
