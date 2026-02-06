// PART 3: Create new file: RestTemplateConfig.java
// Location: user-service/src/main/java/com/finance/user/config/

package com.finance.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}