package io.github.cursodsousa.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}") //pega o properties
    String url;
    @Value("${spring.datasource.username}") //pega o properties
    String username;
    @Value("${spring.datasource.password}") //pega o properties
    String password;
    @Value("${spring.datasource.driver-class-name}") //pega o properties
    String driver;

//    @Bean esse dataSource nao é otimizada para usar em produção, ele quebra com mutios dados
    public DataSource dataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource(); // um aimplamentação do spring de datasource
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);
        return ds;
    }

    /**
     * configuracao Hikary
     * https://github.com/brettwooldridge/HikariCP
     * @return
     */
    @Bean //tem um input de coneções, aumenta e diminui dependendo da demanda
    public DataSource hikariDataSource(){

        log.info("Iniciando conexão com o banco na URL: {}", url);

        //configuração basica
        HikariConfig config = new HikariConfig();
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);
        config.setJdbcUrl(url);

        //configurações avançadas
        config.setMaximumPoolSize(10); // maximo de conexões liberadas
        config.setMinimumIdle(1); // tamanho inicial do pool
        config.setPoolName("library-db-pool"); //nome do pool que vai aaparecer no log
        config.setMaxLifetime(600000); // 600 mil ms (10 minutos) dura 10 minutos a conecção
        config.setConnectionTimeout(100000); // timeout para conseguir uma conexão
        config.setConnectionTestQuery("select 1"); // query de teste para ver se o banco ta on

        return new HikariDataSource(config);
    }
}
