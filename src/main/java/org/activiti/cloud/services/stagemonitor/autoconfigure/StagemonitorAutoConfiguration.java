package org.activiti.cloud.services.stagemonitor.autoconfigure;

import org.activiti.cloud.services.stagemonitor.StagemonitorInitializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.stagemonitor.core.Stagemonitor;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass(Stagemonitor.class)
@ConditionalOnProperty(name = "spring.activiti.cloud.services.stagemonitor.enabled", matchIfMissing = true)
public class StagemonitorAutoConfiguration {

	@Configuration
	public static class DefaultStagemonitorAutoConfiguration {
	
		@Bean
		@ConditionalOnMissingBean
		public StagemonitorInitializer stagemonitorInitializer() {
			return new StagemonitorInitializer();
		}
	}
	
}
