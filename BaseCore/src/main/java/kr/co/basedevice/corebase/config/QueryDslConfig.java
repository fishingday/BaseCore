package kr.co.basedevice.corebase.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDslConfig {

  @PersistenceContext
  private EntityManager entityManager;
  
  @PersistenceUnit
  EntityManagerFactory entityManagerFactory;
  
  @Bean
  JPAQueryFactory jpaQueryFactory() {
    return new JPAQueryFactory(this.entityManager);
  }
}
