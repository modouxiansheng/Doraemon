package com.example.ioc;

import com.example.ioc.annotion.MyIoc;
import com.example.ioc.annotion.MyIocUse;
import com.example.ioc.core.MyBeanFactoryImpl;
import com.example.ioc.domain.MyIocUseDemo;
import com.example.ioc.domain.Student;
import com.example.ioc.domain.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IocApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(IocApplication.class, args);
		MyBeanFactoryImpl beanFactory = new MyBeanFactoryImpl();
		User user1 = (User)beanFactory.getBeanByName("com.example.ioc.domain.User");
		User user2 = (User)beanFactory.getBeanByName("com.example.ioc.domain.User");
		Student student1 = user1.getStudent();
		Student student2 = user1.getStudent();
		Student student3 = (Student)beanFactory.getBeanByName("com.example.ioc.domain.Student");
		System.out.println(user1);
		System.out.println(user2);
		System.out.println(student1);
		System.out.println(student2);
		System.out.println(student3);
	}
}

