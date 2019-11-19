package com.chmei.nzbmanage.configure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lixinjie
 * @since 2018-11-29
 */
@Configuration
@EnableConfigurationProperties(NzbManageProperties.class)
public class NzbManageConfiguration {

}
