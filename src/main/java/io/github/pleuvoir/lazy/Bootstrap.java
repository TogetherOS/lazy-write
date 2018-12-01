package io.github.pleuvoir.lazy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import io.github.pleuvoir.lazy.payload.DefaultGenerator;
import io.github.pleuvoir.lazy.payload.Generator;

@SpringBootApplication
@ImportResource(locations = { "classpath:application-context.xml" })
@Import(DefaultGenerator.class)
public class Bootstrap implements CommandLineRunner {

	@Autowired
	private Generator generator;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Bootstrap.class);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		// 生成单表实体文件
		generator.generateSingleTabelePO("emp");
		// 根据 sql 生成 vo
		generator.generateVO("select * from emp e left join dept d on e.deptno = d.deptno", "EmpDeptVO");
	}
}
