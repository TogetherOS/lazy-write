package io.github.pleuvoir.lazy.payload;

import java.io.FileNotFoundException;
import java.io.IOException;

import freemarker.template.TemplateException;

public interface Generator {

	/**
	 * 生成单表实体文件
	 * @param tableName 表名
	 */
	void generateSingleTabelePO(String tableName) throws FileNotFoundException, IOException, TemplateException;

	/**
	 * 根据 sql 生成 VO
	 * @param sql	待执行的 sql
	 * @param voName	vo 名称
	 */
	void generateVO(String sql, String voName) throws FileNotFoundException, IOException, TemplateException;
}
