package com.placeti.avaliacao.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//---------------------------------------------------------------------------
/** Libera o acesso do Angular à API.
 *
 *  O front roda na porta 4200 e a API na 8080, ou seja, origens diferentes.
 *  Sem isso o navegador barra as requisições e a tela fica vazia sem dar
 *  nenhum erro visível na aplicação - só no console do browser. */
//---------------------------------------------------------------------------
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}
