package com.vma.push.business.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置swagger
 * Created by zhangsl.
 */
@EnableSwagger2
@Configuration
public class Swagger2 extends WebMvcConfigurerAdapter {

//    @Bean
//    public Docket createRestApi(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.vma.push.business"))
//                .paths(PathSelectors.any())
//                .build();
//    }

	private List<Parameter> getHeader() {
		ParameterBuilder tokenPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<Parameter>();
		tokenPar.name("session_id").description("密钥").modelRef(new ModelRef("string")).parameterType("header").required(false);
		pars.add(tokenPar.build());
		return pars;
	}

	@Bean
	public Docket sys_api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/v1.0/**")).build().groupName("企业后台API").pathMapping("/")
				.apiInfo(apiInfo("企业后台API", "文档中可以查询及测试接口调用参数和结果", "1.0")).globalOperationParameters(getHeader());
	}

	@Bean
	public Docket mini_api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/v2.0/**")).build().groupName("小程序API").pathMapping("/")
				.apiInfo(apiInfo("小程序API", "文档中可以查询及测试接口调用参数和结果", "2.0")).globalOperationParameters(getHeader());
	}

	@Bean
	public Docket sale_api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/v3.0/**")).build().groupName("销售端API").pathMapping("/")
				.apiInfo(apiInfo("销售端API", "文档中可以查询及测试接口调用参数和结果", "3.0")).globalOperationParameters(getHeader());
	}

	@Bean
	public Docket common_api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/v4.0/**")).build().groupName("工具类接口").pathMapping("/")
				.apiInfo(apiInfo("工具类接口", "文档中可以查询及测试接口调用参数和结果", "4.0")).globalOperationParameters(getHeader());
	}

	@Bean
	public Docket common_api_2() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/V1.0/common/**")).build().groupName("通用接口").pathMapping("/")
				.apiInfo(apiInfo("通用接口", "文档中可以查询及测试接口调用参数和结果", "4.0")).globalOperationParameters(getHeader());
	}

	@Bean
	public Docket oem_api_part_1() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/V1.0/oem/**")).build().groupName("贴牌商体系API").pathMapping("/")
				.apiInfo(apiInfo("贴牌商体系API", "文档中可以查询及测试接口调用参数和结果", "5.0")).globalOperationParameters(getHeader());
	}

	@Bean
	public Docket oem_api_part_2() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/V1.0/area/**")).build().groupName("地区总代理API").pathMapping("/")
				.apiInfo(apiInfo("地区总代理API", "文档中可以查询及测试接口调用参数和结果", "6.0")).globalOperationParameters(getHeader());
	}

	@Bean
	public Docket oem_api_part_3() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/V1.0/agent/**")).build().groupName("代理商API").pathMapping("/")
				.apiInfo(apiInfo("代理商API", "文档中可以查询及测试接口调用参数和结果", "7.0")).globalOperationParameters(getHeader());
	}

	@Bean
	public Docket oem_api_part_4() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/V1.0/super/**")).build().groupName("超级总后台API").pathMapping("/")
				.apiInfo(apiInfo("超级总后台API", "文档中可以查询及测试接口调用参数和结果", "8.0")).globalOperationParameters(getHeader());
	}

	@Bean
	public Docket company_api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant("/V1.0/company/**")).build().groupName("企业后台api（new)").pathMapping("/")
				.apiInfo(apiInfo("企业后台api（new)", "文档中可以查询及测试接口调用参数和结果", "8.0")).globalOperationParameters(getHeader());
	}

	private ApiInfo apiInfo(String name, String description, String version) {
		ApiInfo apiInfo = new ApiInfoBuilder().title(name).description(description).version(version).build();
		return apiInfo;
	}


//    private ApiInfo apiInfo(){
//        return new ApiInfoBuilder()
//                .title("客迹后端接口")
//                .description("接口文档")
//                .termsOfServiceUrl("")
//                .version("1.0")
//                .build();
//    }
}
