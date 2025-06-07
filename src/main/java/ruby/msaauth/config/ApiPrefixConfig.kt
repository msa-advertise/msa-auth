package ruby.msaauth.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.util.pattern.PathPatternParser

@Configuration
class ApiPrefixConfig : WebMvcConfigurer {
    override fun configurePathMatch(configurer: PathMatchConfigurer) {
        // @RestController 에 붙은 모든 핸들러의 경로에 /api 를 prefix 로 추가한다.ㄲ
        configurer
            .addPathPrefix("/api") { handlerType ->
                handlerType.isAnnotationPresent(RestController::class.java)
            }
            .setPatternParser(PathPatternParser())
    }
}
