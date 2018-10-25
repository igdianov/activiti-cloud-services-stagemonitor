package org.activiti.cloud.services.stagemonitor.autoconfigure;

import static org.assertj.core.api.Assertions.assertThat;

import org.activiti.cloud.services.stagemonitor.StagemonitorInitializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class StagemonitorAutoConfigurationTest {
	
	@Autowired(required=false)
	private StagemonitorInitializer stagemonitorInitializer;
	
	@SpringBootApplication
	static class StagemonitorApplication {
		
	}

	@Test
	public void testStagemonitorInitializer() {
		assertThat(stagemonitorInitializer).isNotNull();
	}

}
