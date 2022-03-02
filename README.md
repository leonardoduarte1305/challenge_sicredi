# challenge_sicredi

  Para conferir todas as funcionalidades online acesse: https://devback-sicredi.herokuapp.com/swagger-ui/index.html
  
  Para conferir as funcionalidades localmente:
    - Clone o projeto
    - Rode no Docker um container de MySQL: 
          docker run -d --name db_leo_sicredi_mysql_dev -e MYSQL_ROOT_PASSWORD=senhaSegura -e MYSQL_DATABASE=sicredi -p 3306:3306 mysql:latest
    - O projeto está configurado para subir em modo de desenvolvimento.
    - em http://localhost:8080/swagger-ui/index.html você pode conferir todas as funcionalidades.
    
  Para conferir as funcionalidades localmente em modo de producao:
    - Clone o projeto
    - Rode no Docker um container de MySQL: 
          docker run -d --name db_leo_sicredi_mysql_prod -e MYSQL_ROOT_PASSWORD=senhaSegura -e MYSQL_DATABASE=dbProd -p 3306:3306 mysql:latest
    - Para habilitar o modo de produção algumas variáveis de ambiente são necessárias:
      - {PROFILE_ACTIVE} = prod
      - {DATABASE_USERNAME} = root
      - {DATABASE_PASSWORD} = senhaSegura
      - {DATABASE_URL} = jdbc:mysql://localhost:3306/dbProd
      - {PORT} = 8080 (opcional, padrão 8080)
    - em http://localhost:8080/swagger-ui/index.html você pode conferir todas as funcionalidades.
    
    Principais Tecnologias Utilizadas:
      - Spring Boot
      - Spring DataJPA
      - Mysql
      - SpringFox
      - JUnit Jupiter
      - Mockito
      - Docker
      
  Futuras adições:
    - Testes de integração para os controllers
    - Integração com a api de consulta por CPF
    - Spring Security
    - Criação de endpoints para Alteração e Exclusão de entidades
    - Criação da imagem Docker
