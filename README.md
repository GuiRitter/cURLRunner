# cURL Runner

Spring component to run the platform's native cURL with or without using Tor. To use Tor, simply run the browser and let it open in the background. This will keep the necessary ports open.

Example:

```java
package io.github.guiritter.curl_runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.task.AsyncTaskExecutor;

@SpringBootApplication
@ComponentScan("io.github.guiritter.curl_runner")
public class cURLExample implements ApplicationRunner {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private AsyncTaskExecutor asyncExecutor;

	public static void main(String args[]) {
		var application = new SpringApplication(cURLExample.class);

		var env = new StandardEnvironment();
		env.setActiveProfiles("curl_runner_example_profile");

		application.setEnvironment(env);
		application.run(args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		asyncExecutor.submit(applicationContext.getBean(cURL.class).configure("C:\\...\\file.extension", "http://...", false)).get();

		System.exit(0);
	}
}
```

[A few words about Maven.](https://gist.github.com/GuiRitter/1834bd024756e08ab422026a7cd24605)
