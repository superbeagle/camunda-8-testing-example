package io.jp.springzeebesdk;

import io.camunda.zeebe.client.api.response.EvaluateDecisionResponse;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.spring.test.ZeebeSpringTest;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ZeebeSpringTest
@Deployment(resources = "simple.dmn")

public class TestDMN {

	@Autowired private ZeebeClient zeebe;

	@Test
	public void testOK() throws Exception {
		// Prepare data input
		HashMap<String, Object> variables = new HashMap<>();
		variables.put("name","foo");

		// Evaluate a decision
		EvaluateDecisionResponse response =
				zeebe
						.newEvaluateDecisionCommand() //
						.decisionId("Simple_decision")
						.variables(variables) //
						.send()
						.join();

		assertThat(response.getDecisionOutput()).isEqualTo("\"OK\"");
	}

	@Test
	public void testNotOK() throws Exception {
		// Prepare data input
		HashMap<String, Object> variables = new HashMap<>();
		variables.put("name","foobar");

		// Evaluate a decision
		EvaluateDecisionResponse response =
				zeebe
						.newEvaluateDecisionCommand() //
						.decisionId("Simple_decision")
						.variables(variables) //
						.send()
						.join();

		assertThat(response.getDecisionOutput()).isEqualTo("\"Not OK\"");
	}

}